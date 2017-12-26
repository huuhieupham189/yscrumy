package com.yfam.yscrumy.core.data_provider

import com.yfam.yscrumy.core.entity.Team
import com.yfam.yscrumy.core.entity.User

interface TeamRepository : Repository {
    fun createTeam(team : Team)

    fun getByName(name:String) : Team?

    fun getById(id:Int) : Team?

    fun getAllTeamMember(team_id: Int) : List<User>

    fun delTeam(team:Team)
}