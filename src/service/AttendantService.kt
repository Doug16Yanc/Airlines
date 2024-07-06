package service

import domain.Armchain
import domain.Attendant
import enums.StatusAttendant
import repository.ICalculate

class AttendantService : ICalculate{
    fun initializeDataAttendant() : List<Attendant> {
        val attendants = mutableListOf<Attendant>()
        val attendant1 = Attendant(1, "Douglas", "doug", "1609", StatusAttendant.OFFLINE)
        val attendant2 = Attendant(2, "Clara", "clara", "2910", StatusAttendant.OFFLINE)
        val attendant3 = Attendant(3, "Devan", "dev", "0528", StatusAttendant.OFFLINE)

        attendants.add(attendant1)
        attendants.add(attendant2)
        attendants.add(attendant3)

        return attendants
    }

    fun doLoginAttendant(initializeFirstClass: MutableList<Armchain>, initializeEconomy: MutableList<Armchain>): Boolean {
        var chances : Int = 3
        println("Realize login com seu nome de usuário e senha.")

        do {
            println("Login: ")
            val login = readlnOrNull()?.toString()

            println("Senha: ")
            val password = readlnOrNull()?.toString()

            val attendants = initializeDataAttendant()

            val attendantFound = attendants.find { it.login.equals(login) && it.password.equals(password) }

            if (attendantFound != null) {
                attendantFound.statusAttendant = StatusAttendant.ONLINE
                interactesAttendant(attendantFound)
                return true
            } else {
                println("Credenciais inválidas. Tente novamente.\n")
                chances--
            }
        } while(chances > 0)
        return false
    }
    private fun interactesAttendant(attendant: Attendant) {
        println("Bem-vindo(a), caríssimo(a) ${attendant.name}")
        do {
            println("O que desejas?")
            println(" 1 - Ver situação do transporte\n 2- Ver relatório dos passageiros. 3 - Sair\n 4 - Ficar offline\n")
            var option = readln().toInt()

            when (option) {
                1 -> {
                    val airport = Airport()
                    val seats = airport.initializeFirstClass() + airport.initializeEconomy()
                    println(seats)
                }

                2 -> {

                }
                3 -> {
                    break
                }
                4 -> {
                    attendant.statusAttendant = StatusAttendant.OFFLINE
                    break
                }

                else -> {
                    println("Opção impossível.\n")
                }
            }
        } while(true)
    }

    override fun calculateValue(value: Double, option: String): Double {
        val amount = 0.0
        return amount
    }
}