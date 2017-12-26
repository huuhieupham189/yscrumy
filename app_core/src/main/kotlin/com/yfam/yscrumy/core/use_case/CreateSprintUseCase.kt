package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.PBIRepository
import com.yfam.yscrumy.core.data_provider.ProjectRepository
import com.yfam.yscrumy.core.data_provider.SprintRepository
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.entity.*
import java.time.Period

class CreateSprintUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                          private val sprintRepository: SprintRepository,
                          private val pbiRepository: PBIRepository) {
    fun createSprint(sprint: Sprint) {
        unitOfWorkProvider.get().use {
            sprintRepository.connect(it)
            if(Period.between(sprint.startdate,sprint.enddate).days<=0) throw DateException("Start Date must be sooner than End Date!")
            it.flush {

                sprintRepository.createUser(sprint)


            }
        }
    }

    fun getSprintbyPrjID(id: Int): List<Sprint> {
        unitOfWorkProvider.get().use {
            sprintRepository.connect(it)
            return sprintRepository.getSprintByProID(id)

        }
    }

    fun getSprintById(id: Int): Sprint? {
        unitOfWorkProvider.get().use {
            sprintRepository.connect(it)
            return sprintRepository.getById(id)

        }
    }

    fun updateSprintPBI(spid: Int, pbiid: Int) {
        unitOfWorkProvider.get().use {
            sprintRepository.connect(it)
            pbiRepository.connect(it)
            var pbi = pbiRepository.getById(pbiid) as PBI
            var sprint = sprintRepository.getById(spid) as Sprint
            it.flush {
pbi.status=PBIStatus.Processing
                sprint.sprintpbi=pbi
                sprint.status=SprintStatus.Processing
            }
        }
    }
    fun completedSprintPBI(spid: Int, pbiid: Int) {
        unitOfWorkProvider.get().use {
            sprintRepository.connect(it)
            pbiRepository.connect(it)
            var pbi = pbiRepository.getById(pbiid) as PBI
            var sprint = sprintRepository.getById(spid) as Sprint
            it.flush {
                pbi.status=PBIStatus.Complete
                sprint.sprintpbi=pbi
                sprint.status=SprintStatus.Complete
            }
        }
    }
    fun updateDaysleft(spid:Int,day:Int) {
        unitOfWorkProvider.get().use {
            sprintRepository.connect(it)

            var sprint = sprintRepository.getById(spid) as Sprint
            it.flush {
               sprint.daysleft=day
            }
        }
    }

}