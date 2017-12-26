package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.InvalidArgumentException
import com.yfam.yscrumy.core.entity.User
import com.yfam.yscrumy.core.hash
import com.yfam.yscrumy.core.passwordHashingSalt

class GetAndEditProfileUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                               private val userRepository: UserRepository) {
    var user: User? = null

    fun getProfile(id: Int?):User?{
        unitOfWorkProvider.get().use {
            userRepository.connect(it)
            user = userRepository.getById(id as Int)
            if (user == null) throw InvalidArgumentException("User does not exist")
            return user
        }
    }
fun searchUser(username: String):List<User>{
    unitOfWorkProvider.get().use {
        userRepository.connect(it)
        return userRepository.searchUsername(username)
    }
}
    fun searchUserInProject(username: String,id:Int,pjid:Int):List<User>{
        unitOfWorkProvider.get().use {
            userRepository.connect(it)
            return userRepository.searchUsernameInProject(username,id,pjid)
        }
    }


}