package com.yfam.yscrumy.entry_point.web

import com.yfam.yscrumy.core.entity.*
import com.yfam.yscrumy.core.use_case.*
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import java.time.LocalTime
import javax.servlet.http.HttpSession

/*********************************************************************************************/
/* Models */
data class createTask(var name: String = "", var description: String = "", var estimation: LocalTime = LocalTime.of(1,0))

data class updatetask(var id: Int = 0,
                      var name: String = "",
                      var description: String = "",
                      var estimation: LocalTime = LocalTime.now(),
                      var tester: Int = 0)

/*********************************************************************************************/
/* Controller */
@Controller
open class TaskboardController(val createTaskUsecase: CreateTaskUsecase, val getTaskUsecase: GetTaskUsecase, val createProject_MemberUsecase: CreateProject_MemberUsecase,val getAndEditProfileUseCase: GetAndEditProfileUseCase,val createSprintUseCase: CreateSprintUseCase) {
    @GetMapping("/Taskboard")
    fun index(session: HttpSession, model: ModelMap): String {
        var createtask = createTask()
        if (session.getAttribute("currentSprint_id") == null) return "redirect:/User"
        val listProjectMember = createProject_MemberUsecase.getAllProjectMember(session.getAttribute("currentProject_id") as Int)
        var uid = session.getAttribute("currentUser_id") as Int
        var sprintid = session.getAttribute("currentSprint_id") as Int
        val user=getAndEditProfileUseCase.getProfile(uid) as User

        var tasknew = getTaskUsecase.getTask(uid, sprintid, TaskStatus.New)
        var taskbacklog = getTaskUsecase.getTask(uid, sprintid, TaskStatus.Backlog)
        var taskProcessing = getTaskUsecase.getTask(uid, sprintid, TaskStatus.Processing)
        var taskTesting = getTaskUsecase.getTask(uid, sprintid, TaskStatus.Testing)
        var tasktestter = getTaskUsecase.getallTasktest(uid, sprintid)
        var taskBug = getTaskUsecase.getTask(uid, sprintid, TaskStatus.Bug)
        var taskPassedTest = getTaskUsecase.getTask(uid, sprintid, TaskStatus.PassedTest)
        var taskCompleted = getTaskUsecase.getTask(uid, sprintid, TaskStatus.Completed)
        var tasktodo = taskbacklog + tasknew + taskBug
        var taskinprogress = taskProcessing
        var tasktest = taskTesting + taskPassedTest + tasktestter
        var taskcompleted = taskCompleted
        var task = updatetask()
        var check=false
        var totallist=tasktodo+taskinprogress+tasktest+taskcompleted
        if(totallist.size==taskcompleted.size && totallist.size!=0) check=true
        model.put("currentuserid", uid)
        model.put("listprojectmember", listProjectMember)
        model.put("taskupdate", task)
        model.put("tasktodo", tasktodo)
        model.put("taskinprogress", taskinprogress)
        model.put("tasktest", tasktest)
        model.put("taskcompleted", taskcompleted)
        model.put("createtask", createtask)
        model.put("check",check)
        model.put("total",totallist.size)
        model.put("numbertodo",tasktodo.size)
        model.put("numberinprogress",taskinprogress.size)
        model.put("numbertest",tasktest.size)
        model.put("numbercompleted",taskcompleted.size)
        model.put("currentUser_name",user.username)
        return "Taskboard"
    }

    @PostMapping("/Taskboard/create")
    fun createtask(session: HttpSession, model: ModelMap, @ModelAttribute("createtask") createtask: createTask): String {

        createTaskUsecase.createTask(createtask.name, createtask.description, createtask.estimation, session.getAttribute("currentSprint_id") as Int, session.getAttribute("currentUser_id") as Int)
        return "redirect:/Taskboard"
    }

    @GetMapping("/Taskboard/updatestatus")
    fun updateStatus(session: HttpSession, model: ModelMap, @RequestParam("tid") tid: Int, @RequestParam("status") status: String): String {

        var task = getTaskUsecase.getTaskbyID(tid) as Task
        if (status == "InProgress") {
            getTaskUsecase.updateTask(tid, TaskStatus.Processing)
            return "redirect:/Taskboard"
        }
        if (status == "Test") {
            if (task.status == TaskStatus.New || task.status == TaskStatus.Bug || task.status == TaskStatus.Backlog) {
                return "redirect:/Taskboard"
            }
            getTaskUsecase.updateTask(tid, TaskStatus.Testing)
            return "redirect:/Taskboard"
        }

        if (status == "Todo") {
            return "redirect:/Taskboard"
        }
        return "redirect:/Taskboard"
    }

    @GetMapping("/Taskboard/{id}")
    fun getPBI(session: HttpSession, model: ModelMap, @PathVariable("id") id: Int): String {
        var task = updatetask()
        var currenttask = getTaskUsecase.getTaskbyID(id) as Task
        task.id = currenttask.id as Int
        task.description = currenttask.description
        task.estimation = currenttask.estimation
        task.name = currenttask.name
        if (currenttask.tester != null) {
            var user = currenttask.tester as User
            task.tester = user.id as Int
        }
        val listProjectMember = createProject_MemberUsecase.getAllProjectMember(session.getAttribute("currentProject_id") as Int)

        model.put("listprojectmember", listProjectMember)
        model.put("taskupdate", task)

        return "Taskboard::modalsContents"
    }

    @PostMapping("/Taskboard/update")
    fun updateTask(session: HttpSession, model: ModelMap, @ModelAttribute("taskupdate") task: updatetask): String {
        getTaskUsecase.updatetaskall(task.name, task.description, task.estimation, task.id, task.tester)
        return "redirect:/Taskboard"
    }

    @GetMapping("/Taskboard/bug")
    fun bug(@RequestParam("id") tid: Int): String {
        getTaskUsecase.updateTask(tid, TaskStatus.Bug)
        return "redirect:/Taskboard"
    }

    @GetMapping("/Taskboard/completed")
    fun completed(@RequestParam("id") tid: Int): String {
        getTaskUsecase.updateTask(tid, TaskStatus.Completed)
        return "redirect:/Taskboard"
    }
    @GetMapping("/Taskboard/sprintcompleted")
    fun sprintcompleted(session: HttpSession):String{
        var sprint= createSprintUseCase.getSprintById(session.getAttribute("currentSprint_id") as Int) as Sprint
        var pbi=sprint.sprintpbi as PBI
        createSprintUseCase.completedSprintPBI(sprint.id as Int,pbi.id as Int)
        return "redirect:/Project"
    }

}