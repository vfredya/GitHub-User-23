package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.DetailActivity
import com.example.githubuser.fragment.FollowingFragment
import com.example.githubuser.R
import com.example.githubuser.UserPreviewResponse

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listFollow = ArrayList<UserPreviewResponse>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(items: ArrayList<UserPreviewResponse>) {
        listFollow.clear()
        listFollow.addAll(items)
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listFollow.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listFollow[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgAvatar)
        holder.tvUsername.text = data.login

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, FollowingFragment::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_DATA, data.login)
            holder.itemView.getContext().startActivity(intentDetail)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserPreviewResponse)
    }
}