package utils

class Utility : IUtil{
    override fun printGreen(message: String) : String {
        return "\u001B[32m$message\u001B[0m"
    }
    override fun printRed(message: String) : String {
        return "\u001B[31m$message\u001B[0m"
    }
}