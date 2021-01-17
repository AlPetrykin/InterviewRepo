package ua.alexp.redditinterview.screens.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.alexp.redditinterview.helpers.SingleLiveEvent
import ua.alexp.redditinterview.models.ChildrenPost
import ua.alexp.redditinterview.models.Result
import ua.alexp.redditinterview.screens.base.BaseViewModel

class TopViewModel : BaseViewModel(){

    private val topPostsLiveEvent = SingleLiveEvent<List<ChildrenPost>>()

    val topPostsLiveData : LiveData<List<ChildrenPost>>
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
