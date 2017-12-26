package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.PBIRepository
import com.yfam.yscrumy.core.data_provider.ProjectRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.*
import javafx.scene.layout.Priority

class CreatePBIUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                       private val projectRepository: ProjectRepository,
                       private val pbiRepository: PBIRepository) {
    fun createPBI(name: String, description: String, projectID: Int,priority: Int) {
        unitOfWorkProvider.get().use {
            projectRepository.connect(it)
            pbiRepository.connect(it)

            if (pbiRepository.getByName(name) != null) throw  PBIExistedException("PBI name already exists. Please choose a different name!")
            val proj = projectRepository.getById(projectID) ?: throw ProjectNotExistedException("Project not exists!")

            val pbi = PBI()
            pbi.name = name
            pbi.description = description
            pbi.project = proj
            pbi.priority=priority
            it.flush {

                    pbiRepository.createPBI(pbi)


            }
        }
    }





}