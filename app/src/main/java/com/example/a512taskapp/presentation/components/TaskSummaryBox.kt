package com.example.a512taskapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a512taskapp.presentation.ui.theme._512TaskAppTheme

@Composable
fun TaskSummaryBox(
    title : String,
    value : String,
    color : Color
){
    Box(modifier = Modifier
        .size(95.dp)
        .background(color, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = value.toString(),
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun TaskSummaryBoxPreview(){
    _512TaskAppTheme {
        TaskSummaryBox(title = "Completado", value = "10", color = Color.Red)
    }
}