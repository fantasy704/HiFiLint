package top.fantasy.hifilint.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import top.fantasy.hifilint.data.db.dao.AppSettingDao
import top.fantasy.hifilint.data.db.database.AppDatabase
import top.fantasy.hifilint.service.ADBService
import top.fantasy.hifilint.service.ADBServiceImpl
import top.fantasy.hifilint.service.DeviceInfoService
import top.fantasy.hifilint.service.DeviceInfoServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDeviceInfoService(impl: DeviceInfoServiceImpl): DeviceInfoService{
        return impl;
    }

    @Provides
    @Singleton
    fun provideAppSettingDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "hifilint.db"
        ).build()
    }

    @Provides
    fun provideSettingDao(database: AppDatabase): AppSettingDao{
        return database.appSettingDao()
    }

    @Provides
    @Singleton
    fun provideADBService(impl: ADBServiceImpl): ADBService {
        return impl
    }
}