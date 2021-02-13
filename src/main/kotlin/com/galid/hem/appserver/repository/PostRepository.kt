package com.galid.hem.appserver.repository

import com.galid.hem.appserver.model.Post
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository : MongoRepository<Post, ObjectId>, PostCustomRepository {}