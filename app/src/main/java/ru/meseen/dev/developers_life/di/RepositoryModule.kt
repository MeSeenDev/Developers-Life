package ru.meseen.dev.developers_life.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.meseen.dev.developers_life.data.api.DevLifeService
import ru.meseen.dev.developers_life.data.db.DevLifeDataBase
import ru.meseen.dev.developers_life.data.repos.DevLifeRepository
import ru.meseen.dev.developers_life.data.repos.MainRepository
import ru.meseen.dev.developers_life.ui.main.mapper.FeedMapper
import javax.inject.Singleton

/**
 * @author Vyacheslav Doroshenko
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        dataBase: DevLifeDataBase,
        service: DevLifeService,
        mapper: FeedMapper
    ): MainRepository =
        MainRepository(dataBase = dataBase, service = service, mapper = mapper)

    @Provides
    @Singleton
    fun providesDevLifeRepository(repository: MainRepository): DevLifeRepository = repository

}