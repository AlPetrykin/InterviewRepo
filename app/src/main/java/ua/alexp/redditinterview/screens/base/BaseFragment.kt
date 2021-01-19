package ua.alexp.redditinterview.screens.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.alexp.redditinterview.adapters.PostsAdapter
import ua.alexp.redditinterview.helpers.OnPostClickListener
import ua.alexp.redditinterview.screens.image_dialog.ImageDialogFragment

abstract class BaseFragment : Fragment(), OnPostClickListener {

    private val postsAdapter = PostsAdapter(this)

    protected fun initViewModel(viewModel: BaseViewModel) {
        viewModel.postsLiveData.observe(viewLifecycleOwner, {
            postsAdapter.addNewItems(it)
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner,{
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.showPBContentLoadingLiveData.observe(viewLifecycleOwner, {
            postsAdapter.addLoading()
        })
    }

    protected fun initRecyclerView(recyclerView: RecyclerView){
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = postsAdapter
    }


    override fun onPostClick(imageUrl: String) {
        val fm = requireActivity().supportFragmentManager
        val imageDialogFragment = ImageDialogFragment(imageUrl)
        imageDialogFragment.show(fm, "image_dialog")
    }
}