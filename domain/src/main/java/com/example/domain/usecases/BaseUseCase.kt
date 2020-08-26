package com.example.domain.usecases

abstract class BaseUseCase<out Type,in Params>{
    abstract suspend fun run(params: Params): Type

}