package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.PBIRepository
import com.yfam.yscrumy.core.data_provider.ProjectRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.entity.PBI
import com.yfam.yscrumy.core.entity.PBIExistedException
import com.yfam.yscrumy.core.entity.ProjectNotExistedException

class GetAllPBIUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,

                       private val pbiRepository: PBIRepository) {
    fun getAllPBI(): List<PBI> {
        unitOfWorkProvider.get().use {
            pbiRepository.connect(it)
            var list = pbiRepository.getAll()
            return list
        }
    }
    fun getAllPBIByProjectId(id:Int):List<PBI>{
        unitOfWorkProvider.get().use {
            pbiRepository.connect(it)
            var list = pbiRepository.getAllByProject(id)
            return list
        }
    }
    fun getPBIById(id:Int):PBI?{
        unitOfWorkProvider.get().use {
            pbiRepository.connect(it)
            var pbi = pbiRepository.getById(id)
            return pbi
        }
    }
}



