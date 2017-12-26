package com.yfam.yscrumy.core.entity

class Team {
    var id: Int? = null

    var name: String = ""
        set(value) {
            field = if (value.length < 5 || value.length > 50) throw InvalidArgumentException("Team names must be 5 to 50 characters long ")
            else value
        }

    var description: String = ""
    var teamleader : User? =null

}