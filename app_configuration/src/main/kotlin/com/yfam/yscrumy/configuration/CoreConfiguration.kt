package com.yfam.yscrumy.configuration

import com.yfam.yscrumy.core.data_provider.*
import com.yfam.yscrumy.core.use_case.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class CoreConfiguration {
  @Bean
  open fun loginUseCase(unitOfWorkProvider: UnitOfWorkProvider,
                        userRepository: UserRepository) = LoginUseCase(unitOfWorkProvider, userRepository);

  @Bean
  open fun registerUseCase(unitOfWorkProvider: UnitOfWorkProvider,
                           userRepository: UserRepository) = RegisterUseCase(unitOfWorkProvider, userRepository);

  @Bean
  open fun createTeamUseCase(unitOfWorkProvider: UnitOfWorkProvider,
                             teamRepository: TeamRepository, userRepository: UserRepository) = CreateTeamUseCase(unitOfWorkProvider, teamRepository,userRepository);

  @Bean
  open fun createProjectUseCase(unitOfWorkProvider: UnitOfWorkProvider,
                                projectRepository: ProjectRepository, userRepository: UserRepository,createProject_MemberUsecase: CreateProject_MemberUsecase) = CreateProjectUseCase(unitOfWorkProvider, projectRepository,userRepository,createProject_MemberUsecase)

    @Bean
    open fun createPBIUseCase(unitOfWorkProvider: UnitOfWorkProvider,
                                  projectRepository: ProjectRepository, pbiRepository: PBIRepository) = CreatePBIUseCase(unitOfWorkProvider, projectRepository,pbiRepository)
    @Bean
    open fun getAllProjectUseCase(unitOfWorkProvider: UnitOfWorkProvider,
                                  projectRepository: ProjectRepository) = GetAllProjectUseCase(unitOfWorkProvider, projectRepository)

  @Bean
    open fun getAllTeamMemberUseCase(unitOfWorkProvider: UnitOfWorkProvider,
                                     teamRepository: TeamRepository) = GetAllTeamMemberUseCase(unitOfWorkProvider, teamRepository)

    @Bean
    open fun getAndEditProfileUseCase(unitOfWorkProvider: UnitOfWorkProvider,userRepository: UserRepository) = GetAndEditProfileUseCase(unitOfWorkProvider,userRepository)
    @Bean
    open fun getAllPBIUseCase(unitOfWorkProvider: UnitOfWorkProvider,
                              pbiRepository: PBIRepository) = GetAllPBIUseCase(unitOfWorkProvider,pbiRepository)
    @Bean
    open fun editPBIUseCase(unitOfWorkProvider: UnitOfWorkProvider,
                              pbiRepository: PBIRepository) = EditPBIUseCase(unitOfWorkProvider,pbiRepository)
    @Bean
    open fun delPBIUsecase(unitOfWorkProvider: UnitOfWorkProvider,pbiRepository: PBIRepository)=DelPBIUsecase(unitOfWorkProvider,pbiRepository)
    @Bean
    open fun editProfileUsecase(unitOfWorkProvider: UnitOfWorkProvider,userRepository: UserRepository,teamUseCase: CreateTeamUseCase) = EditProfileUseCase(unitOfWorkProvider,userRepository,teamUseCase)
    @Bean
    open fun invitationUsecase(unitOfWorkProvider: UnitOfWorkProvider,invitationRepository: InvitationRepository) = InvitationUsecase(unitOfWorkProvider,invitationRepository)
    @Bean
    open fun project_MemberUsecase(unitOfWorkProvider: UnitOfWorkProvider,project_memberRepository: Project_memberRepository) = CreateProject_MemberUsecase(unitOfWorkProvider,project_memberRepository)
    @Bean
    open fun createsprintUsecase(unitOfWorkProvider: UnitOfWorkProvider,sprintRepository: SprintRepository,pbiRepository: PBIRepository) = CreateSprintUseCase(unitOfWorkProvider,sprintRepository,pbiRepository)
    @Bean
    open fun createtaskUsecase(unitOfWorkProvider: UnitOfWorkProvider,sprintRepository: SprintRepository,userRepository: UserRepository,taskRepository: TaskRepository) = CreateTaskUsecase(unitOfWorkProvider,taskRepository,userRepository, sprintRepository)
    @Bean
    open fun gettaskUsecase(unitOfWorkProvider: UnitOfWorkProvider,taskRepository: TaskRepository,sprintRepository: SprintRepository,userRepository: UserRepository) = GetTaskUsecase(unitOfWorkProvider,taskRepository,userRepository,sprintRepository)

}
