package com.dicoding.calofruit.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.calofruit.databinding.StoryRowBinding
import com.dicoding.calofruit.utils.loadImage
import com.dicoding.calofruit.response.ListStoryItem
import com.dicoding.calofruit.ui.detail.DetailActivity
import com.dicoding.calofruit.utils.formatDate
import java.util.TimeZone

class StoryAdapter :
    PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StoryRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class ViewHolder(private var binding: StoryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(stories: ListStoryItem) {
            loadImage(binding.root.context, stories.photoUrl, binding.imageView)
            binding.tvName.text = stories.name
            binding.tvDateCreated.text = formatDate (stories.createdAt, TimeZone.getDefault().id)

            binding.root.setOnClickListener {
                val detailIntent = Intent(binding.root.context, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.DETAIL_STORY, stories)
                itemView.context.startActivity(
                    detailIntent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity)
                        .toBundle()
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.binding(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}