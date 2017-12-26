package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.entity.UserExistedException
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.User
import com.yfam.yscrumy.core.hash
import com.yfam.yscrumy.core.passwordHashingSalt
import org.omg.PortableInterceptor.USER_EXCEPTION

class RegisterUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                      private val userRepository: UserRepository) {
  private var user:User=User()
  fun createUser(username: String, email: String, password: String, phoneNumber: String): User {
    val user = User()
    user.username = username
    user.email = email
    user.hashedPassword = hash(password, passwordHashingSalt())
    user.phoneNumber = phoneNumber
    return user
  }

  fun register(username: String, email: String, password: String, phoneNumber: String) {
    unitOfWorkProvider.get().use {
      userRepository.connect(it)

      if (userRepository.getByUsername(username) != null) throw  UserExistedException("Username already exists. Please choose a different username!")
      user = createUser(username, email, password, phoneNumber)

      it.flush {
        userRepository.createUser(user)
      }
    }
  }
  fun getCurrentUser() = user
}