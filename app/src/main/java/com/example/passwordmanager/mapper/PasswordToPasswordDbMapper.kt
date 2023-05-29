package com.example.passwordmanager.mapper

import com.example.passwordmanager.database.entity.PasswordDb
import com.example.passwordmanager.repository.model.Password

interface PasswordToPasswordDbMapper : Mapper<Password, PasswordDb>

internal class PasswordToPasswordDbMapperImpl : PasswordToPasswordDbMapper {

    override fun map(input: Password): PasswordDb =
        PasswordDb(
            name = input.name,
            login = input.login,
            password = input.login,
            iv = input.iv
        )
}