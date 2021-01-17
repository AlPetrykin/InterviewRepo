package ua.alexp.redditinterview.api.interfaces

import retrofit2.http.GET
import ua.alexp.redditinterview.models.KindPost
import ua.alexp.redditinterview.models.Post

interface PostsApi {

    @GET("top.json")
    suspend fun getTopPosts() : KindPost

    @GET("best.json")
    suspend fun getBestPosts() : KindPost
}