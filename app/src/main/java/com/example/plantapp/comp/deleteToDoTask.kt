package com.example.plantapp.comp

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class deleteToDoTask {
    val db = Firebase.firestore

    fun deleteTask(date: String, title: String) {
        val user = Firebase.auth.currentUser ?: return
        val uid = user.uid

        val docRef = Firebase.firestore
            .collection("users")
            .document(uid)
            .collection("ToDo_Calender")
            .document(date)  // 日付がドキュメントID

        val updates = hashMapOf<String, Any>(
            title to FieldValue.delete()
        )

        docRef.update(updates)
            .addOnSuccessListener {
                Log.d("Firestore", "TODO_1 削除成功")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "削除に失敗", e)
            }
    }
}