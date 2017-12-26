package com.yfam.yscrumy.core.data_provider

import com.yfam.yscrumy.core.entity.Project
import com.yfam.yscrumy.core.entity.Team
import com.yfam.yscrumy.core.entity.User

interface ProjectRepository : Repository {
    fun createProject(project : Project)

    fun getByName(name:String) : Project?

    fun getById(id:Int) : Project?

    fun getAll(id: Int): List<Project>

}