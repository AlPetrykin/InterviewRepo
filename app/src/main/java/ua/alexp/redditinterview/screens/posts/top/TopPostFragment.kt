package ua.alexp.redditinterview.screens.posts.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_top.*
import ua.alexp.redditinterview.R
import ua.alexp.redditinterview.enums.PostType
import ua.alexp.redditinterview.screens.base.BasePostFragment
import ua.alexp.redditinterview.screens.posts.PostViewModel

class TopPostFragment : BasePostFragment() {

    private val topViewModel : PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItems(topViewModel, top_rv_posts)
    }
}