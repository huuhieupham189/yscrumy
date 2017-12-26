package com.yfam.yscrumy.configuration

import com.yfam.yscrumy.core.use_case.*
import com.yfam.yscrumy.entry_point.EntryPointBootstrap
import com.yfam.yscrumy.entry_point.web.*
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class WebEntryPointConfiguration {
    @Bean
    open fun webEntryPointConfig()
            = WebEntryPointConfig(System.getenv("APP_PORT")!!.toInt())

    @Bean
    open fun embeddedServletContainerCustomizer(config: WebEntryPointConfig) = EmbeddedServletContainerCustomizer { container ->
        container.setPort(config.appPort)
    }

    @Bean
    open fun webEntryPointBootstrap(): EntryPointBootstrap = WebEntryPointBootstrap();

    @Bean
    open fun userController(loginUseCase: LoginUseCase, registerUseCase: RegisterUseCase, getAndEditProfileUseCase: GetAndEditProfileUseCase, editProfileUseCase: EditProfileUseCase, createTeamUseCase: CreateTeamUseCase, invitationUsecase: InvitationUsecase)
            = UserController(loginUseCase, registerUseCase, getAndEditProfileUseCase, editProfileUseCase, createTeamUseCase, invitationUsecase)

    @Bean
    open fun teamController(createTeamUseCase: CreateTeamUseCase, invitationUsecase: InvitationUsecase, editProfileUseCase: EditProfileUseCase, getAllProjectUseCase: GetAllProjectUseCase) = TeamController(createTeamUseCase, invitationUsecase, editProfileUseCase, getAllProjectUseCase)

    @Bean
    open fun projectController(createProjectUseCase: CreateProjectUseCase, getAllProjectUseCase: GetAllProjectUseCase, getAllTeamMemberUseCase: GetAllTeamMemberUseCase, createTeamUseCase: CreateTeamUseCase, createSprintUseCase: CreateSprintUseCase, getAllPBIUseCase: GetAllPBIUseCase, createProject_MemberUsecase: CreateProject_MemberUsecase) = ProjectController(createProjectUseCase, getAllProjectUseCase, getAllTeamMemberUseCase, createTeamUseCase, createSprintUseCase, getAllPBIUseCase, createProject_MemberUsecase)

    @Bean
    open fun dashboardController(getAllTeamMemberUseCase: GetAllTeamMemberUseCase, getAllProjectUseCase: GetAllProjectUseCase, getAndEditProfileUseCase: GetAndEditProfileUseCase, createTeamUseCase: CreateTeamUseCase,getTaskUsecase: GetTaskUsecase) = DashboardController(getAllTeamMemberUseCase, getAllProjectUseCase, getAndEditProfileUseCase, createTeamUseCase,getTaskUsecase)

    @Bean
    open fun pbiController(createPBIUseCase: CreatePBIUseCase, getAllPBIUseCase: GetAllPBIUseCase, editPBIUseCase: EditPBIUseCase, delPBIUsecase: DelPBIUsecase, getAllTeamMemberUseCase: GetAllTeamMemberUseCase, getAndEditProfileUseCase: GetAndEditProfileUseCase, createProject_MemberUsecase: CreateProject_MemberUsecase, createSprintUseCase: CreateSprintUseCase, getAllProjectUseCase: GetAllProjectUseCase) = PBIController(createPBIUseCase, getAllPBIUseCase, editPBIUseCase, delPBIUsecase, getAllTeamMemberUseCase, getAndEditProfileUseCase, createProject_MemberUsecase, getAllProjectUseCase, createSprintUseCase)

    @Bean
    open fun invitationController(invitationUsecase: InvitationUsecase, createTeamUseCase: CreateTeamUseCase, editProfileUseCase: GetAndEditProfileUseCase) = InvitationController(invitationUsecase, createTeamUseCase, editProfileUseCase)

    @Bean
    open fun taskBoardController(createTaskUsecase: CreateTaskUsecase,getTaskUsecase: GetTaskUsecase,createProject_MemberUsecase: CreateProject_MemberUsecase,getAndEditProfileUseCase: GetAndEditProfileUseCase,createSprintUseCase: CreateSprintUseCase) = TaskboardController(createTaskUsecase,getTaskUsecase,createProject_MemberUsecase,getAndEditProfileUseCase,createSprintUseCase)

    @Bean
    open fun sprintreviewcontroller() = DailyMeetingController()


}