package ua.alexp.redditinterview.api.interfaces

import retrofit2.http.GET
import ua.alexp.redditinterview.models.Post

interface PostsApi {

    @GET("top.json")
    suspend fun getTopPosts() : List<Post>

    @GET("best.json")
    suspend fun getBestPosts() : List<Post>
}