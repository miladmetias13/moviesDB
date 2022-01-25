package com.milad.moviesdb.ui.details

import androidx.lifecycle.*
import com.milad.moviesdb.data.repository.MovieRepository
import com.milad.moviesdb.model.State
import com.milad.moviesdb.model.entity.Movie
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MovieDetailsViewModel @AssistedInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted private val movieId: Int
) : ViewModel() {

    private val _movieLiveData = MutableLiveData<State<Movie>>()

    val movieLiveData: LiveData<State<Movie>> = _movieLiveData




    @AssistedFactory
    interface MovieDetailsViewModelFactory {
        fun create(postId: Int): MovieDetailsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: MovieDetailsViewModelFactory,
            movieId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(movieId) as T
            }
        }
    }

    fun fetchDetails() {
        viewModelScope.launch {
            movieRepository.loadMovieDetails(movieId)
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _movieLiveData.value = state }
        }
    }
}
