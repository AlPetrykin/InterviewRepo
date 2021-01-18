package ua.alexp.redditinterview.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ua.alexp.redditinterview.helpers.SingleLiveEvent
import ua.alexp.redditinterview.repository.RedditRepository
import ua.alexp.redditinterview.repository.RedditRepositoryImpl
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel : ViewModel() {

    private val redditRepository : RedditRepository = RedditRepositoryImpl()
    private val isLoadingRunning = AtomicBoolean(false)
    private val showPBContentLoadingLiveEvent = SingleLiveEvent<Boolean>()
    private val errorLiveEvent = SingleLiveEvent<String>()

    val errorLiveData : LiveData<String>
        get() = errorLiveEvent

    val showPBContentLoadingLiveData : LiveData<Boolean>
        get() = showPBContentLoadingLiveEvent

    fun showPBLoading(){
        showPBContentLoadingLiveEvent.value = true
    }

    fun sendError(exception : String){
        errorLiveEvent.value = exception
    }

    fun getRepository() : RedditRepository{
        return redditRepository
    }

    fun setLoadingRunning(run : Boolean){
        isLoadingRunning.set(run)
    }

    fun isLoadingRunning() = isLoadingRunning.get()
}