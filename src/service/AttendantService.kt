package service

import domain.Armchain
import domain.Attendant
import enums.StatusAttendant

class AttendantService {


    fun doLoginAttendant(
        attendants: Set<Attendant>,
        armchainsFirst: MutableList<Armchain>,
        armchainEconomy: MutableList<Armchain>
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
                interactWithAttendant(attendantFound, armchainsFirst, armchainEconomy)
                return true
            } else {
                println("Credenciais inválidas. Tente novamente.\n")
                chances--
            }
        } while (chances > 0)
        return false
    }

    private fun interactWithAttendant(
        attendant: Attendant,
        firstClass: MutableList<Armchain>,
        economy: MutableList<Armchain>
    ) {
        val view = View()
        println("Bem-vindo(a), caríssimo(a) ${attendant.name}")
        var option: String
        do {
            println("O que desejas?")
            println(" T/t - Ver situação do transporte\n R/r - Ver relatório do avião\n S/s - Sair\n O/o - Ficar offline\n")
            option = readln().trim().lowercase()

            when (option) {
                "t" -> {
                    view.showArmchainSituation(firstClass, economy)
                }
                "r" -> {
                    view.showMapAirplane(firstClass, economy)
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
        } while (true)
    }

}
