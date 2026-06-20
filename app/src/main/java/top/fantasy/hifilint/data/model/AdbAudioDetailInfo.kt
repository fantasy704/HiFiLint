package top.fantasy.hifilint.data.model

data class AdbAudioDetailInfo(
    val deviceAddress: String = "",       // 设备唯一标识 (MAC/USB Address)
    val audioType: String = "Unknown",    // 设备类型
    val isRunning: Boolean = false,       // 该设备当前是否正在处理活跃音频流

    // --- AudioFlinger (系统软件混音层) ---
    val systemSampleRate: Int? = null,    // 系统输出采样率 (Hz)
    val systemFormat: String? = null,      // 系统输出格式 (如 AUDIO_FORMAT_PCM_FLOAT)
    val channelMask: String? = null,       // 声道配置 (如 AUDIO_CHANNEL_OUT_STEREO)
    val bufferSizeFrames: Int? = null,     // 缓冲区大小 (Frames)
    val latencyMs: Int? = null,            // 链路延迟 (ms)
    val threadId: String? = null,          // 处理该设备的线程 ID
    val isFastMixer: Boolean = false,      // 是否使用了 Android 的低延迟快速混音器
    val activeTracks: Int = 0,             // 当前有多少个 App 正在往这个设备发声

    // --- Bluetooth (协议传输层) ---
    val bluetoothCodec: String? = null,    // 实时编码: LDAC, aptX HD, AAC, SBC
    val bluetoothSampleRate: Int? = null,  // 蓝牙传输采样率
    val bluetoothBitDepth: Int? = null,    // 蓝牙传输位深 (16, 24, 32)
    val bluetoothChannelMode: String? = null, // 声道模式: Stereo, Mono

    // --- USB/ALSA (底层硬件层) ---
    val usbAlsaStatus: String? = null,     // 硬件流状态: Running, Closed
    val usbAlsaSampleRate: String? = null, // 底层实时硬件频率 (Hz)
    val usbAlsaFormat: String? = null,     // 底层位深格式 (S24_3LE, S32_LE等)
    val usbCardName: String? = null,       // ALSA 声卡名称

    // --- Volume & Logic (音量与逻辑) ---
    val currentVolume: Int? = null,        // 当前流音量
    val maxVolume: Int? = null,            // 最大音量阶数
    val isBitPerfect: Boolean = false,     // 综合诊断：是否初步判定为无损直通

    /**
     * 来源采样率 (App 播放的原始频率)
     * 从 AudioFlinger 的 "Tracks" 部分获取
     */
    val sourceSampleRate: Int? = null,
    val sourceFormat: String? = null,

    /**
     * 策略层信息 (来自 dumpsys audio)
     */
    val audioStreamType: String? = null, // 如 STREAM_MUSIC

    /**
     * 是否是 DirectThread / OffloadThread
     * 这是判定 Bit-Perfect 的关键：如果为 true，通常绕过了系统重采样
     */
    val isDirect: Boolean = false,
    val isOffload: Boolean = false,

    // --- 状态判定 ---
    val resampled: Boolean = false // 逻辑判定：如果 sourceSampleRate != systemSampleRate，则为 true

){
    companion object{
        fun empty(): AdbAudioDetailInfo {
            return AdbAudioDetailInfo()
        }
    }
}