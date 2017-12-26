package com.yfam.yscrumy.core.use_case

import com.yfam.yscrumy.core.data_provider.UnitOfWorkProvider
import com.yfam.yscrumy.core.data_provider.UserRepository
import com.yfam.yscrumy.core.entity.InvalidArgumentException
import com.yfam.yscrumy.core.entity.PasswordException
import com.yfam.yscrumy.core.entity.User
import com.yfam.yscrumy.core.entity.UserExistedException
import com.yfam.yscrumy.core.hash
import com.yfam.yscrumy.core.passwordHashingSalt

class EditProfileUseCase(private val unitOfWorkProvider: UnitOfWorkProvider,
                         private val userRepository: UserRepository,
private val teamUseCase: CreateTeamUseCase) {


    fun edit(id: Int, user: User,currentpass:String) {
        unitOfWorkProvider.get().use {
            userRepository.connect(it)
            var pass=hash(currentpass, passwordHashingSalt())
            val getUser = userRepository.getById(id) ?: throw  InvalidArgumentException("User does not exist")
            if(getUser.hashedPassword!=pass) throw PasswordException("Current password is wrong!")
            it.flush {
                if(user.email!="") getUser.email = user.email
                if(user.hashedPassword!="") getUser.hashedPassword=hash(user.hashedPassword, passwordHashingSalt())
                if(user.phoneNumber!="") getUser.phoneNumber = user.phoneNumber
            }
        }
    }
    fun acceptTeam(id:Int,teamid:Int){
        unitOfWorkProvider.get().use {
            userRepository.connect(it)
            val getUser = userRepository.getById(id) ?: throw  InvalidArgumentException("User does not exist")
            val team=teamUseCase.getTeamById(teamid)
            it.flush {
                getUser.team=team
            }
        }
    }
    fun leaveTeam(id:Int){
        unitOfWorkProvider.get().use {
            userRepository.connect(it)
           var user= userRepository.getById(id) as User
            it.flush {
                user.team=null
            }
        }
    }
    fun getUserByTeamId(id:Int):List<User>{
        unitOfWorkProvider.get().use {
            userRepository.connect(it)
        return userRepository.getUserByTeamId(id)
        }
    }
}