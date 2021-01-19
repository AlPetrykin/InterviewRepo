package ua.alexp.redditinterview.repository

import android.app.Application
import retrofit2.HttpException
import ua.alexp.redditinterview.api.RestApi
import ua.alexp.redditinterview.models.ChildrenPost
import ua.alexp.redditinterview.models.Post
import ua.alexp.redditinterview.models.Result

class RedditRepositoryImpl(application : Application) : RedditRepository {

    companion object{
        lateinit var repository: RedditRepository

        fun initRepository(application: Application){
            repository = RedditRepositoryImpl(application)
        }
    }

    private val api = RestApi().initRetrofit(application)

    private var bestPostsAfter = ""
    private var topPostsAfter = ""

    private val bestList = ArrayList<ChildrenPost>()
    private val topList = ArrayList<ChildrenPost>()

    override suspend fun loadBestPosts(): Result<List<ChildrenPost>> {
        return try{
            val posts = api.getBestPosts(after = bestPostsAfter)
            bestPostsAfter = posts.data.after
            val list = posts.data.children
            bestList.addAll(list)
            Result.Success(list)
        }catch (e : HttpException){
            Result.Error(exception = e.message())
        }
    }

    override suspend fun loadTopPosts(): Result<List<ChildrenPost>> {
        return try{
            val posts = api.getTopPosts(after = topPostsAfter)
            topPostsAfter = posts.data.after
            val list = posts.data.children
            topList.addAll(list)
            Result.Success(list)
        }catch (e : HttpException){
            Result.Error(exception = e.message())
        }
    }

    override suspend fun preLoadBestPosts(): Result<List<ChildrenPost>> {
        return if (bestList.isNotEmpty()){
            Result.Success(bestList)
        }else{
            loadBestPosts()
        }
    }

    override suspend fun preLoadTopPosts(): Result<List<ChildrenPost>> {
        return if (topList.isNotEmpty()){
            Result.Success(topList)
        }else{
            loadTopPosts()
        }
    }
}