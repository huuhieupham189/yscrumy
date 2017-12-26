package com.yfam.yscrumy.core.entity

import java.time.LocalDate


class Sprint {
    var id: Int? = null

    var name: String = ""
        set(value) {
            field = if (value.length < 5 || value.length > 50) throw InvalidArgumentException("Project names must be 5 to 50 characters long ")
            else value
        }

    var description: String = ""

    var project:Project?=null
    var sprintpbi:PBI?=null
    var startdate: LocalDate= LocalDate.now()
    var status=SprintStatus.New
    var enddate:LocalDate= LocalDate.now().plusDays(7)
    var daysleft:Int=0

}