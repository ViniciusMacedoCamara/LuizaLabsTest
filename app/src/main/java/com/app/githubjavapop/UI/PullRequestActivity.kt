package com.app.githubjavapop.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubjavapop.Adapters.PullRequestAdapter
import com.app.githubjavapop.Adapters.RepositoryAdapter
import com.app.githubjavapop.Models.PullRequest
import com.app.githubjavapop.Models.Repository
import com.app.githubjavapop.R
import com.app.githubjavapop.Requests.GeneralRequests
import com.app.githubjavapop.databinding.ActivityMainBinding
import com.app.githubjavapop.databinding.ActivityPullRequestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PullRequestActivity : AppCompatActivity() {

    lateinit var binding: ActivityPullRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPullRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pullUrl = intent.getStringExtra("pullRequest")

        val recyclerViewPullRequest = binding.recyclerPullRequest

        recyclerViewPullRequest.layoutManager = LinearLayoutManager(this)

        recyclerViewPullRequest.addItemDecoration(
            DividerItemDecoration(
                recyclerViewPullRequest.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val pullRequests = ArrayList<PullRequest>()

        val adapter = PullRequestAdapter(applicationContext, pullRequests)

        recyclerViewPullRequest.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            GeneralRequests.getPullRequests(pullUrl!!) {
                runOnUiThread {
                    adapter.updateReceiptsList(it)
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}