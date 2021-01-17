package ua.alexp.redditinterview.models

data class Post(
    val id : String,
    val author : String,
    val thumbnail : String,
    val num_comments : Int,
    val url : String,
    val created_utc : Long
)