package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.PBIRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.InvalidArgumentException
import com.yfam.yscrumy.core.entity.PBI
import com.yfam.yscrumy.core.entity.PBIExistedException
import com.yfam.yscrumy.core.entity.User

class EditPBIUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                     private val pbiRepository: PBIRepository) {

fun getbyID(id:Int):PBI?{
    unitOfWorkProvider.get().use{
        pbiRepository.connect(it)
       return pbiRepository.getById(id)
    }
}
    fun edit(id: Int, pbi: PBI,pri:Int) {
        unitOfWorkProvider.get().use {
            pbiRepository.connect(it)
            if (pbiRepository.getByName(pbi.name) != null )
            {   var pbi=pbiRepository.getByName(pbi.name) as PBI
                if(pbi.id!=id) throw  PBIExistedException("PBI name already exists. Please choose a different name!")}
            val getPBI = pbiRepository.getById(id) ?: throw  InvalidArgumentException("PBI does not exist")
            val getPBIpro=pbiRepository.getPBIbyPriority(pri) as PBI
            it.flush {

                getPBIpro.priority=getPBI.priority
                getPBI.name=pbi.name
                getPBI.description=pbi.description
                getPBI.priority=pbi.priority
            }
        }
    }

}