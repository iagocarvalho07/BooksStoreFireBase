package com.iagocarvalho.booksstorefirebase.network

import com.iagocarvalho.booksstorefirebase.model.ModelApi.Book
import com.iagocarvalho.booksstorefirebase.model.ModelApi.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {

    @GET("volumes")
    suspend fun getAllBokks(@Query("q") query: String): Book

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String):Item
}