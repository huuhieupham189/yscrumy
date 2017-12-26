package com.yfam.yscrumy.data_provider.database

data class DatabaseDataProviderConfig(val host: String, val port: Int, val catalog: String, val utf8: Boolean,
                                      val username: String, val password: String,
                                      val debug: Boolean) {
}