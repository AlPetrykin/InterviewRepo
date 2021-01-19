package ua.alexp.redditinterview.screens.best

import ua.alexp.redditinterview.enums.PostType
import ua.alexp.redditinterview.screens.base.BaseViewModel

class BestViewModel : BaseViewModel() {

    fun loadBestPosts() {
        loadPosts(PostType.BEST)
    }

    fun preLoadPosts(){
        preLoadPosts(PostType.BEST)
    }
}