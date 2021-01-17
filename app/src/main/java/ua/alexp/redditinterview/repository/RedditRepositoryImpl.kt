package ua.alexp.redditinterview.repository

import retrofit2.HttpException
import ua.alexp.redditinterview.api.RestApi
import ua.alexp.redditinterview.models.Post
import ua.alexp.redditinterview.models.Result

class RedditRepositoryImpl : RedditRepository {

    override suspend fun loadBestPosts(): Result<List<Post>> {
        return try{
            val posts = RestApi.api.getBestPosts()
            Result.Success(posts)
        }catch (e : HttpException){
            Result.Error(exception = e.message())
        }
    }

    override suspend fun loadTopPosts(): Result<List<Post>> {
        return try{
            val posts = RestApi.api.getTopPosts()
            Result.Success(posts)
        }catch (e : HttpException){
            Result.Error(exception = e.message())
        }
    }
}