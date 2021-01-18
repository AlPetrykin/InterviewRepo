package ua.alexp.redditinterview.screens.best

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.alexp.redditinterview.helpers.SingleLiveEvent
import ua.alexp.redditinterview.models.ChildrenPost
import ua.alexp.redditinterview.models.Post
import ua.alexp.redditinterview.models.Result
import ua.alexp.redditinterview.screens.base.BaseViewModel

class BestViewModel : BaseViewModel() {

    private val bestPostsLiveEvent = MutableLiveData<List<ChildrenPost>>()

    val bestPostsLiveData: LiveData<List<ChildrenPost>>
        get() = bestPostsLiveEvent

    fun loadBestPosts() {
        viewModelScope.launch {
            when (val posts = getRepository().loadBestPosts()) {
                is Result.Success -> {
                    bestPostsLiveEvent.postValue(posts.data)
                }
                is Result.Error -> {
                    sendError(posts.exception)
                }
            }
        }
    }
}