package com.yfam.yscrumy.entry_point.web

import com.yfam.yscrumy.core.entity.CoreException
import com.yfam.yscrumy.core.entity.DailyMeeting
import com.yfam.yscrumy.core.entity.Project_member
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDate
import java.time.Period
import javax.servlet.http.HttpSession

/*********************************************************************************************/

/*********************************************************************************************/
/* Controller */
@Controller
open class DailyMeetingController() {
    @GetMapping("/DailyMeeting")
    fun index(session: HttpSession, model: ModelMap): String {
        val currentUser_name = session.getAttribute("currentUser_username").toString().toUpperCase()
        model.put("currentUser_name", currentUser_name)
        var createObject = DailyMeeting()
        model.put("createObject",createObject)
        return "DailyMeeting"

    }
    @PostMapping("/DailyMeeting/create")
    fun create(session: HttpSession, model: ModelMap, @ModelAttribute("createObject") createObject: DailyMeeting): String {
         try {

             } catch (ex: CoreException) {


            return "DailyMeeting"
        }
        return "redirect:/DailyMeeting"
    }




}