package com.example.plantapp.ui.theme

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.plantapp.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plantapp.comp.GetInfo
import com.example.plantapp.comp.GetinfoToDo
import com.example.plantapp.comp.SetToDoTask
import com.example.plantapp.comp.deleteToDoTask
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ToDo() {
    var selectedDate by remember{ mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val currentDate: String = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd", Locale.getDefault()))

    Box (modifier = Modifier.fillMaxSize()){
        LaunchedEffect(Unit) {
            selectedDate = currentDate//最初の表示の時にリストの表示を現在の日付で行う
        }
        //カレンダー関連_____________________________________________
        Column (modifier = Modifier.fillMaxSize()){
            CalenderView(onDataSelected = {date ->
                selectedDate = date
            })

            selectedDate?.let{
                ToDoList(modifier = Modifier.padding(bottom = 100.dp),date = it)//selectedDateがnullでない時に呼び出す
            }
        }

        //ダイアログ________________________________________________
        LargeFloatingActionButton(    //<-これはToDoCalenderDiaLog.ktにある
            onClick = { showDialog = true }, // この後のif文に影響する
            shape = CircleShape,
            modifier = Modifier
                .width(70.dp)
                .height(200.dp)
                .padding(bottom = 150.dp, end = 20.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, "Large floating action button")
        }

        // DatePickerDialog を状態に応じて表示
        if (showDialog) {
            DatePickerModal(
                onDateSelected = { millis,title,memo ->
                    if (millis != null) {
                        val sdf = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())
                        val formattedDate = sdf.format(Date(millis))
                        Log.d("DatePicker", "選択された日付: $formattedDate")

                        val setTask = SetToDoTask()
                        setTask.SetTask(formattedDate, title, memo)


                        selectedDate = formattedDate
                    } else {
                        Log.d("DatePicker", "日付が選択されませんでした")
                    }
                },
                onDismiss = { showDialog = false }
            )
        }




    }
}

@Composable
fun ToDoList(modifier: Modifier,date: String){
    var todo by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(date) {  //dateが変更されるたびに実行
        val getInfo = GetinfoToDo()
        val calenderTodo = getInfo
        todo = calenderTodo.getUserInfo("ToDo_Calender",date)
    }


    LazyColumn(modifier.fillMaxWidth()) {
        items(todo) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var checked by remember(item) { mutableStateOf(false) } // itemごとに rememberを作成

                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = it

                        if (it) {
                            val key = item.split(" : ")[0]
                            val deleteTask = deleteToDoTask()
                            deleteTask.deleteTask(date, key)


                            todo = todo.filterNot { task -> task == item }//todoの中身が変更され、自動で再描画される
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun CalenderView(onDataSelected: (String) -> Unit){
    AndroidView(
        factory = { context ->
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.layout_calendar, null)

            val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
            calendarView.setOnDateChangeListener { _, selectYear, selectMonth, selectDay ->
                if(selectMonth + 1 <= 9){
                    if(selectDay <= 9){
                        val date = "${selectYear}_0${selectMonth + 1}_0$selectDay"
                        onDataSelected(date)
                        Log.d("TAG",date)
                    }else{
                        val date = "${selectYear}_0${selectMonth + 1}_$selectDay"
                        onDataSelected(date)
                        Log.d("TAG",date)
                    }
                }else{
                    val date = "${selectYear}_${selectMonth + 1}_$selectDay"
                    onDataSelected(date)
                    Log.d("TAG",date)
                }




            }
            view
        }
    )
}

