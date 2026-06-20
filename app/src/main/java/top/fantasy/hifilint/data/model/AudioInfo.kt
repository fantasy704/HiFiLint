package top.fantasy.hifilint.data.model

data class AudioInfo(
    val sampleRate: String,
    val bufferSize: String,
    val isBluetoothA2dpOn: Boolean,
    val isWireHeadsetOn: Boolean,
    val isSpeakerphoneOn: Boolean,
    val activeDeviceType: String = "Unknown",
    val activeDeviceSampleRate: String = "Unknown",
    val activeDeviceChannels: String = "Unknown",
    val activeDeviceEncoding: String = "Unknown"
)
