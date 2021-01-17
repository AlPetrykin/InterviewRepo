package ua.alexp.redditinterview.utils

import android.app.Application
import ua.alexp.redditinterview.api.RestApi

class RedditApp : Application() {

    override fun onCreate() {
        super.onCreate()

        RestApi().initRetrofit(this)
    }
}