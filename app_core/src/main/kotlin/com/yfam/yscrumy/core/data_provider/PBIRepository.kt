package com.yfam.yscrumy.core.data_provider

import com.yfam.yscrumy.core.entity.PBI

interface PBIRepository : Repository {
    fun createPBI(pbi: PBI)

    fun getByName(name: String): PBI?

    fun getById(id: Int): PBI?
    fun getAll(): List<PBI>
    fun getAllByProject(id: Int): List<PBI>
    fun delPBI(pbi:PBI):Boolean
    fun getPBIbyPriority(pri: Int): PBI?
}