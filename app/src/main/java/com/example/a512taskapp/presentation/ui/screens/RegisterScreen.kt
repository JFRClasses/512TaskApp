package com.example.a512taskapp.presentation.ui.screens

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a512taskapp.R
import com.example.a512taskapp.datasources.services.AuthService
import com.example.a512taskapp.domain.dtos.AuthDto
import com.example.a512taskapp.domain.use_cases.SharedPref
import com.example.a512taskapp.presentation.ui.theme._512TaskAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun RegisterScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController = rememberNavController(),
    sharedPref: SharedPref
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    val isButtonEnabled = email.isNotEmpty()
            && password.isNotEmpty()
            && confirmPassword.isNotEmpty()
            && password == confirmPassword
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "register",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Correo Electronico") },
            shape = RoundedCornerShape(24.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Contraseña") },
            shape = RoundedCornerShape(24.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Confirmar contraseña") },
            shape = RoundedCornerShape(24.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            enabled = isButtonEnabled,
            onClick = {
                scope.launch(Dispatchers.IO) {
                    val authService = Retrofit.Builder()
                        .baseUrl("https://taskapi.juanfrausto.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(AuthService::class.java)
                    val authDto = AuthDto(email = email, password = password)
                    val response = authService.registerUser(authDto)
                    Log.i("RegisterScreenAPI",response.toString())
                    if(response.code() == 200){
                        if(response.body()?.isLogged == true){
                            //El usuario se logueeo correctamente
                            withContext(Dispatchers.Main){
                                navController.navigate("home")
                            }
                        }
                    }
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Registrarse")
        }
        if(password != confirmPassword){
            Text(
                text = "Las constraseñas no coinciden",
                color = Color.Red,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RegisterScreenPreview() {
    _512TaskAppTheme {
        RegisterScreen(
            innerPadding = PaddingValues(0.dp),
            navController = rememberNavController(),
            sharedPref = SharedPref(LocalContext.current)
        )
    }
}