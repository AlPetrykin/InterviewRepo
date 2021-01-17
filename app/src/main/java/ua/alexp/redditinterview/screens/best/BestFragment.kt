package ua.alexp.redditinterview.screens.best

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ua.alexp.redditinterview.R

class BestFragment : Fragment() {

    private val bestViewModel : BestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_best, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        bestViewModel.bestPostsLiveData.observe(viewLifecycleOwner, {

        })

        bestViewModel.errorLiveData.observe(viewLifecycleOwner,{

        })

//        bestViewModel.loadBestPosts()
    }
}