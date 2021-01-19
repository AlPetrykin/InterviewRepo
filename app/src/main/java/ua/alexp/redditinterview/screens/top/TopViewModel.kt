package ua.alexp.redditinterview.screens.top

import ua.alexp.redditinterview.enums.PostType
import ua.alexp.redditinterview.screens.base.BaseViewModel

class TopViewModel : BaseViewModel(){

    fun loadTopPosts(){
        loadPosts(PostType.TOP)
    }

    fun preLoadPosts(){
        preLoadPosts(PostType.TOP)
    }
}
