package top.fantasy.hifilint.ui.screens.home

import android.media.AudioDeviceInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import top.fantasy.hifilint.ui.components.HiFiLintLabel
import top.fantasy.hifilint.ui.components.TitleCard
import top.fantasy.hifilint.ui.screens.settings.SettingViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    settingModel: SettingViewModel = hiltViewModel()
){
    val deviceOptions by viewModel.devices.collectAsState() // 设备列表
    val selectedDevice by viewModel.selectedDevice.collectAsState() // 当前选中的设备
    val adbMode by settingModel.adbMode.collectAsState()

    LaunchedEffect(adbMode) {
        viewModel.refresh()
    }

    selectedDevice?.let {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            HomeHeader(
                deviceOptions = deviceOptions,
                selectedDevice = it,
                modifier = Modifier.background(Color(0xEEEEEEEE))
            )

            Column(
                modifier = Modifier
                    .background(Color(0xEEEEEEEE))
                    .padding(8.dp)
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                TitleCard(title = "Audio Device", modifier = Modifier.padding(vertical = 3.dp).heightIn(min = 120.dp, max = 130.dp).fillMaxWidth().padding(vertical = 1.dp)) {
                    Column {
                        HiFiLintLabel(label = "DeviceName", value = it.name)
                        HiFiLintLabel(label = "isSource", value = it.isSource.toString())
                        HiFiLintLabel(label = "isSink", value = it.isSink.toString())
                    }
                }
                if(it.typeNumber == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP || it.typeNumber == AudioDeviceInfo.TYPE_BLUETOOTH_SCO){
                    BluetoothStatusCard()
                }
                if(it.typeNumber == AudioDeviceInfo.TYPE_USB_HEADSET || it.typeNumber == AudioDeviceInfo.TYPE_USB_DEVICE){
                    UsbStatusCard()
                }
            }
        }
    }
    if(selectedDevice == null){
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            TitleCard(title = "Audio Device", modifier = Modifier.padding(vertical = 3.dp).heightIn(min = 160.dp, max = 170.dp).fillMaxWidth().padding(vertical = 1.dp)) {
                Column {
                    HiFiLintLabel(label = "DeviceName", value = "Unknown")
                    HiFiLintLabel(label = "DeviceType", value = "Unknown")
                    HiFiLintLabel(label = "DeviceAddress", value = "Unknown")
                }
            }
        }
    }
}