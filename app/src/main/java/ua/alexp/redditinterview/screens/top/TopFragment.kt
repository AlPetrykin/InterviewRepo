package ua.alexp.redditinterview.screens.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_top.*
import ua.alexp.redditinterview.R
import ua.alexp.redditinterview.adapters.PostsAdapter
import ua.alexp.redditinterview.helpers.OnPostClickListener
import ua.alexp.redditinterview.screens.image_dialog.ImageDialogFragment

class TopFragment : Fragment(), OnPostClickListener {

    private val topViewModel : TopViewModel by viewModels()
    private val postsAdapter = PostsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        top_rv_posts?.layoutManager = LinearLayoutManager(requireContext())
        top_rv_posts?.adapter = postsAdapter
        top_rv_posts?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!top_rv_posts.canScrollVertically(1)){
                    topViewModel.loadTopPosts()
                }
            }
        })
    }

    private fun initViewModel() {
        topViewModel.topPostsLiveData.observe(viewLifecycleOwner, {
            postsAdapter.addNewItems(it)
        })

        topViewModel.errorLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        topViewModel.showPBContentLoadingLiveData.observe(viewLifecycleOwner, {
            postsAdapter.addLoading()
        })

        topViewModel.loadTopPosts()
    }

    override fun onPostClick(imageUrl: String) {
        val fm = requireActivity().supportFragmentManager
        val imageDialogFragment = ImageDialogFragment(imageUrl)
        imageDialogFragment.show(fm, "image_dialog")
    }
}