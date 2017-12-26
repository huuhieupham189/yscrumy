package com.yfam.yscrumy.data_provider.database.jpa

import com.yfam.yscrumy.core.data_provider.InvitationRepository
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.Invitation
import com.yfam.yscrumy.core.entity.User
import javax.persistence.NoResultException

class JpaInvitationRepository : JpaRepository(), InvitationRepository {
    override fun searchbyUserID(id: Int): List<Invitation> {
        return jpaUnitOfWork.entityManager.createQuery("select invi from Invitation invi where invi.user.id=:id")
                .setParameter("id", id)
                .resultList as List<Invitation>
    }
override fun searchbyTeamId(id:Int):List<Invitation>{
    return jpaUnitOfWork.entityManager.createQuery("select invi from Invitation invi where invi.team.id=:id")
            .setParameter("id", id)
            .resultList as List<Invitation>
}
    override fun getById(id: Int): Invitation? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select invi from Invitation invi where invi.id=:id")
                    .setParameter("id", id)
                    .singleResult as Invitation
        } catch (ex: NoResultException) {
            return null
        }
    }

    override fun createInvitation(invitation: Invitation) {

        jpaUnitOfWork.entityManager.persist(invitation)

    }
    override fun del(invitation: Invitation){
        if(jpaUnitOfWork.entityManager.contains(invitation)) jpaUnitOfWork.entityManager.remove(invitation)
        else {
            jpaUnitOfWork.entityManager.remove(jpaUnitOfWork.entityManager.merge(invitation))
        }
    }


}