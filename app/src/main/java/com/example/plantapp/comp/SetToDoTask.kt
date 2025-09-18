package com.example.plantapp.comp

import android.R.attr.data
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class SetToDoTask{
    val db = Firebase.firestore
    fun SetTask(date:String,title:String,memo:String){
        val user = Firebase.auth.currentUser

        user?.let {
            val uid = user.uid
            val data = hashMapOf(
                title to memo,
            )
            val washingtonRef = db.collection("users").document(uid)
            db.collection("users")
                .document(uid)
                .collection("ToDo_Calender")
                .document(date)
                .set(data, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("SetToDoTask", "Task added successfully for $date")
                }
                .addOnFailureListener { e ->
                    Log.e("SetToDoTask", "Error adding task", e)
                }
            washingtonRef
                .update("ToDo_Calender", true)

            }







    }
}