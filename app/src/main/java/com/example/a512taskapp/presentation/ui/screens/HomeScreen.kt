package com.example.a512taskapp.presentation.ui.screens

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a512taskapp.domain.use_cases.SharedPref
import com.example.a512taskapp.presentation.components.TaskSummaryBox
import com.example.a512taskapp.presentation.ui.theme._512TaskAppTheme
import com.example.a512taskapp.presentation.ui.theme.yellow
import com.example.a512taskapp.presentation.ui.utils.Logout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController,
    sharedPref: SharedPref
) {
    var isBottomSheetVisible by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Tareas",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Hola, juanfr97@hotmail.com",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            IconButton(onClick = {
                sharedPref.removeUserSharedPref()
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            }) {
                Icon(
                    imageVector = Logout,
                    contentDescription = "logout",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TaskSummaryBox(
                title = "Completadas",
                value = "10",
                color = MaterialTheme.colorScheme.secondary
            )
            TaskSummaryBox(
                title = "Completadas",
                value = "10",
                color = yellow
            )
            TaskSummaryBox(
                title = "Completadas",
                value = "10",
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lista de tareas",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            IconButton(onClick = {
                // Mostrar el Modal
                isBottomSheetVisible = true
            }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "add",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
    if(isBottomSheetVisible){
        ModalBottomSheet(onDismissRequest = { isBottomSheetVisible = false }) {
            Column {
                Text(text = "Nueva tarea")
            }
        }
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    _512TaskAppTheme {
        HomeScreen(
            innerPadding = PaddingValues(0.dp),
            navController = rememberNavController(),
            sharedPref = SharedPref(LocalContext.current)
        )
    }
}