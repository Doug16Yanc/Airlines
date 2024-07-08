package service

import domain.Attendant
import enums.StatusAttendant
import repository.ICalculate

class AttendantService : ICalculate {


    fun doLoginAttendant(
        attendants: Set<Attendant>
    ): Boolean {
        var chances = 3
        println("Realize login com seu nome de usuário e senha.")

        do {
            println("Login: ")
            val login = readlnOrNull()?.trim()

            println("Senha: ")
            val password = readlnOrNull()?.trim()

            if (login.isNullOrEmpty() || password.isNullOrEmpty()) {
                println("Credenciais inválidas. Tente novamente.\n")
                chances--
                continue
            }

            val attendantFound = attendants.find { it.login == login && it.password == password }

            if (attendantFound != null) {
                attendantFound.statusAttendant = StatusAttendant.ONLINE
                interactWithAttendant(attendantFound)
                return true
            } else {
                println("Credenciais inválidas. Tente novamente.\n")
                chances--
            }
        } while (chances > 0)
        return false
    }

    private fun interactWithAttendant(attendant: Attendant) {
        println("Bem-vindo(a), caríssimo(a) ${attendant.name}")
        var option: String
        do {
            println("O que desejas?")
            println(" T/t - Ver situação do transporte\n C/c - Calcular seu salário\n S/s - Sair\n O/o - Ficar offline\n")
            option = readln().trim().lowercase()

            when (option) {
                "t" -> {
                    val view = View()
                    view.showArmchainSituation()
                }
                "c" -> {
                    attendant.salary?.let { calculateValue(it, option) }
                }
                "s" -> {
                    attendant.statusAttendant = StatusAttendant.ONLINE
                    break
                }
                "o" -> {
                    attendant.statusAttendant = StatusAttendant.OFFLINE
                    break
                }
                else -> {
                    println("Opção impossível.\n")
                }
            }
        } while (option.equals('s') || option.equals('o'))
    }

    override fun calculateValue(salary : Double, option: String): Double {
        val newSalary = salary * 1.04
        println("Salário do funcionário calculado para R$ ${newSalary}.")
        return newSalary
    }
}
