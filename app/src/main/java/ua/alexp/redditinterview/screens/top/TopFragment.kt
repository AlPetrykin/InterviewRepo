package ua.alexp.redditinterview.screens.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_top.*
import ua.alexp.redditinterview.R

class TopFragment : Fragment() {

    private val topViewModel : TopViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        topViewModel.topPostsLiveData.observe(viewLifecycleOwner, {
            top_pb_loading?.visibility = View.GONE
        })

        topViewModel.errorLiveData.observe(viewLifecycleOwner, {

        })

        topViewModel.loadTopPosts()
    }
}