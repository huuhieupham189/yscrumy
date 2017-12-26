package com.yfam.yscrumy.data_provider.database.jpa

import com.yfam.yscrumy.core.data_provider.UnitOfWork
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.User
import javax.persistence.NoResultException

class JpaUserRepository : JpaRepository(), UserRepository {
    override fun getById(id: Int): User? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select user from User user where user.id=:id")
                    .setParameter("id", id)
                    .singleResult as User
        } catch (ex: NoResultException) {
            return null
        }
    }

    override fun createUser(user: User) {
        jpaUnitOfWork.entityManager.persist(user)

    }

    override fun getByUsername(username: String): User? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select user from User user where user.username=:username")
                    .setParameter("username", username)
                    .singleResult as User
        } catch (ex: NoResultException) {
            return null
        }
    }
    override fun searchUsername(username: String):List<User>{

            return jpaUnitOfWork.entityManager.createQuery("select user from User user where user.username like CONCAT ('%',:username,'%') and user.team.id=null")
                    .setParameter("username",username)
                    .resultList as List<User>
    }
    override fun searchUsernameInProject(username:String,id:Int,pjid:Int):List<User>{
        return jpaUnitOfWork.entityManager.createQuery("select user from User user where user.username like CONCAT ('%',:username,'%') and user.team.id=:id and user.id not in (select p.user_id from Project_member p where p.project_id=:pjid)")
                .setParameter("username",username)
                .setParameter("id",id)
                .setParameter("pjid",pjid)
                .resultList as List<User>
    }
    override fun getUserByTeamId(id:Int): List<User>{
        return jpaUnitOfWork.entityManager.createQuery("select user from User user where user.team.id=:id")
                .setParameter("id",id)
                .resultList as List<User>
    }
}