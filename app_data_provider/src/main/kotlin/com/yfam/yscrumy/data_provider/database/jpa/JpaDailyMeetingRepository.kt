package com.yfam.yscrumy.data_provider.database.jpa

import com.yfam.yscrumy.core.data_provider.DailyMeetingRepository
import com.yfam.yscrumy.core.data_provider.PBIRepository
import com.yfam.yscrumy.core.entity.DailyMeeting
import com.yfam.yscrumy.core.entity.PBI
import com.yfam.yscrumy.core.entity.PBIStatus
import javax.persistence.NoResultException

class JpaDailyMeetingRepository : JpaRepository(), DailyMeetingRepository {
    override fun getById(id: Int): DailyMeeting? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select daily from DailyMeeting daily  where daily.id=:id")
                    .setParameter("id", id)
                    .singleResult as DailyMeeting
        } catch (ex: NoResultException) {
            return null
        }
    }

    override fun create(dailyMeeting: DailyMeeting) {
        jpaUnitOfWork.entityManager.persist(dailyMeeting)
    }


    override fun getAll(): List<DailyMeeting> {

        return jpaUnitOfWork.entityManager.createQuery("select daily from DailyMeeting daily")
                .resultList as List<DailyMeeting>

    }



}