package com.iagocarvalho.booksstorefirebase.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.iagocarvalho.booksstorefirebase.navigation.ReaderScreens

@Composable
fun HeaderLogo(modifier: Modifier = Modifier) {
    Text(
        modifier = Modifier.padding(bottom = 16.dp),
        text = "A Reader",
        style = MaterialTheme.typography.titleLarge,
        color = Color.Red.copy(alpha = 0.5f)
    )
}


@Composable
fun EmailImput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enableb: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    action: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        enabled = enableb,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = action
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
) {

    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = labelId) },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
            .padding(
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) },
        keyboardActions = onAction
    )

}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible }) {
        Icons.Default.Close

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    tittle: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onbackArrowClicked: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "LogoIcon",
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(12.dp)
                            )
                            .scale(0.6f)
                    )
                }
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable { onbackArrowClicked.invoke() })
                }
                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = tittle,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.width(150.dp))
            }
        },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(ReaderScreens.LoginScreen.name)
                }
            }) {
                if (showProfile) Row(){
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Exit App",
                        tint = Color.Red
                    )
                }

            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.LightGray)
    )


}


@Composable
fun FABcontent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        containerColor = Color.LightGray
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a book",
            tint = MaterialTheme.colorScheme.primary
        )

    }
}