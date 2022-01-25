package com.milad.moviesdb.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milad.moviesdb.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.milad.moviesdb.model.State
import com.milad.moviesdb.model.entity.Summary
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var _currentPageLiveData = MutableLiveData<State<List<Summary>>>()
    var currentPageLiveData: LiveData<State<List<Summary>>> = _currentPageLiveData

    var pageNumber: MutableLiveData<Int> = MutableLiveData(1)
    var loadedPages: HashMap<String,List<Summary>> = HashMap()
    var loadedGenres: HashMap<Int,String> = HashMap()

    fun fetchGenres() {
        viewModelScope.launch {
            movieRepository.loadGenres()
                .collect { genres ->
                    genres.forEach {
                        loadedGenres[it.id!!] = it.name!!
                    }
                    fetchPage()
                }
        }
    }

    fun fetchPage() {
        viewModelScope.launch {
            movieRepository.loadMovies(pageNumber.value!!)
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _currentPageLiveData.value = state }
        }
    }

    fun filterMovies(list: List<Summary>)
    {
        list.forEach { summary ->
            summary.genre_ids.forEach { genreId ->
                if(loadedPages.containsKey(loadedGenres[genreId]))
                    (loadedPages[loadedGenres[genreId]]!! as ArrayList<Summary>).add(summary)
                else {
                    loadedPages[loadedGenres[genreId]!!] = arrayListOf(summary)
                }
            }
        }
        pageNumber.value = pageNumber.value!! +1
    }

}
