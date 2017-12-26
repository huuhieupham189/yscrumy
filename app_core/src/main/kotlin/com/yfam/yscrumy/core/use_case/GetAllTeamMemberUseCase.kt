package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.ProjectRepository
import com.yfam.yscrumy.core.data_provider.TeamRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.entity.Project
import com.yfam.yscrumy.core.entity.User

class GetAllTeamMemberUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                              private val teamRepository: TeamRepository
) {

    open fun getAllTeamMember(team_id: Int): List<User> {
        unitOfWorkProvider.get().use {
            teamRepository.connect(it)
            return teamRepository.getAllTeamMember(team_id)

        }

    }

}