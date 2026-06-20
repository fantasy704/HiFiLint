package top.fantasy.hifilint.service

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.AudioDeviceInfo
import android.os.IBinder
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import rikka.shizuku.Shizuku
import top.fantasy.hifilint.data.model.AdbAudioDetailInfo
import top.fantasy.hifilint.repository.LogRepository
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

interface ADBService {
    fun isShizukuAvailable(): Boolean
    suspend fun getDetailedAudioDiagnostics(address: String, deviceType: Int): AdbAudioDetailInfo?
}

class ADBServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): ADBService{

    private var userService: IAdbUserService? = null

    @Inject lateinit var logRepository: LogRepository

    private val userServiceArgs by lazy {
        Shizuku.UserServiceArgs(
            ComponentName(context, IAdbUserServiceImpl::class.java)
        ).processNameSuffix("adb_service")
            .debuggable(true)
            .version(1)
            .daemon(false)
    }

    override fun isShizukuAvailable(): Boolean {
        return try{
            Shizuku.pingBinder() && Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        }catch (e: Exception){
            false
        }
    }

    private suspend fun ensureUserService(): IAdbUserService{
        val cached = userService
        if(cached != null && cached.asBinder().pingBinder()){
            return cached
        }
        userService = null

        val result = withTimeoutOrNull(10000L){
            suspendCancellableCoroutine { continuation ->
                val connection = object : ServiceConnection{
                    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                        if(service != null && service.pingBinder()){
                            val instance = IAdbUserService.Stub.asInterface(service)
                            userService = instance
                            if(continuation.isActive){
                                continuation.resume(instance){}
                            }
                        }else{
                            if(continuation.isActive){
                                continuation.resumeWithException(
                                    RuntimeException("UserService binder is null or deal")
                                )
                            }
                        }
                    }

                    override fun onServiceDisconnected(p0: ComponentName?) {
                        userService = null
                    }
                }

                Shizuku.bindUserService(userServiceArgs, connection)

                continuation.invokeOnCancellation {
                    try{
                        Shizuku.unbindUserService(userServiceArgs, connection,  true)
                    }catch (_ : Exception){}
                }
            }
        }
        return result ?: throw RuntimeException("UserService binding timed out")
    }

    private suspend fun executeCommand(command: String): String{
        logRepository.addLog("> Executing: adb shell $command")
        val service = ensureUserService()
        return withContext(Dispatchers.IO){
            service.executeCommand(command) ?: ""
        }
    }


    override suspend fun getDetailedAudioDiagnostics(address: String, deviceType: Int): AdbAudioDetailInfo? {
        if (!isShizukuAvailable()) return null
        return try {
            withContext(Dispatchers.IO) {
                val audioFlingerOutput = executeCommand("dumpsys media.audio_flinger")
                val audioDumpsysOutput = executeCommand("dumpsys audio")
                null
            }
        } catch (e: Exception) {
            Log.e("ADBService", "Error parsing ADB output", e)
            null
        }
    }
}