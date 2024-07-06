package repository

import java.util.Objects

interface ICalculate {
    fun calculateValue(value : Double, option : String) : Double
}