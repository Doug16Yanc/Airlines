package domain

import enums.StatusAttendant

data class Attendant(
    val id: Int?,
    val name: String?,
    var login: String?,
    var password: String?,
    var salary: Double?,
    var statusAttendant: StatusAttendant?
) {}