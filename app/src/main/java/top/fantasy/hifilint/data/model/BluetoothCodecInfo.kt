package top.fantasy.hifilint.data.model

data class BluetoothCodecInfo(
    val codecName: String,
    val sampleRate: Int,
    val bitDepth: Int? = null,
    val channelCount: Int? = null,
    val codecType: String? = null, // 编解码类型 (如 SBC, AAC, aptX, LDAC, LC3等)
    val bitrate: Int? = null,      // 比特率
    val profile: String? = null,   // 蓝牙音频配置文件 (如 A2DP, SCO)
    val isScramblingEnabled: Boolean? = null, // 是否启用扰频
    val vendorSpecificInfo: Map<String, Any>? = null, // 厂商特定信息
    val maxBitrate: Int? = null,   // 最大比特率
    val minBitrate: Int? = null,   // 最小比特率
    val isDynamicRangeCompressionEnabled: Boolean? = null, // 是否启用了动态范围压缩
    val latencyMode: String? = null, // 延迟模式
    val systemSampleRate: Int? = null,    // 系统混音器实际输出采样率
    val deviceMaxSampleRate: Int? = null, // 设备支持的最高采样率
    val isDownSampled: Boolean? = null    // 是否发生降采样
)
