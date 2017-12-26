package com.yfam.yscrumy.data_provider.database.jpa

import com.yfam.yscrumy.core.data_provider.ProjectRepository
import com.yfam.yscrumy.core.data_provider.Project_memberRepository
import com.yfam.yscrumy.core.entity.Project
import com.yfam.yscrumy.core.entity.Project_member
import com.yfam.yscrumy.core.entity.User
import javax.persistence.NoResultException

class JpaProject_memberRepository : JpaRepository(), Project_memberRepository {
    override fun createProject(project_member: Project_member) {
        jpaUnitOfWork.entityManager.persist(project_member)
    }
    override fun getAllProjectMember(project_id:Int):List<User>{
        return jpaUnitOfWork.entityManager.createQuery("select user from User user, Project_member p where user.id=p.user_id and p.project_id=:id")
                .setParameter("id",project_id)
                .resultList as List<User>
    }
    override fun getProject_member(uid:Int,pid:Int):Project_member?{
        return jpaUnitOfWork.entityManager.createQuery("select p from Project_member p where p.project_id=:pid and p.user_id=:uid")
                .setParameter("uid",uid)
                .setParameter("pid",pid)
                .singleResult as Project_member
    }
}