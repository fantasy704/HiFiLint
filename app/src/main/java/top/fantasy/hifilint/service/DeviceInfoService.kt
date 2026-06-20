package top.fantasy.hifilint.service

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.usb.UsbManager
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import top.fantasy.hifilint.data.model.AudioDeviceDetail
import top.fantasy.hifilint.data.model.DeviceInfo
import top.fantasy.hifilint.utils.DeviceUtil
import javax.inject.Inject


interface DeviceInfoService{
    fun getDeviceInfo(): DeviceInfo
    fun getDetailedAudioDevices(): List<AudioDeviceDetail>
    fun getSystemAudioProperties(): Map<String, String>
}

class DeviceInfoServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): DeviceInfoService{
    private val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val usbManager: UsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
    /**
     * 获取本机信息
     */
    override fun getDeviceInfo(): DeviceInfo {
        return DeviceInfo(
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            device = Build.DEVICE,
            product = Build.PRODUCT,
            hardware = Build.HARDWARE,
            board = Build.BOARD,
            androidVersion = Build.VERSION.RELEASE,
            sdkLevel = Build.VERSION.SDK_INT,
        )
    }

    /**
     * 获取详细音频设备信息
     */
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun getDetailedAudioDevices(): List<AudioDeviceDetail> {
        val devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        return devices.map { deviceInfo ->
            convertToDetailedAudioDeviceInfo(deviceInfo)
        }
    }

    /**
     * 转换为详细音频设备信息
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    @RequiresApi(Build.VERSION_CODES.R)
    private fun convertToDetailedAudioDeviceInfo(audioDeviceInfo: AudioDeviceInfo): AudioDeviceDetail {

        val name = audioDeviceInfo.productName?.toString() ?: "Unknown Device"
        val address = audioDeviceInfo.address
        val type = audioDeviceInfo.type


        var extraInfo = ""
        if (type == AudioDeviceInfo.TYPE_USB_DEVICE || type == AudioDeviceInfo.TYPE_USB_HEADSET) {
            extraInfo = getUsbDeviceSpecifics(name)
        }


        if (type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP) {
            extraInfo = getBluetoothDeviceSpecifics(address)
        }
        return AudioDeviceDetail(
            name = audioDeviceInfo.productName?.toString() ?: "Unknown Device",
            type = DeviceUtil.deviceTypeName(audioDeviceInfo.type),
            typeNumber = audioDeviceInfo.type,
            address = audioDeviceInfo.address,
            isSource = audioDeviceInfo.isSource,
            isSink = audioDeviceInfo.isSink,
            channelCounts = audioDeviceInfo.channelCounts,
            sampleRates = audioDeviceInfo.sampleRates,
            encodings = audioDeviceInfo.encodings,
            extraInfo = extraInfo
        )
    }

    override fun getSystemAudioProperties(): Map<String, String> {
        val props = mutableMapOf<String, String>()
        // 原生输出采样率 (Native Sample Rate)
        props["Native Sample Rate"] = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE)
        // 每缓冲区的帧数 (Low Latency 相关)
        props["Native Frames Per Buffer"] = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER) ?: "Unknown"

        // 硬件特性支持
        val pm = context.packageManager
        props["Low Latency Audio"] = pm.hasSystemFeature(PackageManager.FEATURE_AUDIO_LOW_LATENCY).toString()
        props["Pro Audio Support"] = pm.hasSystemFeature(PackageManager.FEATURE_AUDIO_PRO).toString()

        return props
    }

    /**
     * 针对已选的 USB 设备：遍历 UsbManager 获取 VID、PID 和 序列号
     */
    private fun getUsbDeviceSpecifics(productName: String): String {
        return try {
            val usbDevices = usbManager.deviceList
            val target = usbDevices.values.find { it.productName == productName }
            if (target != null) {
                "VID: ${String.format("%04X", target.vendorId)} " +
                        "PID: ${String.format("%04X", target.productId)} " +
                        (if (target.serialNumber != null) "SN: ${target.serialNumber}" else "")
            } else ""
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 针对已选的蓝牙设备：获取别名
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    @RequiresApi(Build.VERSION_CODES.R)
    private fun getBluetoothDeviceSpecifics(address: String): String {
        return try {
            // 需要权限：android.permission.BLUETOOTH_CONNECT
            val device = bluetoothManager?.adapter?.getRemoteDevice(address)
            if (device != null) {
                "Alias: ${device.alias ?: "No Alias"}"
            } else ""
        } catch (e: Exception) {
            ""
        }
    }
}