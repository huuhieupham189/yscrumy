package com.yfam.yscrumy.core.entity

class Project {
    var id: Int? = null

    var name: String = ""
        set(value) {
            field = if (value.length < 5 || value.length > 50) throw InvalidArgumentException("Project names must be 5 to 50 characters long ")
            else value
        }

    var description: String = ""

    var team: Team? = null

    var memberSet = setOf<User>()

    var projectOwner = User()

    var status : ProjectStatus = ProjectStatus.New

}