package ua.alexp.redditinterview.screens.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.alexp.redditinterview.helpers.SingleLiveEvent
import ua.alexp.redditinterview.models.Post
import ua.alexp.redditinterview.models.Result
import ua.alexp.redditinterview.screens.base.BaseViewModel

class TopViewModel : BaseViewModel(){

    private val topPostsLiveEvent = SingleLiveEvent<List<Post>>()

    val topPostsLiveData : LiveData<List<Post>>
        get() = topPostsLiveEvent

    fun loadTopPosts(){
        viewModelScope.launch {
            when(val posts = getRepository().loadTopPosts()){
                is Result.Success ->{
                    topPostsLiveEvent.value = posts.data
                }
                is Result.Error ->{
                    sendError(posts.exception)
                }
            }
        }
    }
}
