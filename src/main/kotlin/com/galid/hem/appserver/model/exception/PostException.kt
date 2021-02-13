package com.galid.hem.appserver.model.exception

class ResourceNotExistException(
    val param: String? = null,
    val code: Int = EXCEPTION_CODE_POST_NOT_EXIST,
    override val message: String = "Resource not found"
): RuntimeException(message)
