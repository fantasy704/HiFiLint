package top.fantasy.hifilint.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import top.fantasy.hifilint.data.db.entity.AppSetting

@Dao
interface AppSettingDao {
    @Query("SELECT * FROM app_setting")
    suspend fun getSetting(): AppSetting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSetting(setting: AppSetting)
}