package com.example.a512taskapp.solid

// 3. L
// Principio de Sustitucion de

open class Figura{
    open fun calcularArea() : Double = 0.0
}

class Triangulo(val base: Double, val altura: Double) : Figura(){
    override fun calcularArea(): Double {
        return base * altura /2
    }
}

class Rectangulo(val base : Double, val altura : Double) : Figura(){
    override fun calcularArea(): Double {
        return base * altura
    }
}

fun main(){
    val triangulo = Triangulo(2.0,3.0)
    val rectangulo = Triangulo(2.0,3.0)
    imprimirArea(rectangulo)
}

fun imprimirArea(figura:Figura){
    println(figura.calcularArea())
}