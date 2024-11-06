package com.example.a512taskapp.solid

// 2. Principio O
// Principio de Abierto/Cerrado Open/Close

abstract class Calculadora{

}

open class CalculadoraOpen{
    fun sumar(a:Int,b:Int) : Int = a + b
    fun restar(a:Int,b:Int) : Int = a - b
}

class CalculadoraCientifica : CalculadoraOpen(){
    fun raizCuadrada(){

    }
}

fun main(){
    val cientifica = CalculadoraCientifica()
}
