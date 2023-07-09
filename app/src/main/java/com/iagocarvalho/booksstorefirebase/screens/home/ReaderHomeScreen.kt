package com.iagocarvalho.booksstorefirebase.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    Scaffold(
        topBar = {},
        floatingActionButton = {FABcontent{}}
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding))
    }
}

@Composable
fun FABcontent(onTap: (String) -> Unit) {
    FloatingActionButton(onClick = { }, shape = RoundedCornerShape(50.dp),) {

    }
}

