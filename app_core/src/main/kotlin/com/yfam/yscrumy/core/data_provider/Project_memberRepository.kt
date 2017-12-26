package com.yfam.yscrumy.core.data_provider

import com.yfam.yscrumy.core.entity.Project_member
import com.yfam.yscrumy.core.entity.User

interface Project_memberRepository : Repository {


    fun createProject(project_member: Project_member)
    fun getAllProjectMember(project_id: Int): List<User>
    fun getProject_member(uid: Int, pid: Int): Project_member?
}