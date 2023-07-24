package com.iagocarvalho.booksstorefirebase.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagocarvalho.booksstorefirebase.data.Resource
import com.iagocarvalho.booksstorefirebase.model.ModelApi.Item
import com.iagocarvalho.booksstorefirebase.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BooksRepository):ViewModel() {
    var listOfBooks: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)

    init {
        loadBooks()
    }

    private fun loadBooks() {
        serachBooks("Android")
    }

    fun serachBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                return@launch
            }
            try {when(val response = repository.getBooks(query)){
                is Resource.Success ->{
                    listOfBooks = response.data!!
                    if (listOfBooks.isNotEmpty()) isLoading = false
                }
                is Resource.Error -> {
                    isLoading = false
                    Log.d("NetworkView", "serachBooks: Falhou em achar os livros ")
                }
                else -> {isLoading = false} }
            }catch (execption: Exception){
                isLoading = false
                Log.d("NetworkView", "serachBooks: ${execption.message}")

            }


        }
    }

}