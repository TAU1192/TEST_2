package com.example.plantapp.comp

import android.R.attr.data
import android.content.ContentValues.TAG
import android.util.Log
import android.util.Log.e
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await



class GetInfo {
    var info: Any? = null
    suspend fun getUserInfo(flag: String): Any? {
        val user = Firebase.auth.currentUser ?: return null
        val uid = user.uid
        val docRef = Firebase.firestore.collection("users").document(uid)

        val document = docRef.get().await()
        val data = document.data
        info = data?.get(flag)
        if(info != null) {
            return info
        }else{
            return "データが見つかりません"
        }

    }
}



