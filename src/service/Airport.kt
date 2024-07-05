package service

class Airport {
    fun doFirstInteraction() : Int? {
        println("Bem-vindo(a) à linha aérea Fortaleza-São Paulo")
        println("Quem é você?")
        println("1 - Atendente \n2- Passageiro\n")
        val option = readlnOrNull()?.toIntOrNull()

        when(option) {
            1 -> {
                val attendantService = AttendantService()
                attendantService.doLoginAttendant()
            }
            2 -> {
                val passengerService = PassengerService()
                passengerService.asksAboutSystem()
            }
            else -> println("Opção desconhecida.\n")
        }

        return option;
    }
}