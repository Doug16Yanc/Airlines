package service

import domain.Attendant

class AttendantService {
    private fun initializeDataAttendant() : List<Attendant> {
        val attendants = mutableListOf<Attendant>()
        val attendant1 = Attendant(1L, "Douglas", "doug", "1609")
        val attendant2 = Attendant(2L, "Clara", "clara", "2910")
        val attendant3 = Attendant(3L, "Devan", "dev", "0528")

        attendants.add(attendant1)
        attendants.add(attendant2)
        attendants.add(attendant3)

        return attendants
    }

    fun doLoginAttendant() : Boolean {
        var chances : Int = 3
        println("Realize login com seu nome de usuário e senha.")

        do {
            println("Login: ")
            val login = readlnOrNull()?.toString()

            println("Senha: ")
            val password = readlnOrNull()?.toString()

            val attendants = initializeDataAttendant()

            val attendantFound = attendants.find { it.login.equals(login) && it.password.equals(password) }

            if (attendantFound != null) {
                interactesAttendant(attendantFound)
                return true
            } else {
                println("Credenciais inválidas. Tente novamente.\n")
                chances--
            }
        } while(chances > 0)
        return false
    }
    private fun interactesAttendant(attendant: Attendant) {
        println("Bem-vindo(a), caríssimo(a) ")
    }
}