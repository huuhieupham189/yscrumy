package com.yfam.yscrumy.core.data_provider

import com.yfam.yscrumy.core.entity.Team
import com.yfam.yscrumy.core.entity.User
import javax.jws.soap.SOAPBinding

interface UserRepository : Repository {
  fun createUser(user: User)

  fun getByUsername(username: String): User?

  fun getById(id: Int): User?
    fun searchUsername(username: String): List<User>
    fun getUserByTeamId(id: Int): List<User>
    fun searchUsernameInProject(username: String, id: Int,pjid:Int): List<User>
}