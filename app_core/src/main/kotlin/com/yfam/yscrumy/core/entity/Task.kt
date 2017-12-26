package com.yfam.yscrumy.core.entity

import java.time.LocalDate
import java.time.LocalTime

class Task {
    var id: Int? = null

    var name: String = ""
        set(value) {
            field = if (value.length < 5 || value.length > 50) throw InvalidArgumentException("Project names must be 5 to 50 characters long ")
            else value
        }

    var description: String = ""
    var dueDate: LocalDate = LocalDate.now()
    var status = TaskStatus.New
    var priority = PriorityTask.Medium
    var estimation : LocalTime = LocalTime.of(1,0)
    var sprint: Sprint? = null
    var assignTo: User? = null
    var tester:User?=null


}