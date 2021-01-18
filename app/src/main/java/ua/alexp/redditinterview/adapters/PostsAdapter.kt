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

    fun addNewItems(list: List<ChildrenPost>) {
        postsList.addAll(list)
        notifyDataSetChanged()
    }

    inner class PostsRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val item: View = view

        fun onBind(post: ChildrenPost) {
            post.data.let { data ->
                item.post_item_tv_author.text = data.author
                item.post_item_tv_comments_count.text = data.num_comments.toString()

                data.thumbnail.let {
                    if (!it.isNullOrEmpty()) {
                        loadImageIntoView(url = it)
                    }
                }

                item.setOnClickListener {
                    val url = data.url
                    if (url.endsWith("jpg") || url.endsWith("jpeg") || url.endsWith("png")){
                        listener.onPostClick(data.url)
                    }else{
                        if (!data.thumbnail.isNullOrEmpty()) {
                            listener.onPostClick(data.thumbnail)
                        }
                    }
                }
            }
        }

        private fun loadImageIntoView(url: String) {
            Glide.with(item)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(item.post_item_iv_thumbnail)
        }
    }
}