package com.galid.hem.appserver.repository

import com.galid.hem.appserver.model.Post
import org.bson.types.ObjectId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class PostCustomRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : PostCustomRepository {
    override fun findAllBefore(postId: ObjectId?, fetchSize: Int): List<Post> {
        val query = Query()
        if (postId != null) {
            query.addCriteria(Criteria("id").`is`(postId))
        }

        return mongoTemplate.find(
            query.with(
                PageRequest.of(
                    0,
                    fetchSize,
                    Sort.by(Sort.Direction.DESC, "id")
                )
            ), Post::class.java
        )
    }
}