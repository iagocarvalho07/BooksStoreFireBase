package com.iagocarvalho.booksstorefirebase.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iagocarvalho.booksstorefirebase.data.DataOrException
import com.iagocarvalho.booksstorefirebase.model.MBook
import com.iagocarvalho.booksstorefirebase.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val respository: FireRepository) : ViewModel() {
        val data : MutableState<DataOrException<List<MBook>, Boolean, Exception>>
        = mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        getAllBooksFromDataBase()
    }

    private fun getAllBooksFromDataBase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = respository.getAllBokksFromRepository()
            if (!data.value.data.isNullOrEmpty()) data.value.loading = false
        }
        Log.d("GEt", "getAllBooksFromDataBase: ${data.value.data?.toList().toString()}")

    }
}