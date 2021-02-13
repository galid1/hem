package com.galid.hem.appserver.model.dto

import org.bson.types.ObjectId

class PostDto {
    data class PostRequest(
        val title: String,
        val contents: String,
        val mediaObjectIdList: List<String>,
    )

    data class PostResponse(
        val postId: String,
        val title: String,
        val contents: String,
        val mediaObjectIdList: List<String>
    )
}