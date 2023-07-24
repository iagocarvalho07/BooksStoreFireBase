package com.iagocarvalho.booksstorefirebase.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagocarvalho.booksstorefirebase.data.Resource
import com.iagocarvalho.booksstorefirebase.model.ModelApi.Item
import com.iagocarvalho.booksstorefirebase.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderBooksDetailsViewModel @Inject constructor(private val repository: BooksRepository) :
    ViewModel() {
        suspend fun getBookInfo(BookId: String): Resource<Item>{
            return repository.getBookInfo(BookId)
        }
}