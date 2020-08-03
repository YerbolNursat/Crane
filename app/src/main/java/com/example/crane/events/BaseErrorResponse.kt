package com.example.crane.events

import java.io.Serializable

open class BaseErrorResponse(
    var code: Int? = null,
    val message: String? = null,
    var errors: Errors? = null
) : Serializable

data class Errors(
    val smsCode: String?
)