package com.example.plantapp.ui.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?,String,String) -> Unit, //ToDoでの使用をするためのコールバック
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    var titleText by remember { mutableStateOf("") }
    var memoText by remember { mutableStateOf("") }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis,titleText,memoText)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            item {
                DatePicker(state = datePickerState)
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                OutlinedTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    label = { Text("タイトル") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = memoText,
                    onValueChange = { memoText = it },
                    label = { Text("メモ") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}