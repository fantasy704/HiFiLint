package top.fantasy.hifilint.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import top.fantasy.hifilint.ui.components.HiFiLintLabel
import top.fantasy.hifilint.ui.components.TitleCard
import top.fantasy.hifilint.utils.DeviceUtil

@Composable
fun UsbStatusCard(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
){
    val selectedDevice by viewModel.selectedDevice.collectAsState() // 当前选中的设备


    TitleCard(title = "USB Status", modifier = Modifier.height(220.dp)) {
        Column(modifier = modifier) {
            selectedDevice?.let {
                HiFiLintLabel(label = "DeviceType", value = it.type)
                HiFiLintLabel(label = "DeviceAddress", value = it.address)
                HiFiLintLabel(label = "ChannelCounts", value = it.channelCounts.joinToString(separator = ","))
                HiFiLintLabel(label = "SampleRates", value = it.sampleRates.joinToString(separator = ","))
                HiFiLintLabel(label = "Encodings", value = it.encodings.joinToString(separator = ","){ DeviceUtil.parseEncodings(it)})
                HiFiLintLabel(label = "ExtraInfo", value = it.extraInfo)
            }
        }
    }

}