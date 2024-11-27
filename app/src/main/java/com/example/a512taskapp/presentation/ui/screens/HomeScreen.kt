package com.example.a512taskapp.presentation.ui.screens

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.a512taskapp.datasources.services.TaskService
import com.example.a512taskapp.domain.entities.Task
import com.example.a512taskapp.domain.use_cases.SharedPref
import com.example.a512taskapp.presentation.components.TaskItem
import com.example.a512taskapp.presentation.components.TaskSummaryBox
import com.example.a512taskapp.presentation.ui.theme._512TaskAppTheme
import com.example.a512taskapp.presentation.ui.theme.yellow
import com.example.a512taskapp.presentation.ui.utils.Description
import com.example.a512taskapp.presentation.ui.utils.Logout
import com.example.a512taskapp.presentation.ui.utils.Title
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    var tasks by remember {
        mutableStateOf(emptyList<Task>())
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var completedTasks by remember {
        mutableStateOf(0)
    }
    var pendingTasks by remember {
        mutableStateOf(0)
    }
    var expiredTasks by remember {
        mutableStateOf(0)
    }
    val datePickerState = rememberDatePickerState()
    val userId = sharedPref.getUserIdSharedPref()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
       scope.launch(Dispatchers.IO) {
           try{
               val taskService = Retrofit.Builder()
                   .baseUrl("https://taskapi.juanfrausto.com/api/")
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()
                   .create(TaskService::class.java)
               isLoading = true
               val response = taskService.getTasks(userId = userId)
               
               withContext(Dispatchers.Main){
                   tasks = response.body()?.tasks ?: emptyList()
                   completedTasks = response.body()?.completedTasks ?: 0
                   pendingTasks = response.body()?.pendingTasks ?: 0
                   expiredTasks = response.body()?.expiredTasks ?: 0
                   isLoading = false
               }
               Log.i("HomeScreen",response.toString())
           }
           catch (e:Exception){
               Log.e("HomeScreenError",e.toString())
               isLoading = false
           }
       }



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
                value = completedTasks.toString(),
                color = MaterialTheme.colorScheme.secondary
            )
            TaskSummaryBox(
                title = "Pending",
                value = pendingTasks.toString(),
                color = yellow
            )
            TaskSummaryBox(
                title = "Vencidas",
                value = expiredTasks.toString(),
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
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(tasks){
                TaskItem(task = it){}
            }
        }
    }
    if(isBottomSheetVisible){
        ModalBottomSheet(onDismissRequest = { isBottomSheetVisible = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                Text(
                    text = "Nueva tarea",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text(text = "Titulo") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Title,
                            contentDescription = "title"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text(text = "Descripcion") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Description,
                            contentDescription = "Description"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                DatePicker(state = datePickerState)
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Guardar")
                }
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