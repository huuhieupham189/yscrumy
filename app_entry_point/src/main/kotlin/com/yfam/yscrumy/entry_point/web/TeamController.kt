package com.yfam.yscrumy.entry_point.web

import com.yfam.yscrumy.core.entity.CoreException
import com.yfam.yscrumy.core.entity.Team
import com.yfam.yscrumy.core.entity.User
import com.yfam.yscrumy.core.use_case.CreateTeamUseCase
import com.yfam.yscrumy.core.use_case.EditProfileUseCase
import com.yfam.yscrumy.core.use_case.GetAllProjectUseCase
import com.yfam.yscrumy.core.use_case.InvitationUsecase
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession


/*********************************************************************************************/
/* Models */

data class CreateRequest(var name: String = "",
                         var description: String = ""
)


/*********************************************************************************************/
/* Controller */
@Controller
open class TeamController(val createTeamUseCase: CreateTeamUseCase, val invitationUsecase: InvitationUsecase, val editProfileUseCase: EditProfileUseCase, val getAllProjectUseCase: GetAllProjectUseCase) {


    @GetMapping("/Team")
    fun Team(session: HttpSession, model: ModelMap): String {
        if (session.getAttribute("currentUser_id") == null) return "redirect:/User"
        if (session.getAttribute("currentUser_team") != null) return "redirect:/Dashboard"
        var list = invitationUsecase.getInvitationByUserId(session.getAttribute("currentUser_id") as Int)
        val createRequest = CreateRequest()
        model.put("listinvitation", list)
        model.put("createRequest", createRequest)
        return "Team"
    }


    @PostMapping("/Team/Create")
    fun createTeam(session: HttpSession, model: ModelMap, @ModelAttribute("createRequest") createRequest: CreateRequest): String {
        var id: Int? = null
        try {
            id = createTeamUseCase.createTeamAndAddUser(createRequest.name, createRequest.description, session.getAttribute("currentUser_id") as Int)
        } catch (ex: CoreException) {
            val teamJoin = Team()
            val createRequest = CreateRequest()
            model.put("teamJoin", teamJoin)
            model.put("createRequest", createRequest)
            model.put("errorcreate", ex.userMessage)
            return "Team"
        }
        if (id != null) session.setAttribute("currentUser_team", id)
        return "redirect:/Dashboard"
    }

    @GetMapping("/Team/Accept")
    fun accept(session: HttpSession, @RequestParam("id") id: Int): String {
        editProfileUseCase.acceptTeam(session.getAttribute("currentUser_id") as Int, id)
        session.setAttribute("currentUser_team", id)
        return "redirect:/Dashboard"
    }

    @PostMapping("/Team/Edit")
    fun editTeam(session: HttpSession, @ModelAttribute("editTeam") edit: Team): String {

        createTeamUseCase.update(session.getAttribute("currentUser_team") as Int, edit.description)
        return "redirect:/Dashboard"
    }

    @GetMapping("/Team/Del")
    fun del(session: HttpSession): String {
        var list = editProfileUseCase.getUserByTeamId(session.getAttribute("currentUser_team") as Int)
        if (getAllProjectUseCase.getAllProject(session.getAttribute("currentUser_team") as Int).isEmpty()) {
            for (user in list) {
                editProfileUseCase.leaveTeam(user.id as Int)
            }
            var listinvi = invitationUsecase.getInvitationbyTeamId(session.getAttribute("currentUser_team") as Int)
            for (invi in listinvi) {
                invitationUsecase.delInvitation(invi)
            }
            createTeamUseCase.delTeam(session.getAttribute("currentUser_team") as Int)
            session.removeAttribute("currentUser_team")

            return "redirect:/Dashboard"
        }
        return "redirect:/Dashboard"

    }
}








