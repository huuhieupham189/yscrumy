package com.yfam.yscrumy.entry_point.web

import com.yfam.yscrumy.core.entity.CoreException
import com.yfam.yscrumy.core.entity.Team
import com.yfam.yscrumy.core.entity.User
import com.yfam.yscrumy.core.use_case.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpSession

/*********************************************************************************************/
/* Models */

data class SignUpRequest(var username: String = "",
                         var hashedPassword: String = "",
                         var email: String = "",
                         var phoneNumber: String = "")

data class SignInRequest(var username: String = "", var hashedPassword: String = "")

data class EditProfileRequest(var email: String = "",
                              var phoneNumber: String = "",
                              var hashedPassword: String = "",
                              var newHashedPassword: String = ""
                              )

/*********************************************************************************************/
/* Controller */
@Controller
open class UserController(private val loginUseCase: LoginUseCase, private val registerUseCase: RegisterUseCase, private val getAndEditProfileUseCase: GetAndEditProfileUseCase,private val editprofile:EditProfileUseCase,val createTeamUseCase: CreateTeamUseCase,val invitationUsecase: InvitationUsecase) {



    @GetMapping("/")
    fun index(session: HttpSession): String {
        if (session.getAttribute("currentUser_id") == null) return "redirect:/User"
        return "redirect:/Team"
    }

    @GetMapping("/User")
    fun user(model: ModelMap, session: HttpSession): String {
        if (session.getAttribute("currentUser_id")!= null) return "redirect:/Team"
        var signInRequest = SignInRequest()
        var signUpRequest = SignUpRequest()
        model.put("signInRequest", signInRequest)
        model.put("signUpRequest", signUpRequest)
        return "User"
    }

    @PostMapping("User/Login")
    fun login(session: HttpSession, model: ModelMap, @ModelAttribute("signInRequest") signInRequest: SignInRequest): String {
        try {
            loginUseCase.login(signInRequest.username, signInRequest.hashedPassword)

        } catch (ex: CoreException) {

            var signUpRequest = SignUpRequest()
            model.put("signInRequest", signInRequest)
            model.put("signUpRequest", signUpRequest)
            model.put("errorlog", ex.userMessage)
            return "User"
        }
        var currentUser = loginUseCase.getCurrentUser()
        var user = (currentUser as User)
        session.setAttribute("currentUser_id", (currentUser as User).id)
        session.setAttribute("currentUser_username", (currentUser as User).username)
        if (user.team != null) {
            var id = (user.team as Team).id
            session.setAttribute("currentUser_team", id)
        }
        return "redirect:/Team"
    }

    @PostMapping("User/Register")
    fun register(session: HttpSession, model: ModelMap, @ModelAttribute("signUpRequest") signUpRequest: SignUpRequest): String {
        try {
            registerUseCase.register(signUpRequest.username, signUpRequest.email, signUpRequest.hashedPassword, signUpRequest.phoneNumber)
        } catch (ex: CoreException) {
            var signInRequest = SignInRequest()
            model.put("signInRequest", signInRequest)
            model.put("signUpRequest", signUpRequest)
            model.put("errorreg", ex.userMessage)
            return "User"
        }
        var currentUser = registerUseCase.getCurrentUser()
        var user = (currentUser as User)
        session.setAttribute("currentUser_id", user.id)
        session.setAttribute("currentUser_username", user.username)
        return "redirect:/Team"
    }


    @GetMapping("/User/LogOut")
    fun logOut(session: HttpSession): String {
        session.invalidate()
        return "redirect:/User"
    }

    @GetMapping("/User/Profile")
    fun getProfile(session: HttpSession, model: ModelMap): String {
        if (session.getAttribute("currentUser_id") == null) return "redirect:/User"
        val user_id = session.getAttribute("currentUser_id")
        if (session.getAttribute("currentUser_team") == null) return "redirect:/Team"
        val user = getAndEditProfileUseCase.getProfile(user_id as Int)
        var editProfileRequest = EditProfileRequest()
        val currentUser_name = session.getAttribute("currentUser_username").toString().toUpperCase()
        model.put("user", user)
        model.put("editProfileRequest",editProfileRequest)
        model.put("currentUser_name", currentUser_name)
        return "User/Profile"
    }
@PostMapping("/User/Profile")
    fun update(session: HttpSession,model: ModelMap,@ModelAttribute("editProfileRequest") edit: EditProfileRequest):String{

    try {
        var useredit= User()
        useredit.hashedPassword=edit.newHashedPassword
        if(edit.email!="") useredit.email=edit.email
        if(edit.phoneNumber!="") useredit.phoneNumber=edit.phoneNumber
        editprofile.edit(session.getAttribute("currentUser_id")as Int,useredit,edit.hashedPassword)
    }catch(ex:CoreException){
        val user = getAndEditProfileUseCase.getProfile( session.getAttribute("currentUser_id")as Int)
          var editProfileRequest = EditProfileRequest()
        val currentUser_name = session.getAttribute("currentUser_username").toString().toUpperCase()
        model.put("currentUser_name", currentUser_name)
        model.put("user", user)
        model.put("editProfileRequest",editProfileRequest)
        model.put("errorlog",ex.userMessage)
        return "User/Profile"
    }

return "redirect:/User/Profile"
}
@GetMapping("/User/LeaveTeam")
    fun leave(session: HttpSession):String{
    var team=createTeamUseCase.getTeamById(session.getAttribute("currentUser_team")as Int) as Team
    var user=team.teamleader as User
    if(user.id==session.getAttribute("currentUser_id")as Int){
        var list = editprofile.getUserByTeamId(session.getAttribute("currentUser_team") as Int)

        for(user in list){
            editprofile.leaveTeam(user.id as Int)
        }
        var listinvi= invitationUsecase.getInvitationbyTeamId(session.getAttribute("currentUser_team") as Int)
        for(invi in listinvi){
            invitationUsecase.delInvitation(invi)
        }
        createTeamUseCase.delTeam(session.getAttribute("currentUser_team") as Int)
    }else
    {editprofile.leaveTeam(session.getAttribute("currentUser_id")as Int)}
    session.removeAttribute("currentUser_team")
    return "redirect:/Dashboard"
}

}