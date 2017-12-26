package com.yfam.yscrumy.entry_point.web

import com.sun.org.apache.bcel.internal.generic.RETURN
import com.yfam.yscrumy.core.entity.*
import com.yfam.yscrumy.core.use_case.*
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.Period
import javax.print.attribute.standard.JobPriority
import javax.servlet.http.HttpSession
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


data class createSprint(var id: Int? = null, var name: String = "", var description: String = "", var startdate: String = "", var enddate: String = "")
data class dataPBI(var id: Int? = null, var name: String = "", var description: String = "", var priority: Int = 0)
data class updateRole(var userid: Int? = null, var role: String = "", var username: String = "")
data class listrole(var string: String = "", var list: ArrayList<updateRole> = ArrayList())
@Controller
open class PBIController(val createpbiUseCase: CreatePBIUseCase, val getAllPBIUseCase: GetAllPBIUseCase, val editPBIUseCase: EditPBIUseCase, val delPBIUsecase: DelPBIUsecase, val getAllTeamMemberUseCase: GetAllTeamMemberUseCase, val getAndEditProfileUseCase: GetAndEditProfileUseCase, val createProject_MemberUsecase: CreateProject_MemberUsecase, val getAllProjectUseCase: GetAllProjectUseCase, val createSprintUseCase: CreateSprintUseCase) {

    @PostMapping("/PBI/create")
    fun create(session: HttpSession, model: ModelMap, @ModelAttribute("createPBI") create: dataPBI): String {
        var listPBI = getAllPBIUseCase.getAllPBIByProjectId(session.getAttribute("currentProject_id") as Int).sortedWith(compareBy({ it.priority }))
        try {
            var pri = listPBI.count()
            createpbiUseCase.createPBI(create.name, create.description, session.getAttribute("currentProject_id") as Int, pri)
        } catch (ex: CoreException) {
            var createPBI = dataPBI()
            var listsprint = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
            for (sprint in listsprint) {
                var day = Period.between(LocalDate.now(), sprint.enddate).days
                createSprintUseCase.updateDaysleft(sprint.id as Int, day)
            }
            var listsprint1 = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
            var listPBI = getAllPBIUseCase.getAllPBIByProjectId(session.getAttribute("currentProject_id") as Int).sortedWith(compareBy({ it.priority }))
            val listProjectMember = createProject_MemberUsecase.getAllProjectMember(session.getAttribute("currentProject_id") as Int)
            val search = search()
            var check = false
            var createsprint = createSprint()
            model.put("listsprint", listsprint1)
            model.put("createsprint", createsprint)
            model.put("select", check)
            model.put("listProjectMember", listProjectMember)
            model.put("createPBI", createPBI)
            model.put("updatePBI", createPBI)
            model.put("listPBI", listPBI)
            model.put("search", search)

            var listRole = ArrayList<updateRole>()
            for (user in listProjectMember) {
                var updateRole = updateRole()
                updateRole.userid = user.id
                updateRole.username = user.username
                var userrole = createProject_MemberUsecase.getProjectMember(session.getAttribute("currentProject_id") as Int, user.id as Int) as Project_member
                updateRole.role = userrole.role
                listRole.add(updateRole)
            }
            var updaterole = listrole("a", listRole)
            model.put("updaterole", updaterole)
            model.put("errorpbi", ex.userMessage)

            return "Project"
        }
        return "redirect:/Project"
    }

    @GetMapping("/Project")
    fun pbi(model: ModelMap, session: HttpSession): String {
        if (session.getAttribute("currentUser_team") == null) return "redirect:/Dashboard"
        var createPBI = dataPBI()
        var listsprint = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
        for (sprint in listsprint) {
            var day = Period.between(LocalDate.now(), sprint.enddate).days
            createSprintUseCase.updateDaysleft(sprint.id as Int, day)
        }
        var listsprint1 = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
        var listPBI = getAllPBIUseCase.getAllPBIByProjectId(session.getAttribute("currentProject_id") as Int).sortedWith(compareBy({ it.priority }))
        val listProjectMember = createProject_MemberUsecase.getAllProjectMember(session.getAttribute("currentProject_id") as Int)
        val search = search()
        var check = false
        var createsprint = createSprint()
        val listTeamMember = getAllTeamMemberUseCase.getAllTeamMember(session.getAttribute("currentUser_team") as Int)
        val listMemberNotInProject = ArrayList<User>()
        for (i in 0..(listTeamMember.size - 1)) {
            check = true
            for (j in 0..(listProjectMember.size - 1))
                if (listTeamMember.get(i).id == listProjectMember.get(j).id) check=false
            if (check) listMemberNotInProject.add(listTeamMember.get(i))
        }




        model.put("listMemberNotInProject", listMemberNotInProject)
        model.put("listsprint", listsprint1)
        model.put("createsprint", createsprint)
        model.put("select", check)
        model.put("listProjectMember", listProjectMember)
        model.put("createPBI", createPBI)
        model.put("updatePBI", createPBI)
        model.put("listPBI", listPBI)
        model.put("search", search)

        var listRole = ArrayList<updateRole>()
        for (user in listProjectMember) {
            var updateRole = updateRole()
            updateRole.userid = user.id
            updateRole.username = user.username
            var userrole = createProject_MemberUsecase.getProjectMember(session.getAttribute("currentProject_id") as Int, user.id as Int) as Project_member
            updateRole.role = userrole.role
            listRole.add(updateRole)
        }
        var updaterole = listrole("a", listRole)
        model.put("updaterole", updaterole)


        return "Project"
    }

