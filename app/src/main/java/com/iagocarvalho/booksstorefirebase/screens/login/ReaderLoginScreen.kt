package com.iagocarvalho.booksstorefirebase.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iagocarvalho.booksstorefirebase.R
import com.iagocarvalho.booksstorefirebase.components.EmailImput
import com.iagocarvalho.booksstorefirebase.components.HeaderLogo
import com.iagocarvalho.booksstorefirebase.components.PasswordInput
import com.iagocarvalho.booksstorefirebase.navigation.ReaderScreens


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)

    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderLogo()
            if (showLoginForm.value) UserForm(
                loadind = false,
                isCreateAcount = false
            ) { email, password ->
                //FB Login
                viewModel.SingInEmailAndPassword(email = email, password = password){
                    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                }
            }

            else {
                UserForm(loadind = false, isCreateAcount = true) { email, password ->
                //create FB Acount
                    viewModel.createUserEmailAndPassword(email, password){
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }

                }

            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = if (showLoginForm.value) "Sign Up" else "Login"
            Text(text = "New User?")
            Text(
                text = text, modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value
                    }
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loadind: Boolean = false,
    isCreateAcount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pws -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val passwordAuthentication = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keybordController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, passwordAuthentication.value) {
        email.value.trim().isNotEmpty() && passwordAuthentication.value.trim().isNotEmpty()
    }

    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(
            rememberScrollState()
        )
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAcount) Text(
            text = stringResource(id = R.string.creta_acct),
            modifier = Modifier.padding(4.dp)
        ) else Text(
            text = ""
        )
        EmailImput(
            emailState = email,
            enableb = !loadind,
            action = KeyboardActions { passwordFocusRequest.requestFocus() })
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = passwordAuthentication,
            labelId = "Passaword",
            enabled = !loadind,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid)
                    onDone(email.value.trim(), passwordAuthentication.value.trim())
            }
        )
        SubmitButton(
            textId = if (isCreateAcount) "Create Account else" else "Login",
            loadind = loadind,
            validInputs = valid
        ) {
            onDone(email.value.trim(), passwordAuthentication.value.trim())
            keybordController?.hide()
        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    loadind: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loadind && validInputs,
        shape = CircleShape
    ) {
        if (loadind) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
    }
}


