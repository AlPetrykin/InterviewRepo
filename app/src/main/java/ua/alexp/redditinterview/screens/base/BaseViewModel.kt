package ua.alexp.redditinterview.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ua.alexp.redditinterview.helpers.SingleLiveEvent
import ua.alexp.redditinterview.repository.RedditRepository
import ua.alexp.redditinterview.repository.RedditRepositoryImpl

abstract class BaseViewModel : ViewModel() {

    private val redditRepository : RedditRepository = RedditRepositoryImpl()

    private val errorLiveEvent = SingleLiveEvent<String>()

    val errorLiveData : LiveData<String>
        get() = errorLiveEvent

    fun sendError(exception : String){
        errorLiveEvent.value = exception
    }

    fun getRepository() : RedditRepository{
        return redditRepository
    }
}