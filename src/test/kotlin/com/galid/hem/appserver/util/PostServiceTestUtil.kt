package com.galid.hem.appserver.util

import com.galid.hem.appserver.model.Post
import com.galid.hem.appserver.model.dto.PostDto
import com.galid.hem.appserver.model.dto.PostDto.PostRequest
import com.galid.hem.appserver.model.dto.PostDto.PostResponse
import org.bson.types.ObjectId

class PostServiceTestUtil {
    companion object {
        val mockPostId: ObjectId = ObjectId.get()
        val mockTitle: String = "TEST"
        val mockContents: String = "TEST_CONTENT"
        val mockMediaObjectIdList: List<String> = emptyList()

        fun createMockPost(
            id: ObjectId? = null,
            title: String? = mockTitle,
            contents: String? = mockContents,
            mediaObjectIdList: List<String>? = mockMediaObjectIdList
        ): Post {
            return Post(
                title = title!!,
                contents = contents!!,
                mediaObjectIdList = mediaObjectIdList!!
            )
        }

        fun createMockPostRequest(
            id: ObjectId? = null,
            title: String? = mockTitle,
            contents: String? = mockContents,
            mediaObjectIdList: List<String>? = mockMediaObjectIdList
        ): PostRequest {
            return PostRequest(
                title = title!!,
                contents = contents!!,
                mediaObjectIdList = mediaObjectIdList!!
            )
        }

        fun createMockPostResponse(
            title: String? = mockTitle,
            contents: String? = mockContents,
            mediaObjectIdList: List<String>? = mockMediaObjectIdList
        ): PostResponse {
            return PostResponse(
                postId = mockPostId.toString(),
                title = title!!,
                contents = contents!!,
                mediaObjectIdList = mediaObjectIdList!!
            )
        }
    }
}