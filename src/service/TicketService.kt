package service

import domain.Armchain
import domain.Attendant
import domain.Passenger
import enums.StatusArmchain
import enums.StatusAttendant
import repository.ICalculate
import java.util.*

class TicketService : ICalculate {
    fun doTicket(passenger: Passenger?, firstClass: MutableList<Armchain>, economy: MutableList<Armchain>) {
        var chances = 3
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

            processWithAttendant(passenger, price, number)
        }
    }

    private fun processWithAttendant(passenger: Passenger, price: Double, chain: Int) {
        val attendantService = AttendantService()

        for (attendant : Attendant in attendantService.initializeDataAttendant()) {
            if (attendant.statusAttendant == StatusAttendant.ONLINE) {
                println("Identificador do atendente : ${attendant.id}")
                println("Nome do atendente : ${attendant.name}\n")
            }
            else{
                println("Sem atendentes disponíveis. Aguarde!")
            }
        }

        println("Digite o identificador do atendente que desejas comprovar pagamento:")
        var number = readln().toInt()

        val validate = attendantService.initializeDataAttendant().find { it.id == number }

        if (validate != null) {
            println("Forma de pagamento:\n D/d - Dinheiro\n C/c - Cartão\n P/p - Pix\n")
            var option = readLine().toString()

            determinesPaymentMethod(passenger, validate, option, price, chain)
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

    private fun determinesPaymentMethod(
        passenger: Passenger,
        attendant: Attendant,
        option: String,
        price: Double,
        chain: Int
    ) {
        val attendantService = AttendantService()
        var change = 0.0
        when(option.lowercase(Locale.getDefault())){
            "d" -> {
                println("Digite, em reais, o valor entregue: ")
                var value = readln().toDouble()

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
                var card = readln().toString()

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