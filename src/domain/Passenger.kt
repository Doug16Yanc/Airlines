package domain

import enums.StatusPassenger
import enums.TypePassenger

data class Passenger(
    val id: Int,
    val cpf: String?,
    val rg: String?,
    val name: String?,
    var type: TypePassenger?,
    var status : StatusPassenger?
) {}