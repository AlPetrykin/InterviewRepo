package ua.alexp.redditinterview.utils

import android.app.Application
import ua.alexp.redditinterview.api.RestApi
import ua.alexp.redditinterview.repository.RedditRepositoryImpl

class RedditApp : Application() {

    override fun onCreate() {
        super.onCreate()

        RedditRepositoryImpl.initRepository(application = this)
    }
}