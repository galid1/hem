package com.galid.hem.appserver.presenter

import com.galid.hem.appserver.model.const.DEFAULT_POST_FETCH_SIZE
import com.galid.hem.appserver.model.dto.PostDto.PostRequest
import com.galid.hem.appserver.model.dto.PostDto.PostResponse
import com.galid.hem.appserver.model.dto.Response
import com.galid.hem.appserver.model.extension.toObjectId
import com.galid.hem.appserver.service.PostService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService
) {
    @PostMapping
    fun createPost(
        @RequestBody request: PostRequest
    ): Response<PostResponse> {
        return Response(
            data = postService.create(request)
        )
    }

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable("postId") postId: String
    ): Response<PostResponse> {
        return Response(
            data = postService.getPost(postId.toObjectId())
        )
    }

    @GetMapping
    fun getPostList(
        @RequestParam(value = "fetch_size", required = false) fetchSize: Int?,
        @RequestParam(value = "last_fetch_id", required = false) lastFetchId: String? = null
    ): Response<List<PostResponse>> {
        return Response(
            data = postService.getPostList(
                fetchSize = fetchSize?: DEFAULT_POST_FETCH_SIZE,
                lastFetchId = lastFetchId?.let { it.toObjectId() }
            )
        )
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: String,
        @RequestBody request: PostRequest
    ): Response<PostResponse> {
        return Response(
            data = postService.update(postId.toObjectId(), request)
        )
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable("postId") postId: String
    ) {
        postService.delete(postId.toObjectId())
    }
}