package ua.alexp.redditinterview.api.interfaces

import retrofit2.http.GET
import retrofit2.http.Query
import ua.alexp.redditinterview.models.KindPost
import ua.alexp.redditinterview.models.Post

interface PostsApi {

    @GET("top.json")
    suspend fun getTopPosts(@Query("after") after : String) : KindPost

    @GET("best.json")
    suspend fun getBestPosts(@Query("after") after: String) : KindPost
}