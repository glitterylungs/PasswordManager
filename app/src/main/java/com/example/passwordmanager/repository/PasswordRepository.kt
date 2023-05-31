package com.example.passwordmanager.repository

import com.example.passwordmanager.database.dao.PasswordDao
import com.example.passwordmanager.mapper.PasswordDbToPasswordMapper
import com.example.passwordmanager.mapper.PasswordToPasswordDbMapper
import com.example.passwordmanager.repository.model.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PasswordRepository {

    fun getAllPasswords(): Flow<List<Password>>

    fun getPassword(id: Int): Flow<Password>

    suspend fun insertPassword(password: Password)
}

internal class PasswordRepositoryImpl(
    private val passwordDao: PasswordDao,
    private val passwordDbToPasswordMapper: PasswordDbToPasswordMapper,
    private val passwordToPasswordDbMapper: PasswordToPasswordDbMapper,
) : PasswordRepository {

    override fun getAllPasswords(): Flow<List<Password>> =
        passwordDao.getAllPasswords().map { passwords ->
            passwords.map { password ->
                passwordDbToPasswordMapper.map(password)
            }
        }

    override fun getPassword(id: Int): Flow<Password> =
        passwordDao.getPassword(id).map { password ->
            passwordDbToPasswordMapper.map(password)
        }

    override suspend fun insertPassword(password: Password) =
        passwordDao.insertPassword(passwordToPasswordDbMapper.map(password))
}