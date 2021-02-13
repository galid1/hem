package com.galid.hem.appserver.model.exception

import com.galid.hem.appserver.model.dto.Response
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PostExceptionHandler {
    @ExceptionHandler(value = [ResourceNotExistException::class])
    fun handleResourceNotExistException(e: ResourceNotExistException): Response<Void> {
        return Response(
            status = HttpStatus.NOT_FOUND.value(),
            code = e.code,
            message = e.message
        )
    }
}