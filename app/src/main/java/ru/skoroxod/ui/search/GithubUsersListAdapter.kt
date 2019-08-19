package ru.skoroxod.ui.search

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.skoroxod.domain.github.GithubUser

class GithubUsersListAdapter()
    :PagedListAdapter<GithubUser, GithubUserViewHolder>(UserDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
            return GithubUserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        val UserDiffCallback = object: DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }

        }
    }
}
