package com.galid.hem.appserver.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "post")
class Post(
    @Id
    var id: ObjectId? = null,
    val title: String,
    val contents: String,
    val mediaObjectIdList: List<String>,
    var deleted: Boolean? = false
) {
    @LastModifiedDate
    lateinit var lastModifiedAt: LocalDateTime
}