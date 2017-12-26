package com.yfam.yscrumy.entry_point.web

import com.yfam.yscrumy.core.entity.*
import com.yfam.yscrumy.core.use_case.*
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.time.Period
import javax.servlet.http.HttpSession


/*********************************************************************************************/
/* Models */
data class createProjectRequest(var name: String = "",
                                var description: String = "")


/*********************************************************************************************/
/* Controller */
@Controller
open class ProjectController(val createProjectUseCase: CreateProjectUseCase, val getAllProjectUseCase: GetAllProjectUseCase, val getAllTeamMemberUseCase: GetAllTeamMemberUseCase,val createTeamUseCase: CreateTeamUseCase,val createSprintUseCase: CreateSprintUseCase,val getAllPBIUseCase: GetAllPBIUseCase,val createProject_MemberUsecase: CreateProject_MemberUsecase) {


    @PostMapping("/Project/Create")
    fun createProject(model: ModelMap, session:HttpSession,@ModelAttribute("createproject") createproject: createProjectRequest): String {
        try {
            createProjectUseCase.createProject(createproject.name,createproject.description,session.getAttribute("currentUser_id") as Int)
        } catch (ex: CoreException) {
            val createProject = createProjectRequest()
            val listProject = getAllProjectUseCase.getAllProject(session.getAttribute("currentUser_team") as Int)
            val currentUser_name = session.getAttribute("currentUser_username").toString().toUpperCase()
            val listProjectMember = createProject_MemberUsecase.getAllProjectMember(session.getAttribute("currentProject_id") as Int)
            val team=createTeamUseCase.getTeamById(session.getAttribute("currentUser_team") as Int) as Team
            val search=search()
            val check="false"
            val listTeamMember = getAllTeamMemberUseCase.getAllTeamMember(session.getAttribute("currentUser_team") as Int)
            val listMemberNotInProject = ArrayList<User>()
            var checktmp= false
            for (i in 0..(listTeamMember.size - 1)) {
                checktmp = true
                for (j in 0..(listProjectMember.size - 1))
                    if (listTeamMember.get(i).id == listProjectMember.get(j).id) checktmp=false
                if (checktmp) listMemberNotInProject.add(listTeamMember.get(i))
            }
            model.put("listMemberNotInProject", listMemberNotInProject)
            model.put("listProject",listProject)
            model.put("createProject",createProject)
            model.put("listProjectMember",listProjectMember)
            model.put("currentUser_name",currentUser_name)
            model.put("NumberOfMember",listTeamMember.size)
            model.put("NumberOfProject",listProject.size)
            model.put("error",ex.userMessage)
            model.put("editTeam",team)
            model.put("select",check)
            model.put("search",search)
            return "Dashboard"
        }
        session.setAttribute("currentProject_id",createProjectUseCase.getCurrentProject().id)
        return  "redirect:/Dashboard"
    }
    @GetMapping("/Project/update")
    fun updatesprintpbi(session: HttpSession,@RequestParam("pbi") pbi:Int,@RequestParam("sprint") sprint:Int,model: ModelMap):String{
        var listsprint=createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id")as Int)
        var check=true
        for(sprint1 in listsprint){
            if(sprint1.status==SprintStatus.Processing) check=false
        }
        if(check)
        createSprintUseCase.updateSprintPBI(sprint,pbi)
        else {
            var createPBI = dataPBI()
            var listsprint = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
            for (sprint in listsprint) {
                var day = Period.between(LocalDate.now(), sprint.enddate).days
                createSprintUseCase.updateDaysleft(sprint.id as Int, day)
            }
            var listsprint1 = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
            var listPBI = getAllPBIUseCase.getAllPBIByProjectId(session.getAttribute("currentProject_id") as Int).sortedWith(compareBy({ it.priority }))
            val listTeamMember = createProject_MemberUsecase.getAllProjectMember(session.getAttribute("currentProject_id") as Int)
            val search = search()
            var check = false
            var createsprint = createSprint()
            model.put("listsprint", listsprint1)
            model.put("createsprint", createsprint)
            model.put("select", check)
            model.put("listTeamMember", listTeamMember)
            model.put("createPBI", createPBI)
            model.put("updatePBI", createPBI)
            model.put("listPBI", listPBI)
            model.put("search", search)

            var listRole= ArrayList<updateRole>()
            for (user in listTeamMember){
                var updateRole=updateRole()
                updateRole.userid=user.id
                updateRole.username=user.username
                var userrole=createProject_MemberUsecase.getProjectMember(session.getAttribute("currentProject_id") as Int,user.id as Int) as Project_member
                updateRole.role=userrole.role
                listRole.add(updateRole)
            }
            var updaterole=listrole("a",listRole)
            model.put("updaterole",updaterole)
            model.put("errorrule","Can't start this Sprint because another Sprint is running!")
            return "Project"
        }
        return "redirect:/Project"
    }
    @GetMapping("/Project/task")
    fun redirect(session: HttpSession,@RequestParam("id") id:Int):String{
        session.setAttribute("currentSprint_id",id)
        return "redirect:/Taskboard"
    }

}