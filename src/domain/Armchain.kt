package domain

import enums.StatusArmchain

data class Armchain(
    val number : Int?,
    var statusArmchain: StatusArmchain?,
    ) {}