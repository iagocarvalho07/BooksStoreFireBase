package com.iagocarvalho.booksstorefirebase.repository

import com.iagocarvalho.booksstorefirebase.data.DataOrException
import com.iagocarvalho.booksstorefirebase.model.ModelApi.Item
import com.iagocarvalho.booksstorefirebase.network.BooksApi
import javax.inject.Inject

class BooksRepository @Inject constructor(private val api: BooksApi) {
    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()

    private val BookInvokeDataOrExcption = DataOrException<Item, Boolean, Exception>()


    suspend fun getBooks(searchQuery: String):
            DataOrException<List<Item>, Boolean, Exception>  {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllBokks(searchQuery).items
            if (dataOrException.data!!.isNotEmpty()) dataOrException.loading = false
        }catch (e: Exception){
            dataOrException.e = e
        }
        return dataOrException
    }

    suspend fun getBookInfo(BookId: String): DataOrException<Item, Boolean, Exception>{
        val response = try {
            BookInvokeDataOrExcption.loading = true
            BookInvokeDataOrExcption.data = api.getBookInfo(bookId = BookId)
            if (BookInvokeDataOrExcption.data!!.toString().isNotEmpty()) BookInvokeDataOrExcption.loading = false
            else { }
        }catch (e: Exception){
            BookInvokeDataOrExcption.e = e

        }

        return BookInvokeDataOrExcption
    }
}