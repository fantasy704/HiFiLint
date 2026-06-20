package top.fantasy.hifilint.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val icon: ImageVector) {
    object Home : Screen("Device", Icons.Default.DeviceHub)
    object Log : Screen("Log", Icons.Default.Info)
    object Settings : Screen("Setting", Icons.Default.Settings)
}

object Constants {
}