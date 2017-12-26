package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.ProjectRepository
import com.yfam.yscrumy.core.data_provider.TeamRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.*

class CreateProjectUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                           private val projectRepository: ProjectRepository,
                           private val userRepository: UserRepository,
                           private val createProject_MemberUsecase: CreateProject_MemberUsecase) {
    private var project: Project = Project()
    fun createProject(name: String, description: String, userid: Int) {
        unitOfWorkProvider.get().use {
            projectRepository.connect(it)
            userRepository.connect(it)

            if (projectRepository.getByName(name) != null) throw  ProjectExistedException("Project name already exists. Please choose a different name!")
            val user = userRepository.getById(userid) ?: throw InvalidArgumentException("The user doest not exist!")
            if (user.team == null) throw InvalidArgumentException("You have to Create or join into a Team first!")
            val project = Project()
            project.name = name
            project.description = description
            project.team = user.team
            project.memberSet.plus(user)
            this.project = project


            it.flush {
                projectRepository.createProject(project)
                project.team = user.team
                project.projectOwner = user

            }
        }
        createProject_MemberUsecase.createProject_Member(project.id as Int,userid,"Product Owner")
    }

    fun getCurrentProject() = this.project

}