package com.iagocarvalho.booksstorefirebase.screens.update

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.iagocarvalho.booksstorefirebase.components.InputField
import com.iagocarvalho.booksstorefirebase.components.ReaderAppBar
import com.iagocarvalho.booksstorefirebase.data.DataOrException
import com.iagocarvalho.booksstorefirebase.model.MBook
import com.iagocarvalho.booksstorefirebase.screens.home.HomeScreenViewModel
import com.iagocarvalho.booksstorefirebase.screens.home.RounderButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookUpadateScreen(
    navController: NavController,
    bookItemId: String?,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(
            tittle = "Update Book",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false,

            ) { navController.popBackStack() }
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {

            val bookinfo = produceState<DataOrException<List<MBook>, Boolean, Exception>>(
                initialValue = DataOrException(data = emptyList(), true, Exception("")),
            ) { value = viewModel.data.value }.value

            Surface(
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(top = 3.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("INFO", "BookUpadateScreen: ${viewModel.data.value.data.toString()}")
                    if (bookinfo.loading == true) {
                        LinearProgressIndicator()
                        bookinfo.loading = false
                    } else {
                        Surface(
                            modifier = Modifier
                                .padding(3.dp)
                                .fillMaxWidth(),
                            shape = CircleShape,
                            tonalElevation = 40.dp
                        ) {
                            ShowBookUpdate(bookinfo = viewModel.data.value, bookItemId = bookItemId)

                        }
                        ShowSimpleForm(
                            book = viewModel.data.value.data?.first { mBook -> mBook.googleBookId == bookItemId }!!,
                            navController
                        )
                    }


                    Spacer(modifier = Modifier.padding(bottom = 15.dp))

                }
            }
        }

    }

}

@Composable
fun ShowSimpleForm(book: MBook, navController: NavController) {

    val notesText = remember { mutableStateOf("") }
    val isStartedReding = remember { mutableStateOf(false) }
    val isFinishedReading = remember { mutableStateOf(false) }
    val ratingVal = remember { mutableStateOf(0) }
    SimpleForm(
        defaultValue = if (book.notes.toString()
                .isNotEmpty()
        ) book.notes.toString() else ""
    ) { note ->
        notesText.value = note
    }
    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextButton(
            onClick = { isStartedReding.value = true },
            enabled = book.startedRaiding == null
        ) {
            if (book.startedRaiding == null) {
                if (!isStartedReding.value) {
                    Text(text = "Start Reading")
                } else {
                    Text(
                        text = "Started Reading",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            } else {
                Text(text = "Started On ${book.startedRaiding}")

            }
            Spacer(modifier = Modifier.height(4.dp))
            TextButton(
                onClick = { isFinishedReading.value = true },
                enabled = book.finisheReading == null
            ) {
                if (book.finisheReading == null) {
                    if (!isFinishedReading.value) {
                        Text(text = "Mark as read")
                    } else {
                        Text(text = "Finished Reading!")
                    }
                } else {
                    Text(text = "Finished On ${book.finisheReading}")
                }

            }
        }
    }
    Text(text = "Rating", modifier = Modifier.padding(bottom = 3.dp))
//        book.rating?.toInt().let {
//           RatingBar(rating = it!! ){rating ->
//               ratingVal.value = rating
//           }
//        }
    Row(horizontalArrangement = Arrangement.SpaceAround) {

        val chagendNote = book.notes != notesText.value
        val chagedRating = book.rating?.toInt() != ratingVal.value
        val isFinishedTimeStamp =
            if (isFinishedReading.value) Timestamp.now() else book.finisheReading
        val isStaredTimestamp =
            if (isStartedReding.value) Timestamp.now() else book.startedRaiding

        val bookToUpadate = chagendNote || chagedRating || isStartedReding.value || isFinishedReading.value
        val bookupdate = hashMapOf(
            "fineshe_reading" to isFinishedTimeStamp,
            "started_raiding" to isStaredTimestamp,
            "reating" to ratingVal,
            "notes" to notesText.value
        ).toMap()

        RounderButton(label = "Upadate") {
            if (bookToUpadate){
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.id!!)
                    .update(bookupdate)
                    .addOnCompleteListener{ task->
                        Log.d("TAG", "ShowSimpleForm:  ${task.result.toString()}")  }
                    .addOnFailureListener { task -> Log.w("ERROR", "ERROR TO UPDATE DOCUMENT : ${task.message}") }
            }


        }
        RounderButton(label = "Delete") {}
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue: String = "Great Book!",
    onSerch: (String) -> Unit
) {
    Column() {
        val TexFildValue = rememberSaveable { mutableStateOf(defaultValue) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(TexFildValue) {
            TexFildValue.value.trim().isNotEmpty()

        }

        InputField(
            modifier = Modifier
                .height(140.dp)
                .padding(3.dp)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            valueState = TexFildValue,
            labelId = "Entrer",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSerch(TexFildValue.value.trim())
                keyboardController?.hide()
            })

    }

}

@Composable
fun ShowBookUpdate(
    bookinfo: DataOrException<List<MBook>, Boolean, Exception>,
    bookItemId: String?
) {
    Row {
        Spacer(modifier = Modifier.width(43.dp))
        if (bookinfo.data != null) {
            Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center) {
                CardListItem(book = bookinfo.data!!.first { mBook ->
                    mBook.googleBookId == bookItemId
                }, onPressDetails = {})
            }
        }
    }


}

@Composable
fun CardListItem(book: MBook, onPressDetails: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp)
            .clip(
                RoundedCornerShape(20.dp)
            )
            .clickable { },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row() {
            AsyncImage(
                model = book.photoURl,
                contentDescription = "",
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp,
                            topEnd = 20.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
            )
            Column {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.authors.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(
                        start = 8.dp, end = 8.dp, top = 0.dp, bottom = 8.dp
                    )
                )

                Text(
                    text = book.publisheData.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(
                        start = 8.dp, end = 8.dp, top = 0.dp, bottom = 8.dp
                    )
                )
            }
        }

    }
}
