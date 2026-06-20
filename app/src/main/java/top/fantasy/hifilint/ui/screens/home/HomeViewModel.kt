package top.fantasy.hifilint.ui.screens.home

import android.content.Context
import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import top.fantasy.hifilint.data.db.dao.AppSettingDao
import top.fantasy.hifilint.data.model.AdbAudioDetailInfo
import top.fantasy.hifilint.data.model.AudioDeviceDetail
import top.fantasy.hifilint.data.model.DeviceInfo
import top.fantasy.hifilint.service.ADBService
import top.fantasy.hifilint.service.DeviceInfoService
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deviceInfoService: DeviceInfoService,
    private val adbService: ADBService,
    private val appSettingDao: AppSettingDao,
    @ApplicationContext private val context: Context,
): ViewModel(){
    private val _devices = MutableStateFlow<List<AudioDeviceDetail>>(emptyList())
    val devices: StateFlow<List<AudioDeviceDetail>> = _devices.asStateFlow() //音频设备列表

    val deviceInfo: DeviceInfo = deviceInfoService.getDeviceInfo() //基本设备信息

    private val _selectedDevice = MutableStateFlow<AudioDeviceDetail?>(null)

    val selectedDevice: StateFlow<AudioDeviceDetail?> = _selectedDevice.asStateFlow() //当前选中的设备

    private val _adbAudioDetail = MutableStateFlow<AdbAudioDetailInfo?>(null)
    val adbAudioDetail: StateFlow<AdbAudioDetailInfo?> = _adbAudioDetail.asStateFlow()

    private val _isShizukuAvailable = MutableStateFlow(false)
    val isShizukuAvailable: StateFlow<Boolean> = _isShizukuAvailable.asStateFlow()

    private val _adbMode = MutableStateFlow(0)
    val adbMode = _adbMode.asStateFlow()

    init {
        refresh()
    }

    fun refresh(){
        viewModelScope.launch {
            _adbMode.value = appSettingDao.getSetting()?.mode ?: 0
            _isShizukuAvailable.value = adbService.isShizukuAvailable()
            refreshAudioDevices()
            observeAudioDeviceChanges()
            onDeviceSelected(_devices.value.first())
        }
    }

    fun onDeviceSelected(selection: AudioDeviceDetail){ //监听设备选择
        _selectedDevice.value = selection

        if(_adbMode.value == 1 && _isShizukuAvailable.value){
            viewModelScope.launch {
                _adbAudioDetail.value = adbService.getDetailedAudioDiagnostics(selection.address, selection.typeNumber)
            }
        }
    }

    private fun refreshAudioDevices() { // 刷新音频设备列表
        val deviceList = deviceInfoService.getDetailedAudioDevices()
        _devices.value = deviceList
        if(deviceList.isNotEmpty()){
            _selectedDevice.value = deviceList.first()
        }
    }

    private fun observeAudioDeviceChanges() { // 监听音频设备变化
        viewModelScope.launch {
            callbackFlow {
                val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val callback = object : AudioDeviceCallback() {
                    override fun onAudioDevicesAdded(addedDevices: Array<out AudioDeviceInfo>?) {
                        trySend(Unit)
                    }
                    override fun onAudioDevicesRemoved(removedDevices: Array<out AudioDeviceInfo>?) {
                        trySend(Unit)
                    }
                }
                audioManager.registerAudioDeviceCallback(callback, null)
                awaitClose {
                    audioManager.unregisterAudioDeviceCallback(callback)
                }
            }.collect {
                refreshAudioDevices()
            }
        }
    }

}