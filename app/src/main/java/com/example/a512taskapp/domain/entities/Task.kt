package com.example.a512taskapp.domain.entities

import android.icu.text.CaseMap.Title
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Task(
    val id : Int,
    val title: String,
    val description : String,
    val isDone : Boolean,
    val creationDate : String,
    val dueDate : String,
    val userId : Int
){
    val computedStatus : String get() {
        //Dia actual y due date 28/nov 29/nov
        val today = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())
        val dueDateParsed = formatter.parse(dueDate)

        return when{
            isDone -> "Completado"
            dueDateParsed != null && today.after(dueDateParsed) -> "Pendiente"
            else -> "Vencida"
        }
    }
}

/*
*
* "id": 24,
      "title": "otra",
      "description": "otra",
      "isDone": false,
      "creationDate": "2024-11-26T21:29:56.1615665",
      "dueDate": "2024-11-25T00:00:00",
      "userId": 1,
*
* */
