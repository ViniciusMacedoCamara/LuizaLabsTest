package com.app.githubjavapop.Requests

import okhttp3.OkHttpClient
import okhttp3.Request

class TestRequests {

    fun checkRepoRequest() : Int{
        val url = "https://api.github.com/search/repositories?q=language:Kotlin&sort=stars&page=1&per_page=1"

        val request = Request.Builder()

        val mainRequest = request.url(url).get().build()
        val client = OkHttpClient.Builder().build()

        var code : Int? = null

        client.newCall(mainRequest).execute().use { response -> code = response.code }

        return code!!
    }

    fun checkPullRequest() : Int{
        val url = "https://api.github.com/repos/square/okhttp/pulls/1"

        val request = Request.Builder()

        val mainRequest = request.url(url).get().build()
        val client = OkHttpClient.Builder().build()

        var code : Int? = null

        client.newCall(mainRequest).execute().use { response -> code = response.code }

        return code!!
    }

    fun checkProfileRequest() : Int{
        val url = "https://api.github.com/users/square"

        val request = Request.Builder()

        val mainRequest = request.url(url).get().build()
        val client = OkHttpClient.Builder().build()

        var code : Int? = null

        client.newCall(mainRequest).execute().use { response -> code = response.code }

        return code!!
    }
}