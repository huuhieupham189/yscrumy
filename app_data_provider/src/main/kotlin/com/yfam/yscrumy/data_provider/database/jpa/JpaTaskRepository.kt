package com.yfam.yscrumy.data_provider.database.jpa

import com.yfam.yscrumy.core.data_provider.SprintRepository
import com.yfam.yscrumy.core.data_provider.TaskRepository
import com.yfam.yscrumy.core.entity.SprintStatus
import com.yfam.yscrumy.core.entity.Task
import com.yfam.yscrumy.core.entity.TaskStatus
import javax.persistence.NoResultException

class JpaTaskRepository : JpaRepository(), TaskRepository {
    override fun getById(id: Int): Task? {
        try {
            return jpaUnitOfWork.entityManager.createQuery("select task from Task task where task.id=:id")
                    .setParameter("id", id)
                    .singleResult as Task
        } catch (ex: NoResultException) {
            return null
        }
    }

    override fun createTask(task: Task) {
        jpaUnitOfWork.entityManager.persist(task)

    }

    //    override fun getByUsername(username: String): User? {
//        try {
//            return jpaUnitOfWork.entityManager.createQuery("select user from User user where user.username=:username")
//                    .setParameter("username", username)
//                    .singleResult as User
//        } catch (ex: NoResultException) {
//            return null
//        }
//    }
//    override fun searchUsername(username: String):List<User>{
//
//            return jpaUnitOfWork.entityManager.createQuery("select user from User user where user.username like CONCAT ('%',:username,'%') and user.team.id=null")
//                    .setParameter("username",username)
//                    .resultList as List<User>
//    }
//    override fun searchUsernameInProject(username:String,id:Int,pjid:Int):List<User>{
//        return jpaUnitOfWork.entityManager.createQuery("select user from User user where user.username like CONCAT ('%',:username,'%') and user.team.id=:id and user.id not in (select p.user_id from Project_member p where p.project_id=:pjid)")
//                .setParameter("username",username)
//                .setParameter("id",id)
//                .setParameter("pjid",pjid)
//                .resultList as List<User>
//    }
//    override fun getSprintByProID(id:Int): List<Sprint>{
//        return jpaUnitOfWork.entityManager.createQuery("select sp from Sprint sp where sp.project.id=:id")
//                .setParameter("id",id)
//                .resultList as List<Sprint>
//    }
    override fun getallTask(uid: Int, sprintid: Int,status:TaskStatus): List<Task> {
        return jpaUnitOfWork.entityManager.createQuery("select task from Task task where task.assignTo.id=:uid and task.sprint.id=:sprintid and task.status=:status")
                .setParameter("uid", uid)
                .setParameter("sprintid", sprintid)
                .setParameter("status",status)
                .resultList as List<Task>
    }

    override fun getAllTaskAllSprint(uid:Int, status: TaskStatus):List<Task>{
        return jpaUnitOfWork.entityManager.createQuery("select task from Task task  where task.assignTo.id=:uid and task.status=:status")
                .setParameter("uid",uid)
                .setParameter("status",status)
                .resultList as List<Task>
    }
    override fun getallTasktest(uid: Int, sprintid: Int): List<Task> {
        return jpaUnitOfWork.entityManager.createQuery("select task from Task task where task.assignTo.id<>:uid and task.sprint.id=:sprintid and task.status=:status and task.tester.id=:uid")
                .setParameter("uid", uid)
                .setParameter("sprintid", sprintid)
                .setParameter("status",TaskStatus.Testing)
                .resultList as List<Task>
    }


}