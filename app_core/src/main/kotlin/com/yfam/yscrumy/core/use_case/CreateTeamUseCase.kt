package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.TeamRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.*
//import org.postgresql.util.PSQLException

class CreateTeamUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                        private val teamRepository: TeamRepository,
                        private val userRepository: UserRepository) {
    fun createTeamAndAddUser(name: String, description: String, userid: Int):Int? {
        unitOfWorkProvider.get().use {
            teamRepository.connect(it)
            userRepository.connect(it)

            if (teamRepository.getByName(name) != null) throw  TeamExistedException("Team name already exists. Please choose a different name!")
            val user = userRepository.getById(userid) ?: throw InvalidArgumentException("User does not exist")
            if (user.team != null) throw AlreadyJoinATeamException("You have already join a team")
            val team = Team()
            team.name = name
            team.description = description
            team.teamleader=user
            it.flush {
                teamRepository.createTeam(team)
                user.team = team
            }
            return team.id
        }

    }

fun getTeamById(id:Int):Team ?{
    unitOfWorkProvider.get().use {
        teamRepository.connect(it)
       return teamRepository.getById(id)
    }
}
    fun update(id:Int,description: String){
        unitOfWorkProvider.get().use {
            teamRepository.connect(it)

            var team=teamRepository.getById(id) as Team
        it.flush {
            team.description=description
        }
        }
    }
    fun delTeam(id:Int){
        unitOfWorkProvider.get().use {
            teamRepository.connect(it)
            var team=teamRepository.getById(id) as Team
            it.flush {
                try{teamRepository.delTeam(team)}
                catch (ex: Exception)
                {
                    throw ProjectExistedException("Can't remove team before remove the project!")
                }
            }
        }
    }

}