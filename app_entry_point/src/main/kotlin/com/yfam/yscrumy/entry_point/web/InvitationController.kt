package com.yfam.yscrumy.entry_point.web

import com.sun.org.glassfish.gmbal.ParameterNames
import com.yfam.yscrumy.core.entity.CoreException
import com.yfam.yscrumy.core.entity.Invitation
import com.yfam.yscrumy.core.entity.Team
import com.yfam.yscrumy.core.use_case.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpSession

/*********************************************************************************************/
/* Models */


/*********************************************************************************************/
/* Controller */
@Controller
open class InvitationController(val invitationUsecase: InvitationUsecase,val createTeamUseCase: CreateTeamUseCase,val editProfileUseCase: GetAndEditProfileUseCase) {


   @GetMapping("/Invitation/create")
    fun create(@RequestParam("id") id:Int,session: HttpSession,model: ModelMap):String{
 var team=createTeamUseCase.getTeamById(session.getAttribute("currentUser_team") as Int)
       var user=editProfileUseCase.getProfile(id)
       var invi= Invitation()
      invi.team=team
       invi.user=user
       invitationUsecase.create(invi)

        return "redirect:/Dashboard"
    }

}