package com.galid.hem.appserver.model.dto

import org.springframework.http.HttpStatus

data class Response<T>(
    val data: T? = null,
    val status: Int = HttpStatus.OK.value(),
    val code: Int = 0,
    val message: String? = null
)