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

    fun showArmchainSituation() : List<Armchain>{
        val airport = Airport()
        val utility = Utility()
        val firstClass = airport.initializeFirstClass()
        val economy = airport.initializeEconomy()
        val seats = firstClass + economy
        val armchainSymbol = "\uD83D\uDCBA"
        println("\n Verde - livre\n Vermelho - ocupado\n")
        for (armchain: Armchain in seats){
            when(armchain.statusArmchain){
                StatusArmchain.FREE ->  println(utility.printGreen("${armchain.number}${armchainSymbol}"))
                StatusArmchain.BUSY ->  println(utility.printRed("${armchain.number}${armchainSymbol}"))
                null -> TODO()
            }
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
    fun showTicket(passenger: Passenger, attendant: Attendant, option: String, change: Double, chain: Armchain) {
        passenger.status = StatusPassenger.FINISHED
        chain.statusArmchain = StatusArmchain.BUSY

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