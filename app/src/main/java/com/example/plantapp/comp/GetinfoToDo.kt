package com.example.plantapp.comp

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class GetinfoToDo {
    val db = Firebase.firestore
    suspend fun getUserInfo(flag: String, selectedDate: String): List<String> {
        val user = Firebase.auth.currentUser ?: return emptyList()
        val uid = user.uid

        if (flag == "ToDo_Calender") {
            val documentId = selectedDate

            val documentSnapshot = db.collection("users")
                .document(uid)
                .collection("ToDo_Calender")
                .document(documentId)
                .get()
                .await()

            if (documentSnapshot.exists()) {
                val data = documentSnapshot.data
                Log.d("TODOO", "Document ID: ${documentSnapshot.id}, Data: $data")

                // Mapのエントリーを文字列リストに変換
                val todoList = data?.map { entry ->
                    "${entry.key} : ${entry.value}"
                } ?: emptyList()

                return todoList
            } else {
                Log.d("TODOO", "$documentId ドキュメントは存在しません")
                return emptyList()
            }
        }
        return emptyList()
    }

}