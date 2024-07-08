package utils

class Utility {
    fun printGreen(message: String) : String {
        return "\u001B[32m$message\u001B[0m"
    }
    fun printRed(message: String) : String {
        return "\u001B[31m$message\u001B[0m"
    }
}