    @GetMapping("/Project/{id}")
    fun getPBI(session: HttpSession, model: ModelMap, @PathVariable("id") id: Int): String {
        val pbi = editPBIUseCase.getbyID(id)
        var listPBI = getAllPBIUseCase.getAllPBIByProjectId(session.getAttribute("currentProject_id") as Int).sortedWith(compareBy({ it.priority }))
        model.put("updatePBI", pbi)
        model.put("listPBI", listPBI)
        return "Project::modalsContents"
    }


    @PostMapping("/Project/update")
    fun editPBI(model: ModelMap, session: HttpSession, @ModelAttribute("updatePBI") update: dataPBI): String {

        var pbi = PBI()

        try {
            pbi.name = update.name
            pbi.description = update.description
            pbi.id = update.id
            pbi.priority = update.priority
            editPBIUseCase.edit(update.id as Int, pbi, update.priority)
        } catch (ex: CoreException) {
            var createPBI = dataPBI()
            var listsprint = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
            for (sprint in listsprint) {
                var day = Period.between(LocalDate.now(), sprint.enddate).days
                createSprintUseCase.updateDaysleft(sprint.id as Int, day)
            }
            var listsprint1 = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
            var listPBI = getAllPBIUseCase.getAllPBIByProjectId(session.getAttribute("currentProject_id") as Int).sortedWith(compareBy({ it.priority }))
            val listProjectMember = createProject_MemberUsecase.getAllProjectMember(session.getAttribute("currentProject_id") as Int)
            val search = search()
            var check = false
            var createsprint = createSprint()
            model.put("listsprint", listsprint1)
            model.put("createsprint", createsprint)
            model.put("select", check)
            model.put("listProjectMember", listProjectMember)
            model.put("createPBI", createPBI)
            model.put("updatePBI", createPBI)
            model.put("listPBI", listPBI)
            model.put("search", search)
            val listTeamMember = getAllTeamMemberUseCase.getAllTeamMember(session.getAttribute("currentUser_team") as Int)
            val listMemberNotInProject = ArrayList<User>()
            for (i in 0..(listTeamMember.size - 1)) {
                check = true
                for (j in 0..(listProjectMember.size - 1))
                    if (listTeamMember.get(i).id == listProjectMember.get(j).id) check=false
                if (check) listMemberNotInProject.add(listTeamMember.get(i))
            }

            model.put("listMemberNotInProject", listMemberNotInProject)

            var listRole = ArrayList<updateRole>()
            for (user in listProjectMember) {
                var updateRole = updateRole()
                updateRole.userid = user.id
                updateRole.username = user.username
                var userrole = createProject_MemberUsecase.getProjectMember(session.getAttribute("currentProject_id") as Int, user.id as Int) as Project_member
                updateRole.role = userrole.role
                listRole.add(updateRole)
            }
            var updaterole = listrole("a", listRole)
            model.put("updaterole", updaterole)
            model.put("errorpbi", ex.userMessage)
            return "Project"
        }
        return "redirect:/Project"
    }

