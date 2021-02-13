package com.galid.hem.appserver.service

import com.galid.hem.appserver.model.Post
import com.galid.hem.appserver.model.dto.PostDto
import com.galid.hem.appserver.model.dto.PostDto.PostRequest
import com.galid.hem.appserver.model.dto.PostDto.PostResponse
import com.galid.hem.appserver.model.exception.ResourceNotExistException
import com.galid.hem.appserver.repository.PostRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun create(request: PostRequest): PostResponse {
        val post = postFrom(request)

        val savedPost = postRepository.save(post)

        return responseFrom(savedPost)
    }

    fun getPost(postId: ObjectId): PostResponse {
        val post = postRepository.findById(postId)
            .orElseThrow { throw ResourceNotExistException(postId.toString()) }

        return responseFrom(post)
    }

    fun getPostList(lastFetchId: ObjectId?, fetchSize: Int): List<PostResponse> {
        return postRepository.findAllBefore(lastFetchId, fetchSize)
            .map {
                PostResponse(
                    postId = it.id.toString(),
                    title = it.title,
                    contents = it.contents,
                    mediaObjectIdList = it.mediaObjectIdList)
            }
    }

    fun update(postId: ObjectId, request: PostRequest): PostResponse {
        val post = postFrom(request)
        post.id = postId

        val updatedPost = postRepository.save(post)
        return responseFrom(updatedPost)
    }

    fun delete(postId: ObjectId) {
        val existPost = postRepository.findById(postId)
            .orElseThrow { throw ResourceNotExistException(postId.toString()) }

        existPost.deleted = true

        postRepository.save(existPost)
    }

    internal fun postFrom(request: PostRequest) = Post(
        title = request.title,
        contents = request.contents,
        mediaObjectIdList = request.mediaObjectIdList
    )

    internal fun responseFrom(savedPost: Post): PostResponse {
        return PostResponse(
            postId = savedPost.id.toString(),
            title = savedPost.title,
            contents = savedPost.contents,
            mediaObjectIdList = savedPost.mediaObjectIdList
        )
    }
}