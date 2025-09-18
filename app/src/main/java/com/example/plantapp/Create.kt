package com.example.plantapp

import android.R.attr.name
import android.R.attr.password
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantapp.comp.EmailViewModel
import com.example.plantapp.comp.PasswordTextField
import com.example.plantapp.comp.ValidateInput
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.security.Timestamp
import java.util.Date

@Composable
fun Create(toLogIn: () -> Unit){
    val emailViewModel: EmailViewModel = viewModel<EmailViewModel>()
    val email = emailViewModel.email
    val passwordState = remember { TextFieldState() }
    val name = rememberSaveable { mutableStateOf("") }
    Scaffold(modifier = Modifier.fillMaxSize()){ innerPaging ->
        Column (modifier = Modifier.padding(innerPaging)){
            Text("アカウント作成",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                fontSize = 30.sp)

            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = {Text("Name")},
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
            )
            ValidateInput()
            PasswordTextField(state = passwordState)
            val password1 = passwordState.text.toString()

            Button(onClick = {
                    Log.d("create","${name}")
                    createAcc(
                        name.value.toString(),email, password1, onSuccess = { toLogIn() },
                        onFailure = {
                            Log.e("Create", "アカウント作成失敗: ${it?.message}")
                        })
            }) {
                Text("アカウント作成")
            }
            TextButton(onClick = {toLogIn()}) {
                Text("すでにアカウントをお持ちの方はこちら")
            }
        }
    }
}
private fun createAcc(
    name: String,
    email: String,
    password: String,
    onSuccess: () -> Unit,  //成功したときのラムダ式
    onFailure: (Exception?) -> Unit  //失敗したときのラムダ式
) {
    Firebase.auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
                val currentUser = Firebase.auth.currentUser
                currentUser?.let {
                    val uid = it.uid
                    val user = hashMapOf(
                        "born" to 2008,
                        "country" to "JAPAN",
                        "Name" to name
                    )
                    Firebase.firestore.collection("users").document(uid)
                        .set(user)
                        .addOnSuccessListener {
                            Log.d(
                                "HOME",
                                "DocumentSnapshot successfully written!"
                            )
                        }
                        .addOnFailureListener { e -> Log.w("HOME", "Error writing document", e) }
                    val todo = hashMapOf(
                        "Time" to " "
                    )
                    Firebase.firestore.collection("users").document(uid).collection("ToDo_Calender").document("test")
                        .set(todo)
                        .addOnSuccessListener {
                            Log.d(
                                "HOME",
                                "DocumentSnapshot successfully written!"
                            )
                        }
                        .addOnFailureListener { e -> Log.w("HOME", "Error writing document", e) }
                }
            } else {
                onFailure(task.exception)
            }
        }
}

