package service

import domain.Armchain
import domain.Attendant
import domain.Passenger
import enums.StatusArmchain
import enums.StatusAttendant
import enums.StatusPassenger
import enums.TypePassenger
import repository.ICalculate
import java.util.*

class TicketService : ICalculate {
    fun doTicket(
        passenger: Passenger,
        firstClass: MutableList<Armchain>,
        economy: MutableList<Armchain>,
        attendants: Set<Attendant>
    ) {
        val view = View()
        val seats = firstClass + economy
        var chances = 3
        println("\nDe 1 a 25: Primeira classe\nDe 26 a 50: Classe econômica.\n")
        println("Escolha a poltrona:")

        view.showArmchainSituation()

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
                    doPaymentTicket(passenger, found, attendants)
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

    private fun doPaymentTicket(passenger: Passenger?, found: Armchain?, attendants: Set<Attendant>) {
        val price = found?.let { calculatePrice(it) }
        if (passenger != null) {
            println("Caro, ${passenger.name}, o valor da sua passagem é : R$ $price\n")

            if (price != null) {
                processWithAttendant(passenger, attendants, price, found)
            }
        }
    }

   fun processWithAttendant(passenger: Passenger, attendants: Set<Attendant>, price : Double, chain: Armchain) {
        var available = false
        for (attendant: Attendant in attendants) {
            if (attendant.statusAttendant == StatusAttendant.ONLINE) {
                println("Identificador do atendente : ${attendant.id}")
                println("Nome do atendente : ${attendant.name}\n")
                available = true
            }
        }
        if (available == true){
            println("Digite o identificador do atendente que desejas comprovar pagamento:")
            var number = readln().toInt()

            val validate = attendants.find { it.id == number }

            if (validate != null) {
                println("Forma de pagamento:\n D/d - Dinheiro\n C/c - Cartão\n P/p - Pix\n")
                var option = readLine().toString()

                determinesPaymentMethod(passenger, validate, option, price, chain)
            }
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
        chain: Armchain
    ) {
        val attendantService = AttendantService()
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
                calculateValue(change, option)
                attendantService.calculateValue(price, option)

            }
            "c" -> {
                println(" C/c - Crédito\n D/d - Débito")
                var card = readln()

                when(card.lowercase(Locale.getDefault())){
                    "c" -> {
                        calculateValue(price, option)
                    }
                    "d" -> {
                        calculateValue(price, option)
                    }
                    else -> {
                        println("Opção impossível.\n")
                    }
                }
                attendantService.calculateValue(price, option)
            }
            "p" -> {
                println("Pagamento realizado com sucesso.\n")
                calculateValue(price, option)
                attendantService.calculateValue(price, option)
            }
            else -> {
                println("Opção impossível.\n")
            }
        }
        val view = View()
        view.showTicket(passenger, attendant, option, change, chain)
    }
    override fun calculateValue(value : Double, option : String): Double {
        var amount = 0.0
        when(option.lowercase()){
            "d" -> {
                amount = value * 1.02
            }
            "c" -> {
                amount = value * 1.03
            }
            "p" -> {
                amount = value * 1.06
            }
        }
        return amount
    }
}