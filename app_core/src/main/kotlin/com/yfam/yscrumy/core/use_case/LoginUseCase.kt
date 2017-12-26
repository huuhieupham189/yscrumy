package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.InvalidArgumentException
import com.yfam.yscrumy.core.entity.User
import com.yfam.yscrumy.core.hash
import com.yfam.yscrumy.core.passwordHashingSalt

class LoginUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                   private val userRepository: UserRepository) {
    var user: User? = null
    fun login(username: String, password: String) {
        unitOfWorkProvider.get().use {
            userRepository.connect(it)
            user = userRepository.getByUsername(username)
            if (user == null) throw InvalidArgumentException("Username does not exist")
            val hashedpassword = hash(password, passwordHashingSalt())
            if (hashedpassword != (user as User).hashedPassword) throw InvalidArgumentException("The password is incorrect. Try again!")

        }
    }

    fun getCurrentUser() = user
}