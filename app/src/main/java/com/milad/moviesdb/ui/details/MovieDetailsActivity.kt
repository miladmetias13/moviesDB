package com.milad.moviesdb.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.app.ShareCompat
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import com.milad.moviesdb.R
import com.milad.moviesdb.databinding.ActivityMovieDetailsBinding
import com.milad.moviesdb.model.State
import com.milad.moviesdb.ui.base.BaseActivity
import com.milad.moviesdb.utils.showToast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<MovieDetailsViewModel, ActivityMovieDetailsBinding>() {


    @Inject
    lateinit var viewModelFactory: MovieDetailsViewModel.MovieDetailsViewModelFactory

    override val mViewModel: MovieDetailsViewModel by viewModels {
        val movieId = intent.extras?.getInt(KEY_MOVIE_ID)
            ?: throw IllegalArgumentException("`Details` error")
        MovieDetailsViewModel.provideFactory(viewModelFactory, movieId)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        initView()
    }

    override fun onStart() {
        super.onStart()
        observeDetails()
    }

    fun initView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.title = mViewModel.symbol
        fetchDetails()
    }

    private fun fetchDetails() = mViewModel.fetchDetails()


    override fun getViewBinding(): ActivityMovieDetailsBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)




    private fun observeDetails() {
        mViewModel.movieLiveData.observe(this) { state ->
            when (state) {
                is State.Success -> {
                    if (state.data != null) {
                        // load UI
                        supportActionBar!!.title = state.data.title
                        mViewBinding.movieTitle.text = state.data.title
                        mViewBinding.overview.text = state.data.overview
                        mViewBinding.movieImage.load(state.data.getPoster()) {
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                    }
                }
                is State.Error -> {
                    showToast(state.message)
                }
            }
        }
    }

    companion object {
        private const val KEY_MOVIE_ID = "movieId"

        fun getStartIntent(
            context: Context,
            symbol: Int
        ) = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra(
                KEY_MOVIE_ID,
                symbol
            )
        }
    }
}