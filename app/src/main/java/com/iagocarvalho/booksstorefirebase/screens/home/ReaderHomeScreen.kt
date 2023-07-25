package com.iagocarvalho.booksstorefirebase.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.iagocarvalho.booksstorefirebase.components.FABcontent
import com.iagocarvalho.booksstorefirebase.components.ReaderAppBar
import com.iagocarvalho.booksstorefirebase.model.MBook
import com.iagocarvalho.booksstorefirebase.navigation.ReaderScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            ReaderAppBar(tittle = "A.Reader", navController = navController)
        },
        floatingActionButton = { FABcontent {navController.navigate(ReaderScreens.SearchScreen.name)} }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Surface(modifier = Modifier.fillMaxSize()) {
                HomeContent(navController = navController, viewModel)

            }
        }
    }
}

@Composable
fun HomeContent(navController: NavController, viewModel: HomeScreenViewModel) {
    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()){
        listOfBooks = viewModel.data.value.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }
        Log.d("Books", "HomeContent: ${listOfBooks.toString()}")
    }
//    val listOfBooks = listOf(
//        MBook("asad", "hello ", "all of us ", "algusm"),
//        MBook("asad", "hasdello ", "all of us ", "algusm"),
//        MBook("asad", "hasdasello ", "all of us ", "algusm"),
//        MBook("asad", "heddllo ", "all of us ", "algusm"),
//        MBook("asad", "asda ", "asdasd of us ", "algusm"),
//        )

    val firebeseInstance = FirebaseAuth.getInstance()
    val currentUserName = if (!firebeseInstance.currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0) else "N/A"


    Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            Text(text = "Your reading \n " + "activity rigth now")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable { navController.navigate(ReaderScreens.ReaderStatsScreen.name) }
                        .size(45.dp), tint = MaterialTheme.colorScheme.secondary)
                Text(
                    text = currentUserName!!,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Red,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider()
            }
        }
        ReaderRinghtNowArea(book = listOfBooks, navController = navController)
        Text(text = "Reading List")
        BookListArea(listOfBooks = listOfBooks, navController = navController)


    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    HorizontalScrollComponent(listOfBooks){
    navController.navigate(ReaderScreens.UpadateScreen.name + "/$it")
    }
}

@Composable
fun HorizontalScrollComponent(listOfBooks: List<MBook>, onCardPress: (String) -> Unit = {}) {
    val scroolState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scroolState)
    ) {
        for (book in listOfBooks) {
            ListCard(book) {
                onCardPress(book.googleBookId.toString())
            }
        }
    }

}


@Composable
fun ListCard(
    book: MBook = MBook(),
    onPresDetails: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val resources = context.resources

    val displayMatrix = resources.displayMetrics
    val screenWidth = displayMatrix.widthPixels / displayMatrix.density
    val spacing = 10.dp

    Card(
        shape = RoundedCornerShape(29.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable { onPresDetails.invoke(book.title.toString()) }


    ) {
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2))) {
            Row(horizontalArrangement = Arrangement.Center) {
                AsyncImage(
                    model = book.photoURl.toString(),
                    contentDescription = "Image Book",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.width(50.dp))
                Column(
                    modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        modifier = Modifier.padding(
                            bottom = 1.dp
                        )
                    )
                    BookRating()
                }
            }
            Text(
                text = book.title.toString(),
                modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.authors.toString(),
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                RounderButton(label = "Reading", radius = 70)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RounderButton(label: String = "Reading", radius: Int = 29, onPres: () -> Unit = {}) {
    Surface(
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomEndPercent = radius,
                topStartPercent = radius
            )
        ),
        color = Color(0xFF92CBDF)
    ) {
        Column(
            modifier = Modifier
                .width(90.dp)
                .heightIn(40.dp)
                .clickable { onPres.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, style = TextStyle(color = Color.White, fontSize = 15.sp))


        }

    }

}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface(
        modifier = Modifier
            .height(70.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        shadowElevation = 50.dp,
        tonalElevation = 20.dp,

        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier.padding(4.dp)
            )
            Text(text = score.toString(), style = MaterialTheme.typography.titleSmall)


        }

    }

}


@Composable
fun ReaderRinghtNowArea(book: List<MBook>, navController: NavController) {
    ListCard()

}