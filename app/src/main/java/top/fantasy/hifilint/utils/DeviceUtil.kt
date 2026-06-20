package top.fantasy.hifilint.utils

import android.media.AudioDeviceInfo
import android.media.AudioFormat

object DeviceUtil {
    fun deviceTypeName(type: Int): String = when (type) {
        AudioDeviceInfo.TYPE_BLUETOOTH_A2DP -> "蓝牙A2DP"
        AudioDeviceInfo.TYPE_BLUETOOTH_SCO -> "蓝牙SCO"
        AudioDeviceInfo.TYPE_WIRED_HEADPHONES -> "有线耳机"
        AudioDeviceInfo.TYPE_WIRED_HEADSET -> "有线耳麦"
        AudioDeviceInfo.TYPE_USB_HEADSET -> "USB耳麦"
        AudioDeviceInfo.TYPE_USB_DEVICE -> "USB音频"
        AudioDeviceInfo.TYPE_BUILTIN_SPEAKER -> "内置扬声器"
        AudioDeviceInfo.TYPE_BUILTIN_EARPIECE -> "听筒"
        else -> "其他($type)"
    }
    fun findActiveOutputDevice(devices: Array<AudioDeviceInfo>): AudioDeviceInfo? {
        return devices.firstOrNull { it.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP }
            ?: devices.firstOrNull { it.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO }
            ?: devices.firstOrNull { it.type == AudioDeviceInfo.TYPE_USB_HEADSET }
            ?: devices.firstOrNull { it.type == AudioDeviceInfo.TYPE_USB_DEVICE }
            ?: devices.firstOrNull { it.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES }
            ?: devices.firstOrNull { it.type == AudioDeviceInfo.TYPE_WIRED_HEADSET }
            ?: devices.firstOrNull { it.type == AudioDeviceInfo.TYPE_BUILTIN_SPEAKER }
            ?: devices.firstOrNull { it.type == AudioDeviceInfo.TYPE_BUILTIN_EARPIECE }
    }

    fun currentSampleRate(propOutputRate: String?, device: AudioDeviceInfo?): String {
        val propRate = propOutputRate?.toIntOrNull()
        val deviceRates = device?.sampleRates ?: intArrayOf()
        return when {
            propRate != null && propRate in deviceRates -> "${propRate}Hz"
            deviceRates.isNotEmpty() -> "${deviceRates.first()}Hz"
            propRate != null -> "${propRate}Hz"
            else -> "Unknown"
        }
    }

    fun channelCountName(count: Int): String = when (count) {
        1 -> "单声道"
        2 -> "立体声"
        4 -> "4.0"
        6 -> "5.1"
        8 -> "7.1"
        else -> "${count}ch"
    }

    fun parseEncodings(encoding: Int): String {
        return when (encoding) {
            2 -> "PCM 16-bit"
            3 -> "PCM 8-bit"
            4 -> "PCM Float (32-bit)"
            21 -> "PCM 24-bit"
            22 -> "PCM 32-bit"
            13 -> "IEC61937 (Passthrough)"
            // 蓝牙相关（部分系统支持）
            101 -> "LDAC"
            102 -> "aptX"
            103 -> "aptX HD"
            else -> "Unknown ($encoding)"
        }
    }
}