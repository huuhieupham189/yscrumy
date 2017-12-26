package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.InvitationRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.Invitation
import com.yfam.yscrumy.core.entity.User
import com.yfam.yscrumy.core.entity.UserExistedException
import com.yfam.yscrumy.core.hash
import com.yfam.yscrumy.core.passwordHashingSalt

class InvitationUsecase(private val unitOfWorkProvider: UnitOfWorkProvider,
                        private val invitationRepository: InvitationRepository) {

    fun create (invi:Invitation){
        unitOfWorkProvider.get().use {
            invitationRepository.connect(it)
            it.flush {
                invitationRepository.createInvitation(invi)
            }
        }
    }
    fun getInvitationbyTeamId(id:Int):List<Invitation>{
        unitOfWorkProvider.get().use {
            invitationRepository.connect(it)
            return invitationRepository.searchbyTeamId(id)
        }
    }
    fun getInvitationByUserId(id:Int):List<Invitation>{
        unitOfWorkProvider.get().use {
            invitationRepository.connect(it)
            return invitationRepository.searchbyUserID(id)
        }
    }
    fun delInvitation(Invi:Invitation){
        unitOfWorkProvider.get().use {
            invitationRepository.connect(it)
        it.flush {
            invitationRepository.del(Invi)
        }
        }
    }
}