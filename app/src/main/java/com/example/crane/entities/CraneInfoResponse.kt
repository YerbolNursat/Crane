package com.example.crane.entities


data class CraneInfoResponse(
    val list: List<CraneInfo>
)

data class CraneInfo(
    val required: Boolean,
    val question: String,
    val subQuestions: List<CraneInfoSubQuestions>
)

data class CraneInfoSubQuestions(
    val question: String
)
