package com.app.githubjavapop.Requests

import com.app.githubjavapop.Models.PullRequest
import com.app.githubjavapop.Models.Repository
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

object GeneralRequests {

    val token = "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"

    fun getRepositories(repositories: MutableList<Repository>, page: Int, callBack: (List<Repository>) -> Unit) {

        val url = "https://api.github.com/search/repositories?q=language:Kotlin&sort=stars&page=${page}"

        val request = Request.Builder().addHeader("Authorization", token)

        val mainRequest = request.url(url).get().build()
        val client = OkHttpClient.Builder().build()

        client.newCall(mainRequest).execute().use { response ->

            val json = JSONObject(response.body!!.string())
            val items = json.getJSONArray("items")

            for (index in 0 until items.length()) {
                val jsonArticle = items.get(index) as JSONObject

                val repoName = jsonArticle.getString("name")
                val description = jsonArticle.getString("description")
                val numStars = jsonArticle.getInt("stargazers_count")
                val owner = jsonArticle.getString("owner")

                var fullName : String? = null
                var numPullRequests : Int? = null
                val ownerJson = JSONObject(owner)
                val profileUrl = ownerJson.getString("url")
                val name = ownerJson.getString("login")
                val avatarUrl = ownerJson.getString("avatar_url")
                val pullsUrl = "https://api.github.com/repos/${name}/${repoName}/pulls"

                val urlRequest = request.url(profileUrl).get().build()
                client.newCall(urlRequest).execute().use { profileResponse ->

                    val jsonProfile = JSONObject(profileResponse.body!!.string())

                    try {
                        fullName = jsonProfile.getString("name")
                    }catch (e: Exception){
                        print(urlRequest)
                    }
                }

                val editPullUrl = "$pullsUrl?state=all&sort=created&direction=desc&per_page=1"

                val pullRequest = request.url(editPullUrl).get().build()

                client.newCall(pullRequest).execute().use { pullResponse ->
                    val jsonPull = JSONArray(pullResponse.body!!.string())
                    
                    val pull = jsonPull.get(0) as JSONObject
                    numPullRequests = pull.getInt("number")
                }

                val repository = Repository(repoName, description, numPullRequests, numStars, name, fullName, avatarUrl,
                pullsUrl)

                repositories.add(repository)
            }
            callBack(repositories.toList())
        }
    }

    fun getPullRequests(url: String, callBack: (List<PullRequest>) -> Unit){

        val pullRequests: MutableList<PullRequest> = arrayListOf()
        val request = Request.Builder()

        val mainRequest = request.url(url).get().build()
        val client = OkHttpClient.Builder().build()


        client.newCall(mainRequest).execute().use { response ->
            val json = JSONArray(response.body!!.string())

            for (item in 0 until json.length()){

                val pullRequest = json.get(item) as JSONObject

                val title = pullRequest.getString("title")
                val description = pullRequest.getString("body")

                val user = pullRequest.getString("user")

                val userJson = JSONObject(user)
                val profileUrl = userJson.getString("url")
                val name = userJson.getString("login")
                val avatarUrl = userJson.getString("avatar_url")
                var fullName: String? = null

                val userRequest = request.url(profileUrl).get().build()

                client.newCall(userRequest).execute().use { userResponse ->
                    val jsonProfile = JSONObject(userResponse.body!!.string())
                    try {
                        fullName = jsonProfile.getString("name")
                    }catch (e: Exception){
                        print(profileUrl)
                    }
                }
                val pullRequestData = PullRequest(title, description, name, fullName, avatarUrl)
                pullRequests.add(pullRequestData)
            }
            callBack(pullRequests)
        }
    }
}