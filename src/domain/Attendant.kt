package domain

data class Attendant(
    val id: Long,
    val name: String?,
    var login: String?,
    var password: String?
) {}