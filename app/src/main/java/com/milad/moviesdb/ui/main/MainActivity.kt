package com.milad.moviesdb.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyaspatil.MaterialDialog.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import com.milad.moviesdb.R
import com.milad.moviesdb.databinding.ActivityMainBinding
import com.milad.moviesdb.model.State
import com.milad.moviesdb.model.entity.Summary
import com.milad.moviesdb.ui.base.BaseActivity
import com.milad.moviesdb.ui.details.MovieDetailsActivity
import com.milad.moviesdb.ui.main.adapter.GenreListAdapter
import com.milad.moviesdb.ui.main.adapter.MovieListAdapter
import com.milad.moviesdb.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.recyclerview.widget.RecyclerView




@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val mViewModel: MainViewModel by viewModels()

    private val mAdapter = GenreListAdapter(this::onItemClicked)

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme) // Set AppTheme before setting content view.

        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        initView()
    }

    override fun onStart() {
        super.onStart()
        observeMovies()
    }

    fun initView() {
        mViewBinding.run {
            moviesRecyclerView.adapter = mAdapter
            moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                       var visibleItemCount = recyclerView.getLayoutManager()!!.getChildCount()
                       var totalItemCount = recyclerView.getLayoutManager()!!.getItemCount()
                        var pastVisiblesItems =
                            (recyclerView.getLayoutManager() as LinearLayoutManager).findFirstVisibleItemPosition()

                        if (!isLoading && visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            // mocking network delay for API call
                            isLoading = true
                            getMovies()
                        }
                    }
                }
            })
        }

        getGenres()


    }

    private fun observeMovies() {
        mViewModel.currentPageLiveData.observe(this) { state ->
            isLoading = false
            when (state) {
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        mViewModel.filterMovies(state.data)
                    }
                }
                is State.Error -> {
                    showToast(state.message)
                }
                else -> {}
            }
        }

        mViewModel.pageNumber.observe(this) {
            mAdapter.submitList(mViewModel.loadedPages.entries.toList())
        }
    }

    private fun getMovies() = mViewModel.fetchPage()
    private fun getGenres() = mViewModel.fetchGenres()


    override fun onBackPressed() {
        MaterialDialog.Builder(this)
            .setTitle(getString(R.string.exit_dialog_title))
            .setMessage(getString(R.string.exit_dialog_message))
            .setPositiveButton(getString(R.string.option_yes)) { dialogInterface, _ ->
                dialogInterface.dismiss()
                super.onBackPressed()
            }
            .setNegativeButton(getString(R.string.option_no)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .build()
            .show()
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    private fun onItemClicked(movie: Summary, view: View) {
        val movieId = movie.id ?: run {
            showToast("Unable to launch details")
            return
        }
        val intent = MovieDetailsActivity.getStartIntent(this, movieId)
        startActivity(intent)
    }
}
