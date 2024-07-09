package service

import domain.Armchain
import domain.Attendant
import domain.Passenger
import enums.StatusArmchain
import enums.StatusAttendant

class Airport {
    fun initializeDataAttendant(): Set<Attendant> {
        return setOf(
            Attendant(1, "Douglas", "doug", "1609", 1600.00, StatusAttendant.OFFLINE),
            Attendant(2, "Clara", "clara", "2910", 1600.00, StatusAttendant.OFFLINE),
            Attendant(3, "Devan", "dev", "0528", 1600.00, StatusAttendant.OFFLINE)
        )
    }
    fun initializePassengers() : MutableList<Passenger> {
        val passengers = mutableListOf<Passenger>()
        return passengers
    }

    fun initializeFirstClass() : MutableList<Armchain>{
        val armchainsFirst = mutableListOf<Armchain>()
        for (i in 1..25){
            armchainsFirst.add(Armchain(i, StatusArmchain.FREE, null))
        }
        return armchainsFirst
    }

    fun initializeEconomy() : MutableList<Armchain> {
        val armchainsEconomy = mutableListOf<Armchain>()

        for (i in 26..50) {
            armchainsEconomy.add(Armchain(i, StatusArmchain.FREE, null))
        }
        return armchainsEconomy
    }
    fun doFirstInteraction(
        armchainsFirst: MutableList<Armchain>,
        armchainEconomy: MutableList<Armchain>,
        attendants: Set<Attendant>,
        passengers: MutableList<Passenger>
    ): Int {

        var option : Int

        do {
            println("Bem-vindo(a) à linha aérea Fortaleza-São Paulo")
            println("Quem é você?")
            println("1 - Atendente \n2 - Passageiro\n3 - Sair\n")
            option = readlnOrNull()?.toIntOrNull()!!

            when (option) {
                1 -> {
                    val attendantService = AttendantService()
                    attendantService.doLoginAttendant(attendants, armchainsFirst, armchainEconomy)
                }

                2 -> {
                    val passengerService = PassengerService()
                    passengerService.asksAboutSystem(armchainsFirst, armchainEconomy, passengers, attendants)
                }
                3 -> {println("Tchau")
                    System.exit(0)}

                else -> println("Opção desconhecida.\n")
            }

        } while(true)
        return option;
    }
}