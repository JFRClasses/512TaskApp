package com.example.a512taskapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a512taskapp.domain.entities.Task
import com.example.a512taskapp.presentation.ui.theme._512TaskAppTheme
import com.example.a512taskapp.presentation.ui.theme.yellow

@Composable
fun TaskItem(task:Task,onCheckedChange: (Boolean)->Unit){
    var isChecked by remember {
        mutableStateOf(task.isDone)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
             Text(
                 text = task.title,
                 style = MaterialTheme.typography.titleSmall
             )
            Text(
                text = task.computedStatus,
                style = MaterialTheme.typography.bodyMedium,
                color = if(task.computedStatus == "Completado") Color.Green
                        else if(task.computedStatus == "Vencida") Color.Red
                        else yellow
            )
        }
        Checkbox(checked = isChecked, onCheckedChange = {
            isChecked = it
            onCheckedChange(it)
        })
    }
}

@Preview
@Composable
fun TaskItemPreview(){
    _512TaskAppTheme {
        TaskItem(task = Task(
            id = 1,
            title = "Probando",
            description = "Probando",
            creationDate = "",
            dueDate = "",
            isDone = false,
            userId = 1
        )){

        }
    }
}