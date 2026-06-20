package top.fantasy.hifilint

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import top.fantasy.hifilint.ui.screens.layout.LayoutScreen
import top.fantasy.hifilint.ui.theme.HiFiLintTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private const val REQUEST_CODE_BLUETOOTH_PERMISSIONS = 1
    }
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HiFiLintTheme {
                Scaffold(modifier = Modifier.background(Color(0xdddddddd)).systemBarsPadding()) { innerPadding ->
                    LayoutScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT), REQUEST_CODE_BLUETOOTH_PERMISSIONS)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        when (requestCode) {
            REQUEST_CODE_BLUETOOTH_PERMISSIONS -> {
                val allGranted = grantResults.all { it == android.content.pm.PackageManager.PERMISSION_GRANTED }
                if (allGranted) {
                    android.util.Log.d("MainActivity", "蓝牙权限已授予")
                } else {
                    android.util.Log.w("MainActivity", "蓝牙权限被拒绝")
                }
            }
        }
    }
}