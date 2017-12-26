package com.yfam.yscrumy.core.data_provider

import com.yfam.yscrumy.core.entity.Project
import com.yfam.yscrumy.core.entity.Sprint

interface SprintRepository : Repository {


    fun createUser(sprint: Sprint)
    fun getSprintByProID(id: Int): List<Sprint>
    fun getById(id: Int): Sprint?
}