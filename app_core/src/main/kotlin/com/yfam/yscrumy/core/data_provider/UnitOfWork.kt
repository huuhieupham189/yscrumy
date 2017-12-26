package com.yfam.yscrumy.core.data_provider

import java.io.Closeable

interface UnitOfWork : Closeable {
    fun flush(body: () -> Unit)

    fun commit()

    fun rollback()
}

interface UnitOfWorkProvider {
    fun get(): UnitOfWork
}