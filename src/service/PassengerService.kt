package service

import domain.Armchain
import domain.Passenger
import enums.TypePassenger
import java.util.*
import kotlin.random.Random


class PassengerService {
    private fun initializePassengers() : MutableList<Passenger> {
        return mutableListOf()
    }

    private fun generateID(clientes: List<Passenger>): Int {
        var entry : Int
        val ids = clientes.map { it.id }.toSet()

        do {
            entry = Random.nextInt(1000, 10000)
        } while (entry in ids)

        return entry
    }

    fun asksAboutSystem(firstClass: MutableList<Armchain>, economy: MutableList<Armchain>): String {
        println("Já tem cadastro de passagem?\n S/s - Sim\n N/n - Não\n")
        var option = readLine().toString()

        when(option.lowercase(Locale.getDefault())) {
            "s" -> {
                doRecordInSystem(firstClass, economy)
            }
            "n" -> {
                seeDataPassenger()
            }
            else -> println("Opção impossível.\n")
        }
        return option
    }
    private fun doRecordInSystem(firstClass: MutableList<Armchain>, economy: MutableList<Armchain>): Passenger? {
        val passengers = initializePassengers()
        var passenger : Passenger? = null
        var chances : Int = 3
        println("Faça o cadastro dos dados necessários para emissão de passagem:")

        val id = generateID(initializePassengers())

        println("CPF : ")
        val cpf = readLine()

        println("RG : ")
        val rg = readLine()

        println("Nome : ")
        val name = readLine()

        do {
            println("Tipo de classe:\n P/p - Primeira classe\n E/e - Econômica\n ")
            val option = readLine().toString()

            when (option.lowercase(Locale.getDefault())) {
                "p" -> {
                    passenger = Passenger(id, cpf, rg, name, type = TypePassenger.FIRST_CLASS)
                    break
                }
                "e" -> {
                    passenger = Passenger(id, cpf, rg, name, type = TypePassenger.ECONOMY)
                    break
                }
                else -> {
                    println("Opção impossível.\n")
                    chances--
                }
            }
        } while(chances > 0)

        println("Escolha sua passagem:")
        val ticketService = TicketService()
        ticketService.doTicket(passenger, firstClass, economy)

        return passenger
    }
    private fun seeDataPassenger() : Passenger? {
        var foundPassenger : Passenger? = null
        var chances : Int = 3

        do {
            println("Digite seu número identificador aqui: ")
            var number = readln().toInt()

            foundPassenger = initializePassengers().find { it.id == number }

            if (foundPassenger != null) {
                val view = View()
                view.showDataPassenger(foundPassenger)
                break
            } else {
                println("Número identificador não encontrado.")
                chances--
            }
        } while (chances > 0)
        return foundPassenger
    }
}