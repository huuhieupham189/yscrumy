package com.yfam.yscrumy.data_provider.database.jpa

import com.yfam.yscrumy.core.data_provider.ProjectRepository
import com.yfam.yscrumy.core.data_provider.TeamRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWork
import com.yfam.yscrumy.core.entity.Project
import com.yfam.yscrumy.core.entity.Team
import com.yfam.yscrumy.core.entity.User
import javax.persistence.NoResultException

class JpaProjectRepository : JpaRepository(), ProjectRepository {
    override fun createProject(project: Project) {
        jpaUnitOfWork.entityManager.persist(project)

    }

    override fun getByName(name: String): Project? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select project from Project project where project.name=:name")
                    .setParameter("name", name)
                    .singleResult as Project
        } catch (ex: NoResultException) {
            return null
        }

    }

    override fun getById(id: Int): Project? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select project from Project project where project.id=:id")
                    .setParameter("id", id)
                    .singleResult as Project
        } catch (ex: NoResultException) {
            return null
        }
    }
    override fun getAll(id: Int): List<Project> {

            return jpaUnitOfWork.entityManager.createQuery("select project from Project project where project.team.id=:id")
                    .setParameter("id",id)
                    .resultList as List<Project>

    }



}