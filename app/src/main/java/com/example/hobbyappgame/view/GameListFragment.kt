package com.example.hobbyappgame.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hobbyappgame.R
import com.example.hobbyappgame.databinding.FragmentGameListBinding
import com.example.hobbyappgame.viewmodel.GameListViewModel


class GameListFragment : Fragment() {

    private lateinit var viewModel:GameListViewModel
    private lateinit var binding: FragmentGameListBinding
    private val gameListAdapter = GameListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(GameListViewModel::class.java)
        viewModel.refresh()

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = gameListAdapter

        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout)

        swipe.setOnRefreshListener {
            viewModel.refresh()
            binding.recView.visibility = View.GONE
//            binding.txtError.visibility = View.GONE
//            binding.progressLoad.visibility = View.VISIBLE
            swipe.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.gamesLD.observe(viewLifecycleOwner, Observer {
            gameListAdapter.updateGameList(it)
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.recView.visibility = View.GONE
//                binding.progressLoad.visibility = View.VISIBLE
            }
            else {
                binding.recView.visibility = View.VISIBLE
//                binding.progressLoad.visibility = View.GONE
            }
        })

//        viewModel.gameLoadErrorLD.observe(viewLifecycleOwner, Observer {
//            if (it == true) {
//                binding.txtError?.visibility = View.VISIBLE
//            }
//            else {
//                binding.txtError?.visibility = View.GONE
//            }
//        })
    }

}