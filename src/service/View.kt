package service

import domain.Attendant
import domain.Passenger
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class View {
    fun showDataPassenger(passenger: Passenger) {
        println("   Dados do passageiro\n")
        println("   > Identificador : ${passenger.id}")
        println("\n > Nome : ${passenger.name}")
        println("\n > Registro Geral : ${passenger.rg}")
        println("\n > Tipo de passageiro : ${passenger.type}\n")
    }
    fun showTicket(passenger: Passenger, attendant: Attendant, option: String, change: Double, chain: Int) {
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

        val formatted = current.format(formatter)
        println("   Dados do passageiro\n")
        println("   > Identificador : ${passenger.id}")
        println("\n > Nome : ${passenger.name}")
        println("\n > Registro Geral : ${passenger.rg}")
        println("\n > Tipo de passageiro : ${passenger.type}\n")
        println("\n Dados da passagem\n")
        println("\n > Atendente : ${attendant.name}\n")
        println("\n > Forma de pagamento:\n")
        if(option.equals("d")){
            println("Dinheiro\n")
            println("Troco : $change")
        }
        else if (option.equals("c")){
            println("Cartão")
        }
        else if(option.equals("p")){
            println("Pix")
        }
        println("\nNúmero da poltrona : $chain")
        println("\nData e hora da emissão : $formatted")
        println("\nCódigo da operação : ${UUID.randomUUID()}")
    }
}