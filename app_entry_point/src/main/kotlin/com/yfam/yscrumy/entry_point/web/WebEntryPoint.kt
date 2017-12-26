package com.yfam.yscrumy.entry_point.web

import com.yfam.yscrumy.entry_point.EntryPointBootstrap

/*
 * Bootstrap
 */
open class WebEntryPointBootstrap : EntryPointBootstrap {
  override fun run(vararg args: String?) {
  }
}

/*
 * Config
 */
data class WebEntryPointConfig(val appPort: Int)
