package ua.alexp.redditinterview.repository

import ua.alexp.redditinterview.models.Post
import ua.alexp.redditinterview.models.Result

interface RedditRepository {

    suspend fun loadBestPosts() : Result<List<Post>>

    suspend fun loadTopPosts() : Result<List<Post>>
}