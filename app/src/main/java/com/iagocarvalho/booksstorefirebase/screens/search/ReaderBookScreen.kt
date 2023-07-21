package com.iagocarvalho.booksstorefirebase.screens.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.iagocarvalho.booksstorefirebase.components.InputField
import com.iagocarvalho.booksstorefirebase.components.ReaderAppBar
import com.iagocarvalho.booksstorefirebase.model.MBook
import com.iagocarvalho.booksstorefirebase.model.ModelApi.Item
import com.iagocarvalho.booksstorefirebase.navigation.ReaderScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderBookSearchScreen(
    navController: NavController,
    viewModel: BookSearchViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ReaderAppBar(
                tittle = "Search Books",
                icon = Icons.Default.ArrowBack,
                navController = navController,
                showProfile = false
            ) {
                //navController.popBackStack()
                navController.navigate(ReaderScreens.ReaderHomeScreen.name)
            }
        }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Surface() {
                Column {
                    SearchForm(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        viewModel = viewModel
                    ) { query ->
                        viewModel.serachBooks(query)

                    }
                    Spacer(modifier = Modifier.height(13.dp))
                    BookList(navController, viewModel)
                }
            }
        }
    }
}

@Composable
fun BookList(navController: NavController, viewModel: BookSearchViewModel) {
    if (viewModel.listOfBooks.value.loading == true) {
        CircularProgressIndicator()
    } else {
        Log.d("Search", "BookList: ${viewModel.listOfBooks.value.data}")

    }

    // val listOfBooks = viewModel.listOfBooks.value.data

    val listOfBooks = listOf(
        MBook("asad", "hello ", "all of us ", "algusm"),
        MBook("asad", "hasdello ", "all of us ", "algusm"),
        MBook("asad", "hasdasello ", "all of us ", "algusm"),
        MBook("asad", "heddllo ", "all of us ", "algusm"),
        MBook("asad", "asda ", "asdasd of us ", "algusm"),
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(listOfBooks) { book ->
            BookRow(book, navController)
        }
    }
}

@Composable
fun BookRow(
    book: MBook,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(7.dp),
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top
        ) {
            val imageUrl = "http://books.google.com/books/content?id=eVs4DQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            AsyncImage(
                model = imageUrl,
                contentDescription = "",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(
                        4.dp
                    )
            )
            Column() {
                Text(text = book.title, overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Author  ${book.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.titleMedium
                )
                // add more later
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: BookSearchViewModel,
    loadind: Boolean = false,
    hitn: String = "Search",
    onSerach: (String) -> Unit = {}
) {
    Column() {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) { searchQueryState.value.trim().isNotEmpty() }


        InputField(
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSerach(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }


}