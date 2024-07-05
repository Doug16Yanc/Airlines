package domain

import enums.TypePassenger

data class Passenger(
    val id: Int,
    val cpf: String?,
    val rg: String?,
    val name: String?,
    val type: TypePassenger?
) {}