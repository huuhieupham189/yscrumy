package com.yfam.yscrumy.core.data_provider

import com.yfam.yscrumy.core.entity.DailyMeeting
import com.yfam.yscrumy.core.entity.PBI

interface DailyMeetingRepository : Repository {
    fun create(dailyMeeting: DailyMeeting)
    fun getById(id: Int): DailyMeeting?
    fun getAll(): List<DailyMeeting>

}