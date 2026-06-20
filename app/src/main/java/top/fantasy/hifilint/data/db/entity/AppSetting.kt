package top.fantasy.hifilint.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_setting")
data class AppSetting(
    @PrimaryKey
    val key: String,
    val mode: Int
)