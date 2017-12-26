package com.yfam.yscrumy.data_provider.database.jpa

import com.yfam.yscrumy.core.data_provider.TeamRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWork
import com.yfam.yscrumy.core.entity.Project
import com.yfam.yscrumy.core.entity.Team
import com.yfam.yscrumy.core.entity.User
import javax.persistence.NoResultException

class JpaTeamRepository : JpaRepository(), TeamRepository {
    override fun createTeam(team: Team) {
        jpaUnitOfWork.entityManager.persist(team)
    }

    override fun getByName(name: String): Team? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select team from Team team where team.name=:name")
                    .setParameter("name", name)
                    .singleResult as Team
        } catch (ex: NoResultException) {
            return null
        }

    }

    override fun getById(id: Int): Team? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select team from Team team where team.id=:id")
                    .setParameter("id", id)
                    .singleResult as Team
        } catch (ex: NoResultException) {
            return null
        }

    }

    override fun getAllTeamMember(team_id: Int): List<User> {
            return jpaUnitOfWork.entityManager.createQuery("select user from User user where user.team.id=:team_id")
                    .setParameter("team_id", team_id)
                    .resultList as List<User>


    }


 override fun delTeam(team:Team){
     if(jpaUnitOfWork.entityManager.contains(team)) jpaUnitOfWork.entityManager.remove(team)
     else {
         jpaUnitOfWork.entityManager.remove(jpaUnitOfWork.entityManager.merge(team))
     }
 }


}