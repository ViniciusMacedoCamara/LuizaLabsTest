package com.app.githubjavapop.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.githubjavapop.Adapters.RepositoryAdapter
import com.app.githubjavapop.Models.Repository
import com.app.githubjavapop.Requests.GeneralRequests
import com.app.githubjavapop.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var repositories = ArrayList<Repository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var adapter : RepositoryAdapter? = null
        var page = 1

        val recyclerViewRepository = binding.recyclerViewRepository

        recyclerViewRepository.layoutManager = LinearLayoutManager(this)

        recyclerViewRepository.addItemDecoration(
            DividerItemDecoration(
                recyclerViewRepository.context,
                DividerItemDecoration.VERTICAL
            )
        )

        adapter = RepositoryAdapter(applicationContext, repositories)

        recyclerViewRepository.adapter = adapter

        loadRepositories(page, adapter)

        adapter.onItemClick = {
            val intent = Intent(applicationContext, PullRequestActivity::class.java)
            intent.putExtra("pullRequest", it.pullsUrl)
            startActivity(intent)
        }

        recyclerViewRepository.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && binding.progressBar.visibility == View.GONE) {
                    page += 1
                    binding.progressBar.visibility = View.VISIBLE
                    loadRepositories(page, adapter)
                }
            }
        })
    }

    fun loadRepositories(page: Int, adapter: RepositoryAdapter){

        GlobalScope.launch(Dispatchers.IO) {
            GeneralRequests.getRepositories(repositories.toMutableList(), page) {
                runOnUiThread {
                    repositories = it as ArrayList<Repository>
                    adapter.updateReceiptsList(it)
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}
