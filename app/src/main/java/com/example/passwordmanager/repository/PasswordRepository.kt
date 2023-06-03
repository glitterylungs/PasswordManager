package com.example.passwordmanager.repository

import com.example.passwordmanager.database.dao.PasswordDao
import com.example.passwordmanager.mapper.PasswordDbToPasswordMapper
import com.example.passwordmanager.mapper.PasswordToPasswordDbMapper
import com.example.passwordmanager.repository.model.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

interface PasswordRepository {

    fun getAllPasswords(): Flow<List<Password?>>

    fun getPassword(id: Int): Flow<Password>

    suspend fun insertPassword(password: Password)

    suspend fun updatePassword(id: Int, name: String, login: String)

    suspend fun deletePassword(id: Int)
}

internal class PasswordRepositoryImpl(
    private val passwordDao: PasswordDao,
    private val passwordDbToPasswordMapper: PasswordDbToPasswordMapper,
    private val passwordToPasswordDbMapper: PasswordToPasswordDbMapper,
) : PasswordRepository {

    override fun getAllPasswords(): Flow<List<Password?>> =
        passwordDao.getAllPasswords().map { passwords ->
            passwords.map { password ->
                password?.let {
                    passwordDbToPasswordMapper.map(it)
                }
            }
        }

    override fun getPassword(id: Int): Flow<Password> =
        passwordDao.getPassword(id).mapNotNull { password ->
            passwordDbToPasswordMapper.map(password)
        }

    override suspend fun insertPassword(password: Password) =
        passwordDao.insertPassword(passwordToPasswordDbMapper.map(password))

    override suspend fun updatePassword(id: Int, name: String, login: String) =
        passwordDao.updatePassword(id, name, login)

    override suspend fun deletePassword(id: Int) =
        passwordDao.deletePassword(id)
}