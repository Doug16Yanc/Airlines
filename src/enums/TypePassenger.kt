package enums

enum class TypePassenger(private val description : String) {
    FIRST_CLASS("Primeira classe"),
    ECONOMY("Classe econômica");

    override fun toString(): String {
        return description
    }
}