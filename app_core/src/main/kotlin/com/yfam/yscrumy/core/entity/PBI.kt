package com.yfam.yscrumy.core.entity

import java.util.regex.Pattern

class PBI {
    var id: Int ? =null
    var name: String =""
        set(value) { field= if (value.length<5||value.length>50) throw InvalidArgumentException("PBI names must be 5 to 50 characters long ")
        else value }
    var description :String =""
    var status : PBIStatus = PBIStatus.Ready
    var priority:Int=0
    var project : Project? =null
}