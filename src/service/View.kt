package service

import domain.Armchain
import domain.Attendant
import domain.Passenger
import enums.StatusArmchain
import enums.StatusPassenger
import utils.Utility
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class View {

    fun showMapAirplane(firstClass: MutableList<Armchain>, economy: MutableList<Armchain>): List<Armchain> {
        val seats = firstClass + economy
        val busy = seats.count { it.statusArmchain == StatusArmchain.BUSY }
        val sortedSeats = seats.sortedBy { it.number }

        if(busy > 0) {
            println("Mapa do avião e seus passageiros")
            println("Poltrona\tPassageiro\t Documento do passageiro\n")
            for (armchain in sortedSeats) {
                if (armchain.statusArmchain == StatusArmchain.BUSY) {
                    println("${armchain.number} - \t${armchain.passenger?.name} - \t${armchain.passenger?.rg}\n")
                }
            }
        }
        else{
            println("Nenhum passageiro alocado ainda.\n")
        }
        return sortedSeats
    }

    fun showArmchainSituation(firstClass: MutableList<Armchain>, economy: MutableList<Armchain>): List<Armchain> {
        val utility = Utility()
        val seats = firstClass + economy
        val armchainSymbol = "\uD83D\uDCBA"

        val leftSide = seats.take(25)
        val rightSide = seats.drop(25).take(25)

        println("\nDe 1 a 25: Primeira classe\nDe 26 a 50: Classe econômica.\n")
        println("\n Verde - livre\n Vermelho - ocupado\n")

        for (i in 0 until 25) {
            val leftArmchain = leftSide.getOrNull(i)
            val rightArmchain = rightSide.getOrNull(i)

            val leftOutput = when (leftArmchain?.statusArmchain) {
                StatusArmchain.FREE -> utility.printGreen("${leftArmchain.number}${armchainSymbol}")
                StatusArmchain.BUSY -> utility.printRed("${leftArmchain.number}${armchainSymbol}")
                else -> ""
            }

            val rightOutput = when (rightArmchain?.statusArmchain) {
                StatusArmchain.FREE -> utility.printGreen("${rightArmchain.number}${armchainSymbol}")
                StatusArmchain.BUSY -> utility.printRed("${rightArmchain.number}${armchainSymbol}")
                else -> ""
            }

            println("$leftOutput \t\t $rightOutput")
        }

        return seats
    }

    fun showDataPassenger(passenger: Passenger) {
        println("   Dados do passageiro")
        println("\n > Identificador : ${passenger.id}")
        println("\n > Nome : ${passenger.name}")
        println("\n > Registro Geral : ${passenger.rg}")
        println("\n > Tipo de passageiro : ${passenger.type.toString()}\n")
    }
    fun showTicket(
        passenger: Passenger,
        attendant: Attendant,
        option: String,
        change: Double,
        chain: Armchain,
        firstClass: MutableList<Armchain>,
        economy: MutableList<Armchain>
    ) {
        passenger.status = StatusPassenger.FINISHED
        chain.statusArmchain = StatusArmchain.BUSY
        val seats = firstClass + economy
        seats.apply { chain.passenger = passenger }
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

        val formatted = current.format(formatter)
        println("   Dados do passageiro\n")
        println("\n > Identificador : ${passenger.id}")
        println("\n > Nome : ${passenger.name}")
        println("\n > Registro Geral : ${passenger.rg}")
        println("\n > Tipo de passageiro : ${passenger.type}\n")
        println("\n Dados da passagem\n")
        println("\n > Atendente : ${attendant.name}\n")
        println("\n > Forma de pagamento:\n")
        if(option.equals("d")){
            println("Dinheiro\n")
            println("Troco : $change\n")
        }
        else if (option.equals("c")){
            println("Cartão\n")
        }
        else if(option.equals("p")){
            println("Pix\n")
        }
        println("\nNúmero da poltrona : ${chain.number}")
        println("\nData e hora da emissão : $formatted")
        println("\nCódigo da operação : ${UUID.randomUUID()}\n")
        println("Faça uma feliz viagem!\n\n")
    }
}