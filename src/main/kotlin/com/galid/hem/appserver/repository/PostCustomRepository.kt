package com.galid.hem.appserver.repository

import com.galid.hem.appserver.model.Post
import org.bson.types.ObjectId

interface PostCustomRepository {
    fun findAllBefore(postId: ObjectId?, fetchSize: Int): List<Post>
}