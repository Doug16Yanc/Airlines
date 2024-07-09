package domain

import enums.StatusPassenger
import enums.TypePassenger

data class Passenger(
    val id: Int,
    val cpf: String?,
    var rg: String?,
    var name: String?,
    var type: TypePassenger?,
    var status : StatusPassenger?
) {}