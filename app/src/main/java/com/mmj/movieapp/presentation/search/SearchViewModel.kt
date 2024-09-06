package com.mmj.movieapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mmj.movieapp.domain.model.Movie
import com.mmj.movieapp.domain.usecase.GetSearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase
) : ViewModel() {
    private val _moviesState: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(value = PagingData.empty())
    val moviesState: MutableStateFlow<PagingData<Movie>> get() = _moviesState
    var query = ""

    init {
        viewModelScope.launch {
            getSearchMovies()
        }

    }


    suspend fun getSearchMovies() {
        getSearchMoviesUseCase.execute(query).distinctUntilChanged().cachedIn(viewModelScope)
            .collect {
                _moviesState.value = it
            }
    }

}

