package com.yfam.yscrumy.data_provider.database.jpa

import com.yfam.yscrumy.core.data_provider.PBIRepository

import com.yfam.yscrumy.core.entity.PBI
import com.yfam.yscrumy.core.entity.PBIStatus
import javax.persistence.NoResultException

class JpaPBIRepository : JpaRepository(), PBIRepository {
    override fun getById(id: Int): PBI? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select pbi from PBI pbi  where pbi.id=:id")
                    .setParameter("id", id)
                    .singleResult as PBI
        } catch (ex: NoResultException) {
            return null
        }
    }

    override fun createPBI(pbi: PBI) {
        jpaUnitOfWork.entityManager.persist(pbi)
    }

    override fun getByName(name: String): PBI? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select pbi from PBI pbi where pbi.name=:name")
                    .setParameter("name", name)
                    .singleResult as PBI
        } catch (ex: NoResultException) {
            return null
        }
    }

    override fun getAll(): List<PBI> {

        return jpaUnitOfWork.entityManager.createQuery("select pbi from PBI pbi")
                .resultList as List<PBI>

    }

    override fun getAllByProject(id: Int): List<PBI> {
        return jpaUnitOfWork.entityManager.createQuery("select pbi from PBI pbi where pbi.project.id=:id")
                .setParameter("id", id)
                .resultList as List<PBI>

    }
override fun getPBIbyPriority(pri:Int):PBI?{
    try{return jpaUnitOfWork.entityManager.createQuery("select pbi from PBI pbi where pbi.priority=:pri")
            .setParameter("pri", pri)
            .singleResult as PBI}
    catch(ex:NoResultException){
        return null
    }
}
    override fun delPBI(pbi: PBI): Boolean {
        if (pbi.status == PBIStatus.Processing || pbi.status==PBIStatus.Pause) return false
        else {
            if(jpaUnitOfWork.entityManager.contains(pbi)) jpaUnitOfWork.entityManager.remove(pbi)
            else {
                jpaUnitOfWork.entityManager.remove(jpaUnitOfWork.entityManager.merge(pbi))
            }
            return true
        }

    }
}