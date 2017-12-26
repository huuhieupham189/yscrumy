package com.yfam.yscrumy.core.entity

import java.time.LocalDate
import java.time.LocalTime

class DailyMeeting {
    var id: Int? = null
    var user: User?=null
    var sprint: Sprint? = null
    var descriptionYesterday: String = ""
    var descriptionToday: String = ""
    var Date: LocalDate = LocalDate.now()






}