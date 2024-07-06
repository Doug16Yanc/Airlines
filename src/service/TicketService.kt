package service

import domain.Armchain
import domain.Passenger
import enums.StatusArmchain

class TicketService {
    fun doTicket(passenger: Passenger?, firstClass: MutableList<Armchain>, economy: MutableList<Armchain>) {
        var chances : Int = 3
        println("Escolha a poltrona:")
        println(firstClass)
        println("\n")
        println(economy)

        do {
            println("Escolha o número:")
            var number = readln().toInt()

            val seats = firstClass + economy

            val found = seats.find { it.number == number }

            if (found != null) {
                if (found.statusArmchain == StatusArmchain.FREE) {
                    println("Poltrona $number escolhida com sucesso.\n")
                    found.statusArmchain = StatusArmchain.BUSY
                    doPaymentTicket(passenger, number)
                    break
                } else {
                    println("Poltrona $number já ocupada.\n")
                    chances--
                }
            }
            else {
                println("Número de poltrona não identificado.\n")
            }
        } while(chances > 0)
    }

    private fun doPaymentTicket(passenger: Passenger?, number: Int) {
        val price = calculatePrice(number)
        if (passenger != null) {
            println("Caro, ${passenger.name}, o valor da sua passagem é : R$ $price\n")
        }
    }

    private fun calculatePrice(number: Int) : Double {
        var price = 0.0
        if (number >= 1 && number < 26) {
            price = 2000.00
        }
        else if (number >= 26 && number < 51) {
            price = 1700.00
        }
        return price
    }
}