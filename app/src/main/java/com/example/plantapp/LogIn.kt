package com.example.plantapp

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantapp.comp.EmailViewModel
import com.example.plantapp.comp.PasswordTextField
import com.example.plantapp.comp.ValidateInput
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


@Composable
fun LogIn(toHome: () -> Unit, toCreate: () -> Unit){
    val emailViewModel: EmailViewModel = viewModel<EmailViewModel>()
    val email = emailViewModel.email
    val passwordState = remember { TextFieldState() }

    Scaffold(modifier = Modifier.fillMaxSize()){ innerPaging ->
        Column (modifier = Modifier.padding(innerPaging)){
            Text("ログイン",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 30.sp)
            ValidateInput()
            PasswordTextField(state = passwordState)
            val password = passwordState.text.toString()
            Button(onClick = {
                signIn(email, password, onSuccess = { toHome()},
                    onFailure = {
                        Log.e("LogIn", "ログイン失敗: ${it?.message}")
                })
            }) {
                Text("LogIn")
            }
            TextButton(onClick = {toCreate()}) {
                Text("アカウント作成はこちら")
            }
        }
    }
}
private fun signIn(
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onFailure: (Exception?) -> Unit
) {
    Firebase.auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure(task.exception)
            }
        }
}




