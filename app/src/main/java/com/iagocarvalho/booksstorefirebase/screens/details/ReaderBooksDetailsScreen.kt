package com.iagocarvalho.booksstorefirebase.screens.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.iagocarvalho.booksstorefirebase.components.ReaderAppBar
import com.iagocarvalho.booksstorefirebase.data.Resource
import com.iagocarvalho.booksstorefirebase.model.MBook
import com.iagocarvalho.booksstorefirebase.model.ModelApi.Item
import com.iagocarvalho.booksstorefirebase.navigation.ReaderScreens
import com.iagocarvalho.booksstorefirebase.screens.home.RounderButton
import com.iagocarvalho.booksstorefirebase.screens.search.BookSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String?,
    viewModel: ReaderBooksDetailsViewModel = hiltViewModel(),
) {

    Scaffold(topBar = {
        ReaderAppBar(
            tittle = "Book Details",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) { navController.navigate(ReaderScreens.SearchScreen.name) }
    }) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) {
            Surface(
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val bookinfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                        value = viewModel.getBookInfo(BookId = bookId!!)
                    }.value

                    if (bookinfo.data == null) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            LinearProgressIndicator()
                            Text(text = "Loading....")

                        }
                    } else {
                        ShowBooksDetails(bookinfo, navController)

                    }

                }

            }


        }

    }

}

@Composable
fun ShowBooksDetails(bookinfo: Resource<Item>, navController: NavController) {

    val bookData = bookinfo.data!!.volumeInfo
    val googleBookId = bookinfo.data.id



    Card(
        modifier = Modifier.padding(34.dp),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(34.dp)
    ) {
        AsyncImage(
            model = bookData!!.imageLinks.thumbnail,
            contentDescription = "book image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(1.dp)
        )

    }
    Text(
        text = bookData.title,
        style = MaterialTheme.typography.titleLarge,
        overflow = TextOverflow.Ellipsis, maxLines = 19
    )
    Text(text = "Autores : ${bookData.authors}")
    Text(text = "Page Count : ${bookData.pageCount}")
    Text(text = "Categories : ${bookData.categories}", style = MaterialTheme.typography.bodySmall)
    Text(text = "publisher : ${bookData.publisher}")
    Spacer(modifier = Modifier.height(5.dp))

    val cleanDescription = HtmlCompat.fromHtml(
        bookData.description,
        HtmlCompat.FROM_HTML_MODE_LEGACY
    ).toString()
    val localDims = LocalContext.current.resources.displayMetrics

    Surface(
        modifier = Modifier
            .height(localDims.heightPixels.dp.times(0.09f))
            .padding(4.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.DarkGray)
    ) {
        LazyColumn(modifier = Modifier.padding(3.dp)) {
            item {
                Text(text = cleanDescription)
            }
        }

    }
    Row(modifier = Modifier.padding(top = 6.dp), horizontalArrangement = Arrangement.SpaceAround) {
        RounderButton(label = "Save"){
            // save this book to the firestore
            val book = MBook()

            saveToFireBase(book)
        }
        Spacer(modifier = Modifier.width(25.dp))
        RounderButton(label = "Cancel"){
            navController.popBackStack()
        }
    }


}

fun saveToFireBase(book: MBook) {
    val db = FirebaseFirestore.getInstance()
    TODO("Not yet implemented")
}
