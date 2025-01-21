package com.healthbuddy.chatbot


data class DataResponse(
    val isUser: Int,
    val prompt: String,
    val imageUri: String,
)