package com.app.githubjavapop.Models

import org.json.JSONObject

class Repository {

    var name: String? = null
    var description: String? = null
    var numPullRequests: Int? = null
    var numStars: Int? = null
    var username: String? = null
    var fullName: String? = null
    var avatarUrl: String? = null
    var pullsUrl: String? = null

    constructor()

    constructor(name: String?, description: String?, numPullRequests: Int?, numStars: Int?,
                username: String?, fullName: String?, avatarUrl: String?, pullsUrl: String?) {

        this.name = name
        this.description = description
        this.numPullRequests = numPullRequests
        this.numStars = numStars
        this.username = username
        this.fullName = fullName
        this.avatarUrl = avatarUrl
        this.pullsUrl = pullsUrl
    }

    fun toJson(): JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("name", name)
        jsonObject.put("description", description)
        jsonObject.put("numPullRequests", numPullRequests)
        jsonObject.put("stargazers_count", numStars)
        jsonObject.put("login", username)
        jsonObject.put("fullName", fullName)
        jsonObject.put("avatar_url", avatarUrl)
        jsonObject.put("pulls_url", pullsUrl)
        return jsonObject
    }

    companion object {
        fun fromJson(jsonArticle: JSONObject): Repository {
            val repository = Repository()

            repository.name = jsonArticle.getString("title")
            repository.description = jsonArticle.getString("description")
            repository.numStars = jsonArticle.getInt("stargazers_count")
            repository.username = jsonArticle.getString("login")
            repository.fullName = jsonArticle.getString("fullName")
            repository.avatarUrl = jsonArticle.getString("avatar_url")
            return repository
        }

        fun getRepoInfo(jsonArticle: JSONObject): Repository {
            val repository = Repository()

            repository.name = jsonArticle.getString("name")
            repository.description = jsonArticle.getString("description")
            repository.numStars = jsonArticle.getInt("stargazers_count")
            return repository
        }

        fun getUserInfo(jsonArticle: JSONObject): PullRequest {
            val user = PullRequest()

            user.username = jsonArticle.getString("login")
            user.fullName = jsonArticle.getString("name")
            user.avatarUrl = jsonArticle.getString("avatar_url")
            return user
        }
    }
}
