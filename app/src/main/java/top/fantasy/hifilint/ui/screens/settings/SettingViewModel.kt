package top.fantasy.hifilint.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rikka.shizuku.Shizuku
import top.fantasy.hifilint.data.db.dao.AppSettingDao
import top.fantasy.hifilint.data.db.entity.AppSetting
import top.fantasy.hifilint.utils.ShizukuManager
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingDao: AppSettingDao
) : ViewModel(){
    private val _adbMode = MutableStateFlow(0)
    val adbMode = _adbMode.asStateFlow()
    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg = _errorMsg.asStateFlow()

    private val listener = Shizuku.OnRequestPermissionResultListener { requestCode, grantResult ->
        if (grantResult == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            viewModelScope.launch { updateAdbMode(1) }
        } else {
            _errorMsg.value = "授权失败，无法开启 ADB 模式"
            _adbMode.value = 0
        }
    }

    init {
        Shizuku.addRequestPermissionResultListener(listener)
        viewModelScope.launch {
            // 1. 从数据库读取设置
            val savedSetting = settingDao.getSetting() ?: AppSetting(key = "AppSetting", mode = 0)

            // 2. 验证 Shizuku 权限
            // 注意：ShizukuManager.isElevated() 内部应调用 Shizuku.checkSelfPermission()
            val hasElevatedPrivilege = ShizukuManager.isElevated()

            if (savedSetting.mode == 1) { // 假设 1 是 ADB/Shizuku 模式
                if (hasElevatedPrivilege) {
                    _adbMode.value = 1
                } else {
                    // 如果用户之前选了 ADB 模式但现在没权限，回退到普通模式并更新数据库
                    _adbMode.value = 0
                    settingDao.saveSetting(savedSetting.copy(mode = 0))
                }
            } else {
                _adbMode.value = 0
            }
        }
    }

    companion object {
        private const val SHIZUKU_CODE = 1001
    }

    fun toggleAdbMode(enabled: Boolean) {
        viewModelScope.launch {
            if (enabled) {
                if (ShizukuManager.isElevated()) {
                    updateAdbMode(1)
                } else {
                    if(Shizuku.pingBinder()){
                        try{
                            Shizuku.requestPermission(SHIZUKU_CODE)
                        }catch (e: Exception){
                            _adbMode.value = 0
                            // 提示用户没有权限
                            _errorMsg.value = "申请权限出错: ${e.message}"
                        }
                    }else{
                        _errorMsg.value = "Shizuku 服务未运行，请先启动 Shizuku"
                    }
                }
            } else {
                updateAdbMode(0)
            }
        }
    }

    fun dismissDialog(){
        _errorMsg.value = null
    }

    private suspend fun updateAdbMode(mode: Int) {
        settingDao.saveSetting(AppSetting(key = "AppSetting", mode = mode))
        _adbMode.value = mode
    }

    override fun onCleared() {
        super.onCleared()
        Shizuku.removeRequestPermissionResultListener(listener)
    }
}