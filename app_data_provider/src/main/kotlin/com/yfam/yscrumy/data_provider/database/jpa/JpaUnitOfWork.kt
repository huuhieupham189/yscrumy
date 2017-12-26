package com.yfam.yscrumy.data_provider.database.jpa

import com.yfam.yscrumy.core.data_provider.UnitOfWork
import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

class JpaUnitOfWork(val entityManager: EntityManager) : UnitOfWork {
  override fun flush(body: () -> Unit) {
    if (entityManager.transaction.isActive)
      throw UnsupportedOperationException("There is another in-progress transaction")

    entityManager.transaction.begin()
    body()
    commit()
  }

  override fun commit() {
    if (!entityManager.transaction.isActive)
      throw UnsupportedOperationException("There is no active transaction to commit")

    entityManager.transaction.commit()
  }

  override fun rollback() {
    if (!entityManager.transaction.isActive)
      throw UnsupportedOperationException("There is no active transaction to rollback")

    entityManager.transaction.rollback()
  }

  override fun close() {
    try {
      if (entityManager.transaction.isActive) {
        commit()
      }
    }
    catch (ex: Throwable) {
      if (entityManager.transaction.isActive) {
        rollback()
      }
    }
    finally {
      entityManager.close()
    }
  }
}

class JpaUnitOfWorkProvider(val entityManagerFactory: EntityManagerFactory) : UnitOfWorkProvider {
  override fun get(): UnitOfWork = JpaUnitOfWork(entityManagerFactory.createEntityManager())
}