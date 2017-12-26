package com.yfam.yscrumy.data_provider.database.jpa

import com.yfam.yscrumy.core.data_provider.Repository
import com.yfam.yscrumy.core.data_provider.UnitOfWork
import com.yfam.yscrumy.core.entity.Invitation

open class JpaRepository : Repository {
  protected lateinit var jpaUnitOfWork: JpaUnitOfWork

  override fun connect(unitOfWork: UnitOfWork) {
      jpaUnitOfWork = unitOfWork as JpaUnitOfWork

  }}


