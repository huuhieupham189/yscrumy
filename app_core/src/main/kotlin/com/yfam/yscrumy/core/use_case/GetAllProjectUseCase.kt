
package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.ProjectRepository
import com.yfam.yscrumy.core.data_provider.TeamRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.*

class GetAllProjectUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                           private val projectRepository: ProjectRepository
                        ) {
    fun getAllProject(id: Int): List<Project> {
        unitOfWorkProvider.get().use {
            projectRepository.connect(it)
                var list = projectRepository.getAll(id)
                return list
        }
    }
fun getProjectById(id:Int):Project?{
    unitOfWorkProvider.get().use {
        projectRepository.connect(it)
       return projectRepository.getById(id)
    }
}


}