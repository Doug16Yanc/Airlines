package service

import domain.Armchain
import enums.StatusArmchain

class Airport {
    fun initializeFirstClass() : MutableList<Armchain>{
        val armchainsFirst = mutableListOf<Armchain>()
        for (i in 1..25){
            armchainsFirst.add(Armchain(i, StatusArmchain.FREE))
        }
        return mutableListOf()
    }

    fun initializeEconomy() : MutableList<Armchain> {
        val armchainsEconomy = mutableListOf<Armchain>()

        for (i in 26..50) {
            armchainsEconomy.add(Armchain(i, StatusArmchain.FREE))
        }
        return mutableListOf()
    }
    fun doFirstInteraction() : Int? {
        println("Bem-vindo(a) à linha aérea Fortaleza-São Paulo")
        println("Quem é você?")
        println("1 - Atendente \n2- Passageiro\n")
        val option = readlnOrNull()?.toIntOrNull()

        when(option) {
            1 -> {
                val attendantService = AttendantService()
                attendantService.doLoginAttendant(initializeFirstClass(), initializeEconomy())
            }
            2 -> {
                val passengerService = PassengerService()
                passengerService.asksAboutSystem(initializeFirstClass(), initializeEconomy())
            }
            else -> println("Opção desconhecida.\n")
        }

        return option;
    }
}