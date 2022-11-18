package com.app.githubjavapop.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.githubjavapop.Models.PullRequest
import com.app.githubjavapop.R
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class PullRequestAdapter(private val context: Context, private val pullRequests: ArrayList<PullRequest>) : RecyclerView.Adapter<PullRequestAdapter.ViewHolder>() {

    var onItemClick : ((PullRequest) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pull_request_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(pullRequests[position])
        }

        holder.textViewPullRequestName.text = pullRequests[position].title
        holder.textViewDescription.text = pullRequests[position].description
        holder.textViewUsername.text = pullRequests[position].username
        holder.textViewLastName.text = pullRequests[position].fullName

        Glide.with(context).load(pullRequests[position].avatarUrl).into(holder.circleImageView)
    }

    override fun getItemCount(): Int {
        return pullRequests.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewPullRequestName: TextView = itemView.findViewById(R.id.textViewPullRequestName)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val textViewUsername: TextView = itemView.findViewById(R.id.textViewUsername)
        val textViewLastName: TextView = itemView.findViewById(R.id.textViewLastName)
        val circleImageView: CircleImageView = itemView.findViewById(R.id.circleImageView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateReceiptsList(newlist: Collection<PullRequest>) {
        pullRequests.clear()
        pullRequests.addAll(newlist)
        notifyDataSetChanged()
    }
}