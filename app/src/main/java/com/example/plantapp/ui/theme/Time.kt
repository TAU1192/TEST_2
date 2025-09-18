package com.example.plantapp.ui.theme

import android.R.color.white
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.concurrent.timer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeCount(){

    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }
    var showDialog by remember { mutableStateOf(false) }


    var count = remember { mutableStateOf(100) }
    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(Unit) {
            while(true){
                delay(1000)
                if(count.value <= 0) break
                else count.value -= 1
            }
        }
        Text(text = "count:${count.value}")
        Button(onClick = {
            showDialog = true
        }) {
            //Text("ok")
        }

        if(showDialog){
            AdvancedTimePickerExample(
                onDismiss = {
                    showDialog = false
                },
                onConfirm = {
                        time ->
                    selectedTime = time
                    showDialog = false
                },
            )
        }
        if (selectedTime != null) {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, selectedTime!!.hour)
            cal.set(Calendar.MINUTE, selectedTime!!.minute)
            cal.isLenient = false
        } else {
            Text("No time selected.")
        }
    }
}

