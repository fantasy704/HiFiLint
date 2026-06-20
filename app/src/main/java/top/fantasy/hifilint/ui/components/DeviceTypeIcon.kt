package top.fantasy.hifilint.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material.icons.filled.Usb
import androidx.compose.runtime.Composable

@Composable
fun deviceTypeIcon(deviceType: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when {
        deviceType.contains("蓝牙A2DP", ignoreCase = true) ||
                deviceType.contains("蓝牙SCO", ignoreCase = true) -> Icons.Default.Bluetooth
        deviceType.contains("有线耳机", ignoreCase = true) ||
                deviceType.contains("有线耳麦", ignoreCase = true) -> Icons.Default.Headphones
        deviceType.contains("USB耳麦", ignoreCase = true) ||
                deviceType.contains("USB音频", ignoreCase = true) -> Icons.Default.Usb
        deviceType.contains("内置扬声器", ignoreCase = true) -> Icons.Default.Speaker
        deviceType.contains("听筒", ignoreCase = true) -> Icons.Default.Phone
        else -> Icons.Default.Headphones
    }
}