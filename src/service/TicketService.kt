package service

import domain.Armchain
import domain.Attendant
import domain.Passenger
import enums.StatusArmchain
import enums.StatusAttendant
import enums.StatusPassenger
import enums.TypePassenger
import java.util.*

class TicketService {
    fun doTicket(
        passenger: Passenger,
        firstClass: MutableList<Armchain>,
        economy: MutableList<Armchain>,
        attendants: Set<Attendant>
    ) {
        val view = View()
        val seats = firstClass + economy
        var chances = 3
        println("Escolha a poltrona:")

        view.showArmchainSituation(firstClass, economy)

        do {
            println("Escolha o número:")
            var number = readln().toInt()

            val found = seats.find { it.number == number }

            if (found != null) {
                if (found.statusArmchain == StatusArmchain.FREE) {
                    println("Poltrona $number escolhida com sucesso.\n")
                    if (number in 1..25){
                        passenger.type = TypePassenger.FIRST_CLASS
                    }
                    else if (number in 26..50){
                        passenger.type = TypePassenger.ECONOMY
                    }
                    doPaymentTicket(passenger, found, attendants, firstClass, economy)
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

    private fun doPaymentTicket(
        passenger: Passenger?,
        found: Armchain?,
        attendants: Set<Attendant>,
        firstClass: MutableList<Armchain>,
        economy: MutableList<Armchain>
    ) {
        val price = found?.let { calculatePrice(it) }
        if (passenger != null) {
            println("Caro, ${passenger.name}, o valor da sua passagem é : R$ $price\n")

            if (price != null) {
                processWithAttendant(passenger, attendants, price, found, firstClass, economy)
            }
        }
    }

   fun processWithAttendant(
       passenger: Passenger,
       attendants: Set<Attendant>,
       price: Double,
       chain: Armchain,
       firstClass: MutableList<Armchain>,
       economy: MutableList<Armchain>
   ) {
        var chances = 3
        var available = false
        for (attendant: Attendant in attendants) {
            if (attendant.statusAttendant == StatusAttendant.ONLINE) {
                println("Identificador do atendente : ${attendant.id}")
                println("Nome do atendente : ${attendant.name}\n")
                available = true
            }
        }
        if (available){
            do {
                println("Digite o identificador do atendente que desejas comprovar pagamento:")
                val number = readln().toInt()

                val validate = attendants.find { it.id == number }

                if (validate != null) {
                    println("Forma de pagamento:\n D/d - Dinheiro\n C/c - Cartão\n P/p - Pix\n")
                    val option = readLine().toString()
                    determinesPaymentMethod(passenger, validate, option, price, chain, firstClass, economy)
                    break
                } else {
                    println("\n\nIdentificador de atendente inválido, tente novamente.\nVocê tem mais duas chances, se errar em todas, voltará ao início.\n\n")
                    passenger.status = StatusPassenger.WAITING
                    chances--
                }
            } while (chances > 0)
        }
        else {
            println("Sem atendentes disponíveis, aguarde.\n")
            passenger.status = StatusPassenger.WAITING
            return
        }

    }
    private fun calculatePrice(chain: Armchain) : Double {
        var price = 0.0
        if (chain.number in 1..26) {
            price = 2000.00
        }
        else if (chain.number in 26..50) {
            price = 1700.00
        }
        return price
    }

    private fun determinesPaymentMethod(
        passenger: Passenger,
        attendant: Attendant,
        option: String,
        price: Double,
        chain: Armchain,
        firstClass: MutableList<Armchain>,
        economy: MutableList<Armchain>
    ) {
        var change = 0.0
        when(option.lowercase(Locale.getDefault())){
            "d" -> {
                println("Digite, em reais, o valor entregue: ")
                val value = readln().toDouble()

                if (value > price) {
                    println("Devolvidos R$ ${value - price}.\nPassagem sendo processada.\n")
                    change = value - price
                }
                else if (value < price) {
                    println("Valor insuficiente.")
                }
                else {
                    println("Perfeito, passagem sendo processada.")
                }
            }
            "c" -> {
                println(" C/c - Crédito\n D/d - Débito")
                var card = readln()

                when(card.lowercase(Locale.getDefault())){
                    "c" -> {
                        println("Crédito escolhido com sucesso.\n")
                    }
                    "d" -> {
                        println("Débito escolhido com sucesso.\n")
                    }
                    else -> {
                        println("Opção impossível.\n")
                    }
                }
            }
            "p" -> {
                println("Pagamento realized com sucesso.\n")
            }
            else -> {
                println("Opção impossível.\n")
            }
        }
        val view = View()
        view.showTicket(passenger, attendant, option, change, chain, firstClass, economy)
    }
}