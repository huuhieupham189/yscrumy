package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.*
import com.yfam.yscrumy.core.entity.Project_member
import com.yfam.yscrumy.core.entity.Task
import com.yfam.yscrumy.core.entity.TaskStatus
import com.yfam.yscrumy.core.entity.User
import java.time.LocalTime

class CreateTaskUsecase(private val unitOfWorkProvider: UnitOfWorkProvider,
                        private val taskRepository: TaskRepository,
                        private val userRepository: UserRepository,
                        private val sprintRepository: SprintRepository) {
    fun createTask(name: String, description: String, estimation: LocalTime, sprId: Int, userid: Int) {
        unitOfWorkProvider.get().use {
            taskRepository.connect(it)
            userRepository.connect(it)
            sprintRepository.connect(it)
            var sprint = sprintRepository.getById(sprId)
            var user = userRepository.getById(userid)
            var task = Task()

            task.name = name
            task.assignTo = user
            task.estimation = estimation
            task.sprint = sprint
            task.status = TaskStatus.New
            task.description = description
            it.flush {
                taskRepository.createTask(task)
            }
        }
    }

}