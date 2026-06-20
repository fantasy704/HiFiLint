package top.fantasy.hifilint.utils

import android.content.pm.PackageManager
import rikka.shizuku.Shizuku

object ShizukuManager {
    fun isElevated(): Boolean{
        return isServiceRunning() && isPermissionGranted()
    }

    private fun isServiceRunning(): Boolean{
        return try{
            Shizuku.pingBinder()
        }catch (e: Exception){
            false
        }
    }

    private fun isPermissionGranted(): Boolean{
        return try{
            if(Shizuku.isPreV11()) {
                false
            }else{
                Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
            }
        }catch (e: Exception){
            false
        }
    }
}