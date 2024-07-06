package domain

import enums.StatusAttendant

data class Attendant(
    val id: Long,
    val name: String?,
    var login: String?,
    var password: String?,
    var statusAttendant: StatusAttendant?
) {}