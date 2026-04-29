package com.example.splitbuddy.domain.usecase.user

import com.example.splitbuddy.data.remote.user.CreateUserRequest
import com.example.splitbuddy.domain.repository.UserRepository

class CreateUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(request: CreateUserRequest): Boolean {
        return repository.createUser(request)
    }
}