    @GetMapping("/Project/del")
    fun del(session: HttpSession, model: ModelMap, @RequestParam("id") id: Int): String {
        try {
            delPBIUsecase.delPBi(id)
        } catch (ex: CoreException) {
            var createPBI = dataPBI()
            var listsprint = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
            for (sprint in listsprint) {
                var day = Period.between(LocalDate.now(), sprint.enddate).days
                createSprintUseCase.updateDaysleft(sprint.id as Int, day)
            }
            var listsprint1 = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
            var listPBI = getAllPBIUseCase.getAllPBIByProjectId(session.getAttribute("currentProject_id") as Int).sortedWith(compareBy({ it.priority }))
            val listProjectMember = createProject_MemberUsecase.getAllProjectMember(session.getAttribute("currentProject_id") as Int)
            val search = search()
            var check = false
            var createsprint = createSprint()
            model.put("listsprint", listsprint1)
            model.put("createsprint", createsprint)
            model.put("select", check)
            model.put("listProjectMember", listProjectMember)
            model.put("createPBI", createPBI)
            model.put("updatePBI", createPBI)
            model.put("listPBI", listPBI)
            model.put("search", search)
            val listTeamMember = getAllTeamMemberUseCase.getAllTeamMember(session.getAttribute("currentUser_team") as Int)
            val listMemberNotInProject = ArrayList<User>()
            for (i in 0..(listTeamMember.size - 1)) {
                check = true
                for (j in 0..(listProjectMember.size - 1))
                    if (listTeamMember.get(i).id == listProjectMember.get(j).id) check=false
                if (check) listMemberNotInProject.add(listTeamMember.get(i))
            }

            model.put("listMemberNotInProject", listMemberNotInProject)

            var listRole = ArrayList<updateRole>()
            for (user in listProjectMember) {
                var updateRole = updateRole()
                updateRole.userid = user.id
                updateRole.username = user.username
                var userrole = createProject_MemberUsecase.getProjectMember(session.getAttribute("currentProject_id") as Int, user.id as Int) as Project_member
                updateRole.role = userrole.role
                listRole.add(updateRole)
            }
            var updaterole = listrole("a", listRole)
            model.put("updaterole", updaterole)
            model.put("errorlog", ex.userMessage)
            return "Project"

        }
        return "redirect:/Project"
    }


    @GetMapping("/Project/adduser")
    fun addUser(session: HttpSession, @RequestParam("id") id: Int): String {
        var pj = session.getAttribute("currentProject_id") as Int
        var us = id
        createProject_MemberUsecase.createProject_Member(pj, us, "none")
        return "redirect:/Project"
    }

    @PostMapping("/Sprint/create")
    fun createsprint(session: HttpSession, model: ModelMap, @ModelAttribute("createsprint") create: createSprint): String {
        var sprint = Sprint()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-d", Locale.ENGLISH)

        sprint.name = create.name
        sprint.description = create.description
        sprint.enddate = LocalDate.parse(create.enddate, formatter)
        sprint.startdate = LocalDate.parse(create.startdate, formatter)
        sprint.project = getAllProjectUseCase.getProjectById(session.getAttribute("currentProject_id") as Int)

        try {
            createSprintUseCase.createSprint(sprint)
        } catch (ex: CoreException) {
            var createPBI = dataPBI()
            var listsprint = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
            for (sprint in listsprint) {
                var day = Period.between(LocalDate.now(), sprint.enddate).days
                createSprintUseCase.updateDaysleft(sprint.id as Int, day)
            }
            var listsprint1 = createSprintUseCase.getSprintbyPrjID(session.getAttribute("currentProject_id") as Int)
            var listPBI = getAllPBIUseCase.getAllPBIByProjectId(session.getAttribute("currentProject_id") as Int).sortedWith(compareBy({ it.priority }))
            val listProjectMember = createProject_MemberUsecase.getAllProjectMember(session.getAttribute("currentProject_id") as Int)
            val search = search()
            var check = false
            var createsprint = createSprint()
            model.put("listsprint", listsprint1)
            model.put("createsprint", createsprint)
            model.put("select", check)
            model.put("listProjectMember", listProjectMember)
            model.put("createPBI", createPBI)
            model.put("updatePBI", createPBI)
            model.put("listPBI", listPBI)
            model.put("search", search)
            val listTeamMember = getAllTeamMemberUseCase.getAllTeamMember(session.getAttribute("currentUser_team") as Int)
            val listMemberNotInProject = ArrayList<User>()
            for (i in 0..(listTeamMember.size - 1)) {
                check = true
                for (j in 0..(listProjectMember.size - 1))
                    if (listTeamMember.get(i).id == listProjectMember.get(j).id) check=false
                if (check) listMemberNotInProject.add(listTeamMember.get(i))
            }

            model.put("listMemberNotInProject", listMemberNotInProject)

            var listRole = ArrayList<updateRole>()
            for (user in listProjectMember) {
                var updateRole = updateRole()
                updateRole.userid = user.id
                updateRole.username = user.username
                var userrole = createProject_MemberUsecase.getProjectMember(session.getAttribute("currentProject_id") as Int, user.id as Int) as Project_member
                updateRole.role = userrole.role
                listRole.add(updateRole)
            }
            var updaterole = listrole("a", listRole)
            model.put("updaterole", updaterole)
            model.put("error", ex.userMessage)
            return "Project"
        }
        return "redirect:/Project"
    }

    @PostMapping("/Project/updaterole")
    fun updaterole(session: HttpSession, @ModelAttribute("updaterole") listupdate: listrole): String {
        var listroleupdate = listupdate.list
        for (item in listroleupdate) {
            createProject_MemberUsecase.updateRole(session.getAttribute("currentProject_id") as Int, item.userid as Int, item.role)
        }
        return "redirect:/Project"
    }
}

