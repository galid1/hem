package com.galid.hem.appserver.service

import com.galid.hem.appserver.model.Post
import com.galid.hem.appserver.model.dto.PostDto
import com.galid.hem.appserver.model.dto.PostDto.PostRequest
import com.galid.hem.appserver.model.dto.PostDto.PostResponse
import com.galid.hem.appserver.model.exception.ResourceNotExistException
import com.galid.hem.appserver.model.extension.toObjectId
import com.galid.hem.appserver.repository.PostRepository
import com.galid.hem.appserver.util.PostServiceTestUtil.Companion.createMockPost
import com.galid.hem.appserver.util.PostServiceTestUtil.Companion.createMockPostRequest
import com.galid.hem.appserver.util.PostServiceTestUtil.Companion.createMockPostResponse
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import io.mockk.verify
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class PostServiceTest {
    @MockK
    lateinit var postRepository: PostRepository

    lateinit var postService: PostService

    @BeforeEach
    fun setUp() {
        postService = spyk(PostService(postRepository))
    }

    @Test
    fun `존재하지 않는 PostId로 요청시 ResourceNotExistException`() {
        //given
        val notExistPostId = ObjectId.get()

        //when
        every {
            postRepository.findById(notExistPostId)
        } throws ResourceNotExistException(notExistPostId.toString())

        //then
        assertThrows(ResourceNotExistException::class.java) { postService.getPost(notExistPostId) }
    }

    @Test
    fun `Post 저장`() {
        //given
        val postRequest = createMockPostRequest()
        val post = postService.postFrom(postRequest)

        every {
            postService.postFrom(postRequest)
        } returns post
        every {
            postRepository.save(post)
        } returns post

        //when
        val postResponse = postService.create(postRequest)

        //then
        assertEquals(postRequest.title, postResponse.title)
        assertEquals(postRequest.contents, postResponse.contents)
        assertEquals(postRequest.mediaObjectIdList, postResponse.mediaObjectIdList)
        verify(atLeast = 1) { postRepository.save(any()) }
    }

    @Test
    fun `Post 업데이트`() {
        //given
        val updatePostId = ObjectId.get()
        val updateTitle = "UPDATED"
        val updateContents = "UPDATED_CONTENTS"
        val updateMediaObjectIdList = listOf("1", "2")
        val updateRequest = createMockPostRequest(
            id = updatePostId,
            title = updateTitle,
            contents = updateContents,
            mediaObjectIdList = updateMediaObjectIdList
        )

        val post = postService.postFrom(updateRequest)
        every {
            postService.postFrom(updateRequest)
        } returns post
        every {
            postRepository.save(post)
        } returns post

        //when
        val result = postService.update(updatePostId, updateRequest)

        //then
        assertEquals(updateTitle, result.title)
        assertEquals(updateContents, result.contents)
        assertEquals(updateMediaObjectIdList, result.mediaObjectIdList)

        verify(atLeast = 1) {
            postService.update(updatePostId, updateRequest)
        }
    }

    @Test
    fun `Post 삭제`() {
        //given
        val deletePostId = ObjectId.get()
        val deletePost = createMockPost(deletePostId)

        every {
            postRepository.findById(deletePostId)
        } returns Optional.of(deletePost)
        every {
            postRepository.save(deletePost)
        } returns deletePost

        //when
        postService.delete(deletePostId)

        //then
        val deletedPost = postRepository.findById(deletePostId)
            .get()
        assertEquals(deletedPost.deleted, true)

        verify(atLeast = 1) {
            postRepository.findById(deletePostId)
        }
        verify(atLeast = 1) {
            postRepository.save(any())
        }
    }
}