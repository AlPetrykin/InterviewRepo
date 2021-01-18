package ua.alexp.redditinterview.screens.best

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_best.*
import ua.alexp.redditinterview.R
import ua.alexp.redditinterview.adapters.PostsAdapter
import ua.alexp.redditinterview.helpers.OnPostClickListener
import ua.alexp.redditinterview.screens.image_dialog.ImageDialogFragment

class BestFragment : Fragment(), OnPostClickListener {

    private val bestViewModel : BestViewModel by viewModels()
    private val postsAdapter = PostsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_best, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
    }

    private fun initViewModel() {
        bestViewModel.bestPostsLiveData.observe(viewLifecycleOwner, {
            postsAdapter.addNewItems(it)
        })

        bestViewModel.errorLiveData.observe(viewLifecycleOwner,{
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        bestViewModel.showPBContentLoadingLiveData.observe(viewLifecycleOwner, {
            postsAdapter.addLoading()
        })

        bestViewModel.loadBestPosts()
    }

    private fun initRecyclerView(){
        best_rv_posts?.layoutManager = LinearLayoutManager(requireContext())
        best_rv_posts?.adapter = postsAdapter
        best_rv_posts?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    bestViewModel.loadBestPosts()
                }
            }
        })
    }

    override fun onPostClick(imageUrl: String) {
        val fm = requireActivity().supportFragmentManager
        val imageDialogFragment = ImageDialogFragment(imageUrl)
        imageDialogFragment.show(fm, "image_dialog")
    }
}