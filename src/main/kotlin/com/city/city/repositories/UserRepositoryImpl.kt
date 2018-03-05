package com.city.city.repositories

import com.city.city.Users
import com.city.city.entities.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
@Transactional
class UserRepositoryImpl: UserRepository {


    override fun createTable() = SchemaUtils.create(Users)


    private fun toRow(u: User): Users.(UpdateBuilder<*>) -> Unit = {
        it[id]=u.id
        it[firstName] = u.firstName
        it[lastName] = u.lastName


    }

    private fun fromRow(r: ResultRow) =
            User(r[Users.id],r[Users.firstName], r[Users.lastName])

    override fun create(m: User): User {
        Users.insert(toRow(m))
        return m
    }

    override fun findAll() = Users.selectAll().map { fromRow(it) }
    override fun deleteAll() = Users.deleteAll()
    override fun delete(firstName: String) = Users.deleteWhere { Users.firstName like firstName }

    override fun updateUser(id: Int, f: String) {
        Users.update({ Users.id eq id }) { it[firstName] = f }

    }

    override fun findByid(id: Int) =
            Users.select { Users.id.eq(id) }.firstOrNull()


}

