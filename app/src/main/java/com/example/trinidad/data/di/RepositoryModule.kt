package com.example.trinidad.data.di

import com.example.trinidad.domain.repository.local.EquipoLegendarioRepository
import com.example.trinidad.data.repository.EquipoLegendarioRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEquipoLegendarioRepository(
        impl: EquipoLegendarioRepositoryImpl
    ): EquipoLegendarioRepository
}
