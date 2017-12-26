package com.yfam.yscrumy.core.data_provider

import com.yfam.yscrumy.core.entity.SprintStatus
import com.yfam.yscrumy.core.entity.Task
import com.yfam.yscrumy.core.entity.TaskStatus

interface TaskRepository : Repository {


    fun createTask(task: Task)
    fun getallTask(uid: Int, sprintid: Int,status: TaskStatus): List<Task>
    fun getAllTaskAllSprint(uid:Int,status:TaskStatus) : List<Task>
    fun getById(id: Int): Task?
    fun getallTasktest(uid: Int, sprintid: Int): List<Task>
}