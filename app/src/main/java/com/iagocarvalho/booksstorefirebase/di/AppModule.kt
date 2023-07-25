package com.iagocarvalho.booksstorefirebase.di

import com.google.firebase.firestore.FirebaseFirestore
import com.iagocarvalho.booksstorefirebase.network.BooksApi
import com.iagocarvalho.booksstorefirebase.repository.BooksRepository
import com.iagocarvalho.booksstorefirebase.repository.FireRepository
import com.iagocarvalho.booksstorefirebase.utills.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFireBookRepository() = FireRepository(queryBook = FirebaseFirestore.getInstance()
        .collection("books"))

    @Singleton
    @Provides
    fun providesBookRepository(api: BooksApi) = BooksRepository(api)

    @Singleton
    @Provides
    fun prividesBooksApi():BooksApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

}