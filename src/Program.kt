import service.Airport

fun main() {

    val airport = Airport()
    val attendants = airport.initializeDataAttendant()
    val passengers = airport.initializePassengers()
    val armchainsFirst = airport.initializeFirstClass()
    val armchainEconomy = airport.initializeEconomy()

    airport.doFirstInteraction(armchainsFirst, armchainEconomy, attendants, passengers)
}