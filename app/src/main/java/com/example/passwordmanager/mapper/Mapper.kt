package com.example.passwordmanager.mapper

interface Mapper<I, O> {

    fun map(input: I) : O
}