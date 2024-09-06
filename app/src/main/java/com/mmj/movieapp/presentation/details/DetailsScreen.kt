package com.mmj.movieapp.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mmj.movieapp.R
import com.mmj.movieapp.core.app.AppPreferences
import com.mmj.movieapp.core.app.Constants
import com.mmj.movieapp.domain.model.MovieDetails
import com.mmj.movieapp.domain.model.genresAsString
import com.mmj.movieapp.domain.model.spokenLanguageAsString
import com.mmj.movieapp.presentation.main.MainEvent
import com.mmj.movieapp.presentation.main.MainViewModel
import com.mmj.movieapp.presentation.util.ErrorMessage
import com.mmj.movieapp.presentation.util.resource.route.AppScreen
import com.mmj.movieapp.presentation.util.resource.theme.AppTheme

@Composable
fun DetailsScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel(),
    movieId: Int?,
) {


    movieId?.let {
        viewModel.getMovieDetails(it)
    }

    val movieDetailsState by viewModel.movieDetails.collectAsState()


    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                if (mainViewModel.stateApp.theme == AppTheme.Light) {
                    AppPreferences.setTheme(AppTheme.Dark)
                    mainViewModel.onEvent(MainEvent.ThemeChange(AppTheme.Dark))
                } else {
                    AppPreferences.setTheme(AppTheme.Light)
                    mainViewModel.onEvent(MainEvent.ThemeChange(AppTheme.Light))
                }
            }) {
                Icon(
                    painter = if (mainViewModel.stateApp.theme == AppTheme.Light) painterResource(
                        id = R.drawable.ic_dark_mode
                    )
                    else painterResource(id = R.drawable.ic_light_mode),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(25.dp)
                )
            }




            Text(
                text = stringResource(id = R.string.movieDetails),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .weight(1.0f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                navController.popBackStack(
                    AppScreen.DetailsScreen.route, inclusive = true, saveState = true
                )
            }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(25.dp)
                )
            }

        }
    }) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            item { Spacer(modifier = Modifier.padding(0.dp)) }
            item {
                MaterialTheme {

                    when (movieDetailsState) {
                        MovieDetailsUiState.Error -> {
                            ErrorMessage(modifier = Modifier.fillMaxSize(),
                                message = stringResource(id = R.string.movieDetailsError),
                                onClickRetry = {
                                    movieId?.let { _movieId ->
                                        viewModel.getMovieDetails(_movieId)
                                    }
                                })
                        }


                        MovieDetailsUiState.Loading -> Loading()
                        is MovieDetailsUiState.Success -> MovieDetailsScreen(
                            movieDetails = (movieDetailsState as MovieDetailsUiState.Success).movieDetails
                        )
                    }
                }
            }
        }
    }

}


@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier.then(
                Modifier.size(32.dp)
            )
        )
    }
}


@Composable
private fun MovieDetailsScreen(
    modifier: Modifier = Modifier, movieDetails: MovieDetails?
) {

    Surface(
        modifier = modifier
    ) {
        Column {

            AsyncImage(
                modifier = modifier
                    .height(300.dp)
                    .padding(8.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                model = Constants.IMAGE_URL_MOVIE + movieDetails?.posterPath,
                contentDescription = movieDetails?.originalTitle
            )


            Text(
                modifier = modifier.padding(8.dp),
                style = MaterialTheme.typography.headlineLarge,
                text = movieDetails?.originalTitle.toString(),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                modifier = modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium,
                text = movieDetails?.overview.toString(),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                modifier = modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium,
                text = movieDetails?.genres?.genresAsString().toString(),
                color = MaterialTheme.colorScheme.onSurface,
                fontStyle = FontStyle.Italic
            )

            Text(
                modifier = modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium,
                text = movieDetails?.spokenLanguage?.spokenLanguageAsString().toString(),
                color = MaterialTheme.colorScheme.onSurface,
                fontStyle = FontStyle.Italic
            )

            Text(
                modifier = modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium,
                text = movieDetails?.releaseDate.toString(),
                color = MaterialTheme.colorScheme.onSurface,
            )


            if (movieDetails?.adult == true) Icon(
                painter = painterResource(
                    id = R.drawable.ic_adult
                ),
                contentDescription = "adult",
                tint = Color.Red,
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
        }
    }

}