package ua.alexp.redditinterview.screens.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.alexp.redditinterview.helpers.SingleLiveEvent
import ua.alexp.redditinterview.models.ChildrenPost
import ua.alexp.redditinterview.models.Result
import ua.alexp.redditinterview.screens.base.BaseViewModel

class TopViewModel : BaseViewModel(){

    private val topPostsLiveEvent = MutableLiveData<List<ChildrenPost>>()

    val topPostsLiveData : LiveData<List<ChildrenPost>>
        get() = topPostsLiveEvent

    fun loadTopPosts(){
        if (!isLoadingRunning()) {
            setLoadingRunning(true)
            viewModelScope.launch {
                showPBLoading()
                when (val posts = getRepository().loadTopPosts()) {
                    is Result.Success -> {
                        setLoadingRunning(false)
                        topPostsLiveEvent.value = posts.data
                    }
                    is Result.Error -> {
                        setLoadingRunning(false)
                        sendError(posts.exception)
                    }
                }
            }
        }
    }
}
