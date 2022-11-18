package com.app.githubjavapop.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.githubjavapop.Models.Repository
import com.app.githubjavapop.R
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class RepositoryAdapter(private val context: Context, private val repositories: ArrayList<Repository>) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    var onItemClick : ((Repository) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(repositories[position])
        }

        holder.textViewRepositoryName.text = repositories[position].name
        holder.textViewDescription.text = repositories[position].description
        holder.textViewLastName.text = repositories[position].fullName
        holder.textViewUsername.text = repositories[position].username

        holder.textViewNumStars.text = repositories[position].numStars.toString()
        holder.textViewNumberPullRequests.text = repositories[position].numPullRequests.toString()

        Glide.with(context)
            .load(repositories[position].avatarUrl)
            .into(holder.circleImageView)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val circleImageView: CircleImageView = itemView.findViewById(R.id.circleImageView)
        val textViewUsername: TextView = itemView.findViewById(R.id.textViewUsername)
        val textViewLastName: TextView = itemView.findViewById(R.id.textViewLastName)
        val textViewRepositoryName: TextView = itemView.findViewById(R.id.textViewRepositoryName)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val textViewNumberPullRequests: TextView = itemView.findViewById(R.id.textViewNumberPullRequests)
        val textViewNumStars: TextView = itemView.findViewById(R.id.textViewNumStars)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateReceiptsList(newlist: Collection<Repository>) {
        repositories.clear()
        repositories.addAll(newlist)
        notifyDataSetChanged()

    }
}