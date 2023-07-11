package com.iagocarvalho.booksstorefirebase.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.iagocarvalho.booksstorefirebase.model.MUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginScreenViewModel : ViewModel() {
    //val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth
    private var currentUser = Firebase.auth.currentUser


    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun createUserEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result.user?.email?.split('@')?.get(0)
                        creatUser(displayName)
                        home()
                        Log.d(
                            "FB",
                            "createUserEmailAndPassword: conta criada  ${task.result.toString()}"
                        )
                    } else {
                        Log.d(
                            "FB",
                            "createUserEmailAndPassword: deu ruim  ${task.result.toString()}"
                        )
                    }
                    _loading.value = false
                }
        }

    }


    fun SingInEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit
    ) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    Log.d(
                        "FB",
                        "createUserEmailAndPassword: deu certo papa ${task.result.toString()}"
                    )
                    if (task.isSuccessful) {
                        //  TODO("Take the home")
                        home()
                    } else {
                        Log.d(
                            "FB",
                            "createUserEmailAndPassword: deu ruim papa ${task.result.toString()}"
                        )
                    }
                }

        } catch (exc: Exception) {
            Log.d("FB", "createUserEmailAndPassword: ${exc}")
        }
    }

    private fun creatUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user =
            MUser(
                userID = userId.toString(),
                displayName = displayName.toString(),
                avatarURL = "",
                quote = "life is gret",
                profession = "",
                id = null
            ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)

    }

    fun SingOut() {
        FirebaseAuth.getInstance().signOut()
    }

    fun sendEmailVerification() {
        currentUser!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FB", "sendEmailVerification: Email enviado com sucesso ")
                } else {
                    Log.d("FB", "sendEmailVerification: ${task.exception}")
                }
            }
    }

    fun sendPasswordResetEmail(emailCurrentUser: String) {

        Firebase.auth.sendPasswordResetEmail(emailCurrentUser.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(
                        "Fb",
                        "sendPasswordResetEmail: Email para redefinir Enviado ${task.result}"
                    )
                } else {
                    Log.d("FB", "sendPasswordResetEmail: ${task.exception}")
                }

            }
    }
}