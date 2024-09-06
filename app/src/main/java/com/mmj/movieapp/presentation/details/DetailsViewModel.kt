package com.mmj.movieapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmj.movieapp.domain.model.MovieDetails
import com.mmj.movieapp.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {
    private val _movieDetails = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val movieDetails: StateFlow<MovieDetailsUiState> get() = _movieDetails


    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase.execute(movieId).catch {
                _movieDetails.value = MovieDetailsUiState.Error
            }.collect {
                _movieDetails.value = MovieDetailsUiState.Success(it)
            }
        }
    }
}

sealed interface MovieDetailsUiState {
    data class Success(val movieDetails: MovieDetails) : MovieDetailsUiState
    object Error : MovieDetailsUiState
    object Loading : MovieDetailsUiState
}