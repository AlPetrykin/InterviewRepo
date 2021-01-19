package ua.alexp.redditinterview.repository

import ua.alexp.redditinterview.models.ChildrenPost
import ua.alexp.redditinterview.models.Post
import ua.alexp.redditinterview.models.Result

interface RedditRepository {

    suspend fun loadBestPosts() : Result<List<ChildrenPost>>

    suspend fun loadTopPosts() : Result<List<ChildrenPost>>

    suspend fun preLoadBestPosts() : Result<List<ChildrenPost>>

    suspend fun preLoadTopPosts() : Result<List<ChildrenPost>>
}