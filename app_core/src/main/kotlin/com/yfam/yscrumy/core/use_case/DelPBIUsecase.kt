package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.PBIRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.entity.CantRemoveException
import com.yfam.yscrumy.core.entity.PBI

class DelPBIUsecase(private val unitOfWorkProvider: UnitOfWorkProvider,

                    private val pbiRepository: PBIRepository) {

    fun delPBi(id:Int){
        unitOfWorkProvider.get().use {
            pbiRepository.connect(it)
            var pbi =pbiRepository.getById(id) as PBI


            it.flush {
                if ( !pbiRepository.delPBI(pbi)) throw CantRemoveException("PBI is processing!")


            }

        }
    }}
