package com.yfam.yscrumy.core.entity

import sun.util.calendar.BaseCalendar
import java.time.LocalDate
import java.util.*

class Invitation {
    var id: Int ? =null
    var createdDate:LocalDate=LocalDate.now()
    var team:Team ?=null
    var user:User ?=null
}