package com.yfam.yscrumy.core.entity

import java.util.regex.Pattern

class User {
    var id: Int? = null

    var username: String = ""
        set(value) {
            field = if (value.length < 5 || value.length > 50) throw InvalidArgumentException("Usernames must be 5 to 50 characters long ") else value
        }

    var hashedPassword: String = ""

    var email: String = ""
        set(value) {
            field = if (Pattern.compile(
                    "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
            ).matcher(value).matches()) value
            else throw InvalidArgumentException("Email Address in invalid format")

        }

    var phoneNumber: String = ""
        set(value) {
            val numeric = value.matches("-?\\d+(\\.\\d+)?".toRegex())
            if (!numeric)  throw InvalidArgumentException("Phone Number in invalid format")
            if (value.length<10 || value.length>11) throw InvalidArgumentException("Phone number  must be 10 to 11 characters long")
            field = value
        }

    var team: Team? = null
}