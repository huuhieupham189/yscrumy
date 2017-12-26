package com.yfam.yscrumy.core.data_provider

import com.yfam.yscrumy.core.entity.Invitation


interface InvitationRepository : Repository {
    fun createInvitation(invitation: Invitation)
    fun getById(id: Int): Invitation?
    fun searchbyUserID(id:Int): List<Invitation>
    fun searchbyTeamId(id:Int): List<Invitation>
    fun del(invitation: Invitation)
}