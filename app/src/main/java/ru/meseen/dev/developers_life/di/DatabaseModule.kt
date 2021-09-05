package ru.meseen.dev.developers_life.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.meseen.dev.developers_life.BuildConfig
import ru.meseen.dev.developers_life.data.db.DevLifeDataBase
import ru.meseen.dev.developers_life.data.db.dao.FeedDao
import ru.meseen.dev.developers_life.data.db.dao.FeedPageKeyDao
import javax.inject.Singleton

/**
 * @author Vyacheslav Doroshenko
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext applicationContext: Context): DevLifeDataBase =
        Room.databaseBuilder(
            applicationContext, DevLifeDataBase::class.java,
            BuildConfig.DATA_BASE_NAME
        ).build()

    @Provides
    @Singleton
    fun providePageKeyDao(dataBase: DevLifeDataBase): FeedPageKeyDao = dataBase.feedPageKeyDao()

    @Provides
    @Singleton
    fun provideResultsDao(dataBase: DevLifeDataBase): FeedDao = dataBase.feedDao()


}