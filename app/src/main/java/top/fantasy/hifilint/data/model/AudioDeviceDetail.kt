package top.fantasy.hifilint.data.model

data class AudioDeviceDetail(
    val name: String,
    val type: String,
    val typeNumber: Int,
    val address: String, // 对于蓝牙设备等，这会包含MAC地址
    val isSource: Boolean,
    val isSink: Boolean,
    val channelCounts: IntArray,
    val sampleRates: IntArray,
    val encodings: IntArray,
    val isPlaying: Boolean = false,
    val extraInfo: String = ""
)
