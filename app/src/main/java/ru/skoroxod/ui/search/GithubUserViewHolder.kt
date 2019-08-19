package ru.skoroxod.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.userlist_item.view.*
import ru.skoroxod.R
import ru.skoroxod.domain.github.GithubUser
import ru.skoroxod.ui.utils.CircleTransform

class GithubUserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(user: GithubUser?) {
        itemView.title.text = user?.login

        Picasso.get()
            .load(user?.avatar_url)
            .resize(150, 150)
            .centerCrop()
            .transform(CircleTransform())
            .placeholder(R.drawable.ic_tag_faces_24dp)
            .error(R.drawable.ic_tag_faces_24dp)
            .into(itemView.avatar)
    }

    companion object {
        fun create(parent: ViewGroup): GithubUserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.userlist_item, parent, false)
            return GithubUserViewHolder(view)
        }
    }

}