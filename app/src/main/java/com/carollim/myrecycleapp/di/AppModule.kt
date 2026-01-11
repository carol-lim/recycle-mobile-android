package com.carollim.myrecycleapp.di

import com.carollim.myrecycleapp.data.repository.AuthRepositoryImpl
import com.carollim.myrecycleapp.data.repository.CatalogRepositoryImpl
import com.carollim.myrecycleapp.domain.repository.AuthRepository
import com.carollim.myrecycleapp.domain.repository.CatalogRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideCatalogRepository(impl: CatalogRepositoryImpl): CatalogRepository {
        return impl
    }
}
