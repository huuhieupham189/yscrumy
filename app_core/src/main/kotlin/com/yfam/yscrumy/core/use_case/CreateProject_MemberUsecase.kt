package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.Project_memberRepository
import com.yfam.yscrumy.core.data_provider.TeamRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.*
import java.util.*

class CreateProject_MemberUsecase(private val unitOfWorkProvider: UnitOfWorkProvider,
                                  private val project_memberRepository: Project_memberRepository) {
    fun createProject_Member(pjID:Int,uID:Int,role:String){
        unitOfWorkProvider.get().use {
            project_memberRepository.connect(it)
            var pj_m= Project_member()
            pj_m.project_id=pjID
            pj_m.user_id=uID
            pj_m.role=role
            it.flush {
                project_memberRepository.createProject(pj_m)
            }
    }
}
    fun getAllProjectMember(pjID:Int):List<User>{
        unitOfWorkProvider.get().use {
            project_memberRepository.connect(it)
            return project_memberRepository.getAllProjectMember(pjID)
        }
}
fun getProjectMember(pid:Int,uid:Int):Project_member?{
    unitOfWorkProvider.get().use {
        project_memberRepository.connect(it)
        return project_memberRepository.getProject_member(uid,pid)
    }
}
    fun updateRole(proid:Int,userid:Int,rolestr:String){
        unitOfWorkProvider.get().use {
            project_memberRepository.connect(it)
            var role= project_memberRepository.getProject_member(userid,proid) as Project_member
            it.flush {
                role.role=rolestr
            }
        }
    }
}