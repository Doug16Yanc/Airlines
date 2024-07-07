package service

import domain.Armchain
import domain.Attendant
import enums.StatusAttendant
import repository.ICalculate

class AttendantService : ICalculate {

    private val attendants = initializeDataAttendant()

    fun initializeDataAttendant(): Set<Attendant> {
        return setOf(
            Attendant(1, "Douglas", "doug", "1609", 1600.00, StatusAttendant.OFFLINE),
            Attendant(2, "Clara", "clara", "2910", 1600.00, StatusAttendant.OFFLINE),
            Attendant(3, "Devan", "dev", "0528", 1600.00, StatusAttendant.OFFLINE)
        )
    }

    fun doLoginAttendant(initializeFirstClass: MutableList<Armchain>, initializeEconomy: MutableList<Armchain>): Boolean {
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
                interactWithAttendant(attendantFound, initializeFirstClass, initializeEconomy)
                return true
            } else {
                println("Credenciais inválidas. Tente novamente.\n")
                chances--
            }
        } while (chances > 0)
        return false
    }

    private fun interactWithAttendant(attendant: Attendant, firstClass: MutableList<Armchain>, economy: MutableList<Armchain>) {
        println("Bem-vindo(a), caríssimo(a) ${attendant.name}")
        var option: String
        do {
            println("O que desejas?")
            println(" T/t - Ver situação do transporte\n C/c - Calcular seu salário\n S/s - Sair\n O/o - Ficar offline\n")
            option = readln().trim().lowercase()

            when (option) {
                "t" -> {
                    val seats = firstClass + economy
                    println(seats)
                }
                "c" -> {
                    attendant.salary?.let { calculateValue(it, option) }
                }
                "s" -> {
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
        } while (true)
    }

    override fun calculateValue(salary : Double, option: String): Double {
        val newSalary = salary * 1.04
        println("Salário do funcionário calculado para R$ ${newSalary}.")
        return newSalary
    }
}
