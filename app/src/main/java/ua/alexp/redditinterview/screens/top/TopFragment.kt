package ua.alexp.redditinterview.screens.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

        })

        topViewModel.errorLiveData.observe(viewLifecycleOwner, {

        })
    }
}