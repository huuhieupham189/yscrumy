package com.yfam.yscrumy.entry_point.web

import com.yfam.yscrumy.core.entity.CoreException
import com.yfam.yscrumy.core.entity.Team
import com.yfam.yscrumy.core.entity.User
import com.yfam.yscrumy.core.use_case.*
import org.apache.commons.lang3.ObjectUtils
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpSession

/*********************************************************************************************/
/* Models */
data class search(var username: String = "")
data class TaskSum(var user: User, var todo : Int=0, var inprogress : Int=0, var testing : Int=0, var completed : Int=0)

/*********************************************************************************************/
/* Controller */
@Controller
open class DashboardController(val getAllTeamMemberUseCase: GetAllTeamMemberUseCase, val getAllProjectUseCase: GetAllProjectUseCase, val getAndEditProfileUseCase: GetAndEditProfileUseCase, val createTeamUseCase: CreateTeamUseCase, val getTaskUsecase: GetTaskUsecase) {
    @GetMapping("/Dashboard")
    fun index(session: HttpSession, model: ModelMap): String {
        if (session.getAttribute("currentUser_id") == null) return "redirect:/User"
        val currentUser_name = session.getAttribute("currentUser_username").toString().toUpperCase()
        if (session.getAttribute("currentUser_team") == null) return "redirect:/Team"
        val createProject = createProjectRequest()
        val listProject = getAllProjectUseCase.getAllProject(session.getAttribute("currentUser_team") as Int)
        val listTeamMember = getAllTeamMemberUseCase.getAllTeamMember(session.getAttribute("currentUser_team") as Int)
        val search = search()
        val team = createTeamUseCase.getTeamById(session.getAttribute("currentUser_team") as Int) as Team
        val check = "false"
        model.put("createProject", createProject)
        model.put("listProject", listProject)
        model.put("listTeamMember", listTeamMember)
        model.put("currentUser_name", currentUser_name)
        model.put("NumberOfMember", listTeamMember.size)
        model.put("NumberOfProject", listProject.size)
        model.put("select", check)
        model.put("search", search)
        model.put("editTeam", team)
        model.put("listuser", null)
        var taskSumList = ArrayList<TaskSum>()
        for (i in (0..listTeamMember.size-1)){
            var uid = listTeamMember.get(i).id as Int
            taskSumList.add(TaskSum(listTeamMember.get(i),getTaskUsecase.getTaskToDo(uid),getTaskUsecase.getTaskProcessing(uid),getTaskUsecase.getTaskTesting(uid),getTaskUsecase.getTaskComplete(uid)))
        }
        model.put("taskSumList",taskSumList)


        return "Dashboard"

    }

    @PostMapping("/Dashboard/search")
    fun searchUser(session: HttpSession, model: ModelMap, @ModelAttribute("search") search: search): String {
        var list = getAndEditProfileUseCase.searchUser(search.username)
        model.put("listuser", list)
        val createProject = createProjectRequest()
        val listProject = getAllProjectUseCase.getAllProject(session.getAttribute("currentUser_team") as Int)
        val listTeamMember = getAllTeamMemberUseCase.getAllTeamMember(session.getAttribute("currentUser_team") as Int)
        val check = "true"
        val team = createTeamUseCase.getTeamById(session.getAttribute("currentUser_team") as Int) as Team
        val currentUser_name = session.getAttribute("currentUser_username").toString().toUpperCase()
        model.put("createProject", createProject)
        model.put("listProject", listProject)
        model.put("listTeamMember", listTeamMember)
        model.put("currentUser_name", currentUser_name)
        model.put("NumberOfMember", listTeamMember.size)
        model.put("NumberOfProject", listProject.size)
        model.put("select", check)
        model.put("editTeam", team)
        var taskSumList = ArrayList<TaskSum>()
        for (i in (0..listTeamMember.size-1)){
            var uid = listTeamMember.get(i).id as Int
            taskSumList.add(TaskSum(listTeamMember.get(i),getTaskUsecase.getTaskToDo(uid),getTaskUsecase.getTaskProcessing(uid),getTaskUsecase.getTaskTesting(uid),getTaskUsecase.getTaskComplete(uid)))
        }
        model.put("taskSumList",taskSumList)
        return "Dashboard"
    }

    @GetMapping("/Dashboard/{id}")
    fun getProjectID(session: HttpSession, @PathVariable("id") id: Int): String {
        var project = getAllProjectUseCase.getProjectById(id)
        if (project != null) {
            session.setAttribute("currentProject_id", project.id)
            return "redirect:/Project"
        }
        return "redirect:/Dashboard"
    }


}