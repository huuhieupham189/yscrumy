package com.yfam.yscrumy.core.entity

open class CoreException(val errorCode: String, val userMessage: String, val developerMessage: String = userMessage, val moreInformation: String = "")
    : RuntimeException()

class InvalidArgumentException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000001", userMessage, developerMessage, moreInformation)

class UserExistedException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000002", userMessage, developerMessage, moreInformation)

class TeamExistedException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000003", userMessage, developerMessage, moreInformation)

class AlreadyJoinATeamException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000004", userMessage, developerMessage, moreInformation)

class ProjectExistedException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000005", userMessage, developerMessage, moreInformation)
class PBIExistedException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000006", userMessage, developerMessage, moreInformation)
class ProjectNotExistedException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000007", userMessage, developerMessage, moreInformation)
class CantRemoveException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000008", userMessage, developerMessage, moreInformation)
class PBINameExistedException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000009", userMessage, developerMessage, moreInformation)
class PasswordException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000010", userMessage, developerMessage, moreInformation)
class DateException(userMessage: String = "", developerMessage: String = userMessage, moreInformation: String = "")
    : CoreException("CE000011", userMessage, developerMessage, moreInformation)