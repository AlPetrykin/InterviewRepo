package ua.alexp.redditinterview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.post_item.view.*
import ua.alexp.redditinterview.R
import ua.alexp.redditinterview.helpers.OnPostClickListener
import ua.alexp.redditinterview.models.ChildrenPost
import ua.alexp.redditinterview.models.Post
import java.util.*
import kotlin.collections.ArrayList

class PostsAdapter(private val listener: OnPostClickListener) :
    RecyclerView.Adapter<PostsAdapter.PostsRecyclerViewHolder>() {

    private val postsList = ArrayList<ChildrenPost>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsRecyclerViewHolder {
        return PostsRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.post_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostsRecyclerViewHolder, position: Int) {
        holder.onBind(post = postsList[position])
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    fun addLoading() {
        postsList.add(
            ChildrenPost(
                kind = "Loading",
                data = Post(
                    id = "",
                    author = "",
                    thumbnail = "",
                    num_comments = 0,
                    url = "",
                    created_utc = 0
                )
            )
        )
        notifyDataSetChanged()
    }

    fun addNewItems(list: List<ChildrenPost>) {
        if (postsList.isNotEmpty()) {
            postsList.removeLast()
        }
        postsList.addAll(list)
        notifyDataSetChanged()
    }

    inner class PostsRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val item: View = view

        fun onBind(post: ChildrenPost) {
            if (post.kind != "Loading") {
                item.post_item_pb_loading?.visibility = View.GONE
                post.data.let { data ->
                    item.post_item_tv_author.text = data.author
                    item.post_item_tv_comments_count.text = data.num_comments.toString()
                    item.post_item_tv_time.text = "${calculateTimeDifference(data.created_utc)} hours ago"
                    data.thumbnail.let {
                        if (!it.isNullOrEmpty()) {
                            loadImageIntoView(url = it)
                        }
                    }

                    item.setOnClickListener {
                        val url = data.url
                        if (url.endsWith("jpg") || url.endsWith("jpeg") || url.endsWith("png")
                        ) {
                            listener.onPostClick(url)
                        } else {
                            val thumbnail = data.thumbnail
                            if (!thumbnail.isNullOrEmpty()) {
                                listener.onPostClick(thumbnail)
                            }
                        }
                    }
                }
            }
        }

        private fun calculateTimeDifference(time: Long): Long {
            val now = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            val past = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            past.timeInMillis = time * 1000
            val hoursInMilli = 60 * 60 * 1000
            return (now.timeInMillis - past.timeInMillis) / hoursInMilli
        }

        private fun loadImageIntoView(url: String) {
            Glide.with(item)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(item.post_item_iv_thumbnail)
        }
    }
}