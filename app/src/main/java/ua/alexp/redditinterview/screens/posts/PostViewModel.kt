package ua.alexp.redditinterview.screens.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.alexp.redditinterview.enums.PostType
import ua.alexp.redditinterview.helpers.SingleLiveEvent
import ua.alexp.redditinterview.models.ChildrenPost
import ua.alexp.redditinterview.models.Result
import ua.alexp.redditinterview.repository.RedditRepository
import ua.alexp.redditinterview.repository.RedditRepositoryImpl
import java.util.concurrent.atomic.AtomicBoolean

class PostViewModel : ViewModel() {

    private val redditRepository : RedditRepository = RedditRepositoryImpl.repository
    private val isLoadingRunning = AtomicBoolean(false)
    private val showLoadingLiveEvent = SingleLiveEvent<Boolean>()
    private val errorLiveEvent = SingleLiveEvent<String>()
    private val postsLiveEvent = MutableLiveData<List<ChildrenPost>>()

    val errorLiveData : LiveData<String> = errorLiveEvent

    val showLoadingLiveData : LiveData<Boolean> = showLoadingLiveEvent

    val postsLiveData : LiveData<List<ChildrenPost>> = postsLiveEvent

    private fun showPBLoading(){
        showLoadingLiveEvent.value = true
    }

    private fun sendError(exception : String){
        errorLiveEvent.value = exception
    }

    private fun setLoadingRunning(run : Boolean){
        isLoadingRunning.set(run)
    }

    private fun isLoadingRunning() = isLoadingRunning.get()

    fun loadPosts(type : PostType){
        if (!isLoadingRunning()) {
            setLoadingRunning(true)
            viewModelScope.launch {
                showPBLoading()
                checkResponse(initPostsType(type))
            }
        }
    }

    fun preLoadPosts(type: PostType){
        viewModelScope.launch {
            showPBLoading()
            checkResponse(initPrePostsType(type))
        }
    }

    private fun checkResponse(posts : Result<List<ChildrenPost>>){
        when (posts) {
            is Result.Success -> {
                setLoadingRunning(false)
                postsLiveEvent.postValue(posts.data)
            }
            is Result.Error -> {
                setLoadingRunning(false)
                sendError(posts.exception)
            }
        }
    }

    private suspend fun initPrePostsType(type: PostType) : Result<List<ChildrenPost>>{
        return if (type == PostType.BEST){
            redditRepository.preLoadBestPosts()
        }else{
            redditRepository.preLoadTopPosts()
        }
    }

    private suspend fun initPostsType(type: PostType) : Result<List<ChildrenPost>>{
        return if (type == PostType.BEST){
            redditRepository.loadBestPosts()
        }else{
            redditRepository.loadTopPosts()
        }
    }
}