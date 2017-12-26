package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.SprintRepository
import com.yfam.yscrumy.core.data_provider.TaskRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.SprintStatus
import com.yfam.yscrumy.core.entity.Task
import com.yfam.yscrumy.core.entity.TaskStatus
import com.yfam.yscrumy.core.entity.User
import java.time.LocalTime

class GetTaskUsecase(private val unitOfWorkProvider: UnitOfWorkProvider,
                     private val taskRepository: TaskRepository,
                     private val userRepository: UserRepository,
                     private val sprintRepository: SprintRepository) {
//    fun createTask(name: String, description: String, estimation: LocalTime, sprId: Int, userid: Int) {
//        unitOfWorkProvider.get().use {
//            taskRepository.connect(it)
//            userRepository.connect(it)
//            sprintRepository.connect(it)
//            var sprint=sprintRepository.getById(sprId)
//            var user=userRepository.getById(userid)
//            var task= Task()
//            task.name=name
//            task.assignTo=user
//            task.estimation=estimation
//            task.sprint=sprint
//            task.description=description
//            it.flush {
//                taskRepository.createTask(task)
//            }
//        }
//    }
    fun getTask(uid: Int, sprintid: Int,status: TaskStatus):List<Task>{
    unitOfWorkProvider.get().use {
        taskRepository.connect(it)
        return taskRepository.getallTask(uid, sprintid, status)
    }
}

    fun getTaskToDo(uid: Int):Int{
        unitOfWorkProvider.get().use {
            taskRepository.connect(it)
             return taskRepository.getAllTaskAllSprint(uid,TaskStatus.Backlog).size +
                     taskRepository.getAllTaskAllSprint(uid,TaskStatus.New).size +
                     taskRepository.getAllTaskAllSprint(uid,TaskStatus.Bug).size
        }
    }
    fun getTaskProcessing(uid:Int):Int{
        unitOfWorkProvider.get().use {
            taskRepository.connect(it)
            return taskRepository.getAllTaskAllSprint(uid,TaskStatus.Processing).size
        }
    }
    fun getTaskTesting(uid:Int):Int{
        unitOfWorkProvider.get().use {
            taskRepository.connect(it)
            return taskRepository.getAllTaskAllSprint(uid,TaskStatus.Testing).size
        }
    }
    fun getTaskComplete(uid:Int):Int{
        unitOfWorkProvider.get().use {
            taskRepository.connect(it)
            return taskRepository.getAllTaskAllSprint(uid,TaskStatus.Completed).size+
                    taskRepository.getAllTaskAllSprint(uid,TaskStatus.PassedTest).size
        }
    }
    fun getTaskbyID(tid:Int):Task?{
        unitOfWorkProvider.get().use {
            taskRepository.connect(it)
            return taskRepository.getById(tid)
        }
    }

    fun updateTask(tid:Int,status: TaskStatus){
        unitOfWorkProvider.get().use {
            taskRepository.connect(it)
           var task= taskRepository.getById(tid) as Task
            it.flush {
                task.status=status
            }
        }
    }
    fun updatetaskall(name:String,description:String,estimation:LocalTime,id:Int,userid:Int){
        unitOfWorkProvider.get().use {
            taskRepository.connect(it)
            userRepository.connect(it)
            var taskold= taskRepository.getById(id) as Task

            var user =userRepository.getById(userid)
            it.flush {
                taskold.name=name
                taskold.description=description
                taskold.estimation=estimation
                taskold.tester=user
            }
        }
    }
    fun getallTasktest(uid: Int, sprintid: Int): List<Task> {
        unitOfWorkProvider.get().use {
            taskRepository.connect(it)
        return taskRepository.getallTasktest(uid,sprintid)}
    }
}