package ua.alexp.redditinterview.screens.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_best.*
import ua.alexp.redditinterview.adapters.PostsAdapter
import ua.alexp.redditinterview.enums.PostType
import ua.alexp.redditinterview.helpers.OnPostClickListener
import ua.alexp.redditinterview.screens.image_dialog.ImageDialogFragment
import ua.alexp.redditinterview.screens.posts.PostViewModel

abstract class BasePostFragment : Fragment() {

    private val postsAdapter = PostsAdapter(object : OnPostClickListener {
        override fun onPostClick(imageUrl: String) {
            val fm = requireActivity().supportFragmentManager
            val imageDialogFragment = ImageDialogFragment(imageUrl)
            imageDialogFragment.show(fm, "image_dialog")
        }
    })

    protected fun initItems(viewModel: PostViewModel, recyclerView: RecyclerView) {
        initRecyclerView(recyclerView, viewModel)
        initViewModel(viewModel)
    }

    private fun initViewModel(viewModel: PostViewModel) {
        viewModel.postsLiveData.observe(viewLifecycleOwner, {
            postsAdapter.addNewItems(it)
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.showLoadingLiveData.observe(viewLifecycleOwner, {
            postsAdapter.addLoading()
        })

        viewModel.preLoadPosts(PostType.BEST)
    }

    private fun initRecyclerView(recyclerView: RecyclerView, viewModel: PostViewModel) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = postsAdapter
        best_rv_posts?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadPosts(PostType.BEST)
                }
            }
        })
    }
}