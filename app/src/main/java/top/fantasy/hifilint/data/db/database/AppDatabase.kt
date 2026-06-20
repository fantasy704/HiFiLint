package top.fantasy.hifilint.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import top.fantasy.hifilint.data.db.dao.AppSettingDao
import top.fantasy.hifilint.data.db.entity.AppSetting

@Database(entities = [AppSetting::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appSettingDao(): AppSettingDao
}