package com.app.githubjavapop.Models

import org.json.JSONObject

class PullRequest{

    var title: String? = null
    var description: String? = null
    var username: String? = null
    var fullName: String? = null
    var avatarUrl: String? = null

    constructor()

    constructor(title: String?, description: String?, username: String?, fullName: String?, avatarUrl: String?) {
        this.title = title
        this.description = description
        this.username = username
        this.fullName = fullName
        this.avatarUrl = avatarUrl
    }

    fun toJson(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("title", title)
        jsonObject.put("description", description)
        jsonObject.put("login", username)
        jsonObject.put("name", fullName)
        jsonObject.put("avatar_url", avatarUrl)
        return jsonObject
    }

    companion object {
        fun fromJson(jsonArticle: JSONObject): PullRequest {
            val pullRequest = PullRequest()

            pullRequest.title = jsonArticle.getString("title")
            pullRequest.description = jsonArticle.getString("description")
            pullRequest.username = jsonArticle.getString("login")
            pullRequest.fullName = jsonArticle.getString("name")
            pullRequest.avatarUrl = jsonArticle.getString("avatar_url")
            return pullRequest
        }

        fun getPullRequestInfo(jsonArticle: JSONObject): PullRequest {
            val pullRequest = PullRequest()

            pullRequest.title = jsonArticle.getString("title")
            pullRequest.description = jsonArticle.getString("description")
            return pullRequest
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
