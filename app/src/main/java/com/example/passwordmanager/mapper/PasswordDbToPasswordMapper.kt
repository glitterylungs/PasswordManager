package com.example.passwordmanager.mapper

import com.example.passwordmanager.database.entity.PasswordDb
import com.example.passwordmanager.repository.model.Password

interface PasswordDbToPasswordMapper : Mapper<PasswordDb, Password>

class PasswordDbToPasswordMapperImpl : PasswordDbToPasswordMapper {

    override fun map(input: PasswordDb): Password =
        Password(
            id = input.id,
            name = input.name,
            login = input.login,
            password = input.login,
            iv = input.iv
        )
}