package com.yfam.yscrumy.core.data_provider

interface Repository {
  fun connect(unitOfWork: UnitOfWork)
}