package top.fantasy.hifilint.data.model

data class DeviceInfo(
    val manufacturer: String,
    val model: String,
    val device: String,
    val product: String,
    val hardware: String,
    val board: String,
    val androidVersion: String,
    val sdkLevel: Int
)
