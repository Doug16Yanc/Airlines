package service

import domain.Armchain
import domain.Attendant
import domain.Passenger
import enums.StatusPassenger
import java.util.*
import kotlin.random.Random


class PassengerService {

    private fun generateID(clientes: List<Passenger>): Int {
        var entry : Int
        val ids = clientes.map { it.id }.toSet()

        do {
            entry = Random.nextInt(1000, 10000)
        } while (entry in ids)

        return entry
    }

    fun asksAboutSystem(
        firstClass: MutableList<Armchain>,
        economy: MutableList<Armchain>,
        passengers: MutableList<Passenger>,
        attendants: Set<Attendant>
    ): String {
        println("Já tem cadastro de passagem?\n S/s - Sim\n N/n - Não\n")
        var option = readLine().toString()

        when(option.lowercase(Locale.getDefault())) {
            "s" -> {
                seeDataPassenger(firstClass, economy, passengers, attendants)
            }
            "n" -> {
                doRecordInSystem(firstClass, economy, passengers, attendants)
            }
            else -> println("Opção impossível.\n")
        }
        return option
    }
    private fun doRecordInSystem(
        firstClass: MutableList<Armchain>,
        economy: MutableList<Armchain>,
        passengers: MutableList<Passenger>,
        attendants: Set<Attendant>
    ): Passenger {
        val passenger : Passenger
        println("Faça o cadastro dos dados necessários para emissão de passagem:")

        val id = generateID(passengers)

        println("CPF : ")
        val cpf = readLine()

        println("RG : ")
        val rg = readLine()

        println("Nome : ")
        val name = readLine()

        passenger = Passenger(id, cpf, rg, name, null, null)

        passengers.add(passenger)
        println("Caro passageiro, seu número identificador é $id, caso não haja atendentes disponíveis, você irá necessitá-lo depois.\n")

        println("Escolha sua passagem:")
        val ticketService = TicketService()
        ticketService.doTicket(passenger, firstClass, economy, attendants)

        return passenger
    }
    private fun seeDataPassenger(
        firstClass: MutableList<Armchain>,
        economy: MutableList<Armchain>,
        passengers: MutableList<Passenger>,
        attendants: Set<Attendant>
    ): Passenger? {
        var foundPassenger : Passenger?
        var chances = 3

        do {
            println("Digite seu número identificador aqui: ")
            val number = readlnOrNull()?.toInt()

            foundPassenger = passengers.find { it.id == number }

            if (foundPassenger != null) {
                when(foundPassenger.status){
                    StatusPassenger.WAITING -> {
                        println("Caríssimo, ${foundPassenger.name}, vamos tentar novamente.\n")
                        val ticketService = TicketService()
                        ticketService.doTicket(foundPassenger, firstClass, economy, attendants)
                    }
                    StatusPassenger.FINISHED -> {
                        val view = View()
                        view.showDataPassenger(foundPassenger)
                    }
                    null -> TODO()
                }
                break

            } else {
                println("Número identificador não encontrado.")
                chances--
            }
        } while (chances > 0)
        return foundPassenger
    }
}