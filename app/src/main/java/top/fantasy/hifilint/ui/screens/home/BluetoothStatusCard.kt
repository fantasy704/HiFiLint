package top.fantasy.hifilint.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun BluetoothStatusCard(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()){
    val selectDevice by viewModel.selectedDevice.collectAsState()
    val adbMode by viewModel.adbMode.collectAsState()
    val adbDetail by viewModel.adbAudioDetail.collectAsState()
    Column {
        TitleCard(title = "Bluetooth Status", modifier = Modifier.height(200.dp)) {
            Column(modifier = modifier) {
                selectDevice?.let {
                    HiFiLintLabel(label = "DeviceType", value = it.type)
                    HiFiLintLabel(label = "DeviceAddress", value = it.address)
                    HiFiLintLabel(label = "ChannelCounts", value = it.channelCounts.joinToString(separator = ","))
                    HiFiLintLabel(label = "SampleRates", value = it.sampleRates.joinToString(separator = ","))
                    HiFiLintLabel(label = "Encodings", value = it.encodings.joinToString(separator = ","){ DeviceUtil.parseEncodings(it)})
                    HiFiLintLabel(label = "ExtraInfo", value = it.extraInfo)
                }
            }
        }
        if(adbMode == 1){
            TitleCard(title = "Adb Status", modifier = Modifier.height(600.dp).padding(top = 4.dp)) {
                Column(modifier = modifier) {
                    adbDetail?.let {
                        HiFiLintLabel(label = "DeviceAddress", value = it.deviceAddress)
                        HiFiLintLabel(label = "AudioType", value = it.audioType)
                        HiFiLintLabel(label = "IsRunning", value = it.isRunning.toString())
                        HiFiLintLabel(label = "SystemSampleRate", value = it.systemSampleRate.toString())
                        HiFiLintLabel(label = "SystemFormat", value = it.systemFormat ?: "Unknown")
                        HiFiLintLabel(label = "ChannelMask", value = it.channelMask ?: "Unknown")
                        HiFiLintLabel(label = "BufferSizeFrames", value = it.bufferSizeFrames.toString())
                        HiFiLintLabel(label = "LatencyMs", value = it.latencyMs.toString())
                        HiFiLintLabel(label = "ThreadId", value = it.threadId ?: "Unknown")
                        HiFiLintLabel(label = "IsFastMixer", value = it.isFastMixer.toString())
                        HiFiLintLabel(label = "ActiveTracks", value = it.activeTracks.toString())
                        HiFiLintLabel(label = "BluetoothCodec", value = it.bluetoothCodec ?: "Unknown")
                        HiFiLintLabel(label = "BluetoothSampleRate", value = it.bluetoothSampleRate.toString())
                        HiFiLintLabel(label = "BluetoothBitDepth", value = it.bluetoothBitDepth.toString())
                        HiFiLintLabel(label = "BluetoothChannelMode", value = it.bluetoothChannelMode ?: "Unknown")
                        HiFiLintLabel(label = "UsbAlsaStatus", value = it.usbAlsaStatus ?: "Unknown")
                        HiFiLintLabel(label = "UsbAlsaSampleRate", value = it.usbAlsaSampleRate ?: "Unknown")
                        HiFiLintLabel(label = "UsbAlsaFormat", value = it.usbAlsaFormat ?: "Unknown")
                        HiFiLintLabel(label = "UsbCardName", value = it.usbCardName ?: "Unknown")
                        HiFiLintLabel(label = "CurrentVolume", value = it.currentVolume.toString())
                        HiFiLintLabel(label = "MaxVolume", value = it.maxVolume.toString())
                        HiFiLintLabel(label = "IsBitPerfect", value = it.isBitPerfect.toString())
                    }
                }
            }
        }
    }
}