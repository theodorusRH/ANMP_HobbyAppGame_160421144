package com.example.hobbyappgame.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hobbyappgame.R
import com.example.hobbyappgame.databinding.FragmentGameDetailBinding
import com.example.hobbyappgame.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso


class GameDetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentGameDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            viewModel.refresh(GameDetailFragmentArgs.fromBundle(requireArguments()).gameId)

            observeViewModel()
        }
    }

    fun observeViewModel() {
        viewModel.gameLD.observe(viewLifecycleOwner, Observer {

            if (it == null) {
            }
            else {
                binding.txtMaker.setText(it.name)
                binding.txtTitle.setText(it.titleName)
//                binding.txtParagraf.setText(it.news)
                val picasso = Picasso.Builder(binding.root.context)
                picasso.listener { picasso, uri, exception -> exception.printStackTrace()}
                picasso.build().load(it.photourl).into(binding.imageGame)

                val newsSize = it.news?.size?:0
                var index = 0

                if(newsSize > 0){
                    binding.txtParagraf.text = it.news?.get(index)
                    binding.btnPrev.isEnabled = false

                    binding.btnNext.setOnClickListener { view->
                        index++
                        binding.txtParagraf.text = it.news?.get(index)
                        binding.btnPrev.isEnabled = true

                        if(index == newsSize-1){
                            binding.btnNext.isEnabled = false
                        }
                    }

                    binding.btnPrev.setOnClickListener { view->
                        index--
                        binding.txtParagraf.text = it.news?.get(index)
                        binding.btnNext.isEnabled = true

                        if(index == 0){
                            binding.btnPrev.isEnabled = false
                        }
                    }
                }
            }
        })
    }
}