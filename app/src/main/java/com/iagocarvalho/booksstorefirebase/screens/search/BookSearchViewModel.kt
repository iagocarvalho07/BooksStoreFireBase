package com.iagocarvalho.booksstorefirebase.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagocarvalho.booksstorefirebase.data.DataOrException
import com.iagocarvalho.booksstorefirebase.model.ModelApi.Item
import com.iagocarvalho.booksstorefirebase.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BooksRepository):ViewModel() {
    var listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>>
    = mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        serachBooks("android")
    }

    fun serachBooks(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()){
                return@launch
            }
            listOfBooks.value.loading = true
            listOfBooks.value = repository.getBooks(query)
            Log.d("DATAAPI", "serachBooks: ${listOfBooks.value.data.toString()}")
            if (listOfBooks.value.data.toString().isNotEmpty()) listOfBooks.value.loading = false
        }
    }

}