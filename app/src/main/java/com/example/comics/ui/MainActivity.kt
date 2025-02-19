package com.example.comics.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.data.models.ComicsItemModel
import com.example.comics.data.models.asComicsItemModel
import com.example.comics.databinding.ActivityMainBinding
import com.example.comics.ui.adapters.ComicsListAdapter
import com.example.comics.core.util.State.Error
import com.example.comics.core.util.State.Loading
import com.example.comics.core.util.State.Success
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onInit()
    }

    private fun onInit() {
        viewModel.fetchComics()
        onSwipeListListener()
        onObservers()
    }

    private fun onSwipeListListener() {
        binding.swipeRefresh.apply {
            isRefreshing = true
            setOnRefreshListener {
                viewModel.fetchComics()
            }
        }
    }

    private fun onObservers() {
        viewModel.comics.observe(this) { result ->
            when (result) {
                is Loading -> onLoading()
                is Error -> onError()
                is Success -> result.data?.let { onSubmitList(it.asComicsItemModel) }
            }
        }
    }

    private fun onError() {
        binding.apply {
            listItem.visibility = View.GONE
            errorTV.visibility = View.VISIBLE
            swipeRefresh.isRefreshing = false
        }
    }

    private fun onLoading() {
        binding.swipeRefresh.isRefreshing = true
    }

    private fun onSubmitList(comicsItems: List<ComicsItemModel>) {
        binding.apply {
            swipeRefresh.isRefreshing = false
            listItem.visibility = View.VISIBLE
            errorTV.visibility = View.GONE
            listItem.adapter = ComicsListAdapter(comicsItems)
            listItem.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}