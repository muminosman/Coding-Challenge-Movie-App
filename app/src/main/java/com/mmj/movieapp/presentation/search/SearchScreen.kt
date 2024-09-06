package com.mmj.movieapp.presentation.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mmj.movieapp.R
import com.mmj.movieapp.core.app.AppPreferences
import com.mmj.movieapp.domain.model.Movie
import com.mmj.movieapp.presentation.home.component.ItemMovie
import com.mmj.movieapp.presentation.main.MainEvent
import com.mmj.movieapp.presentation.main.MainViewModel
import com.mmj.movieapp.presentation.util.ErrorMessage
import com.mmj.movieapp.presentation.util.LoadingNextPageItem
import com.mmj.movieapp.presentation.util.PageLoader
import com.mmj.movieapp.presentation.util.resource.route.AppScreen
import com.mmj.movieapp.presentation.util.resource.theme.AppTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun SearchScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),

    ) {
    val moviePagingItems: LazyPagingItems<Movie> = viewModel.moviesState.collectAsLazyPagingItems()
    Scaffold(topBar = {
        var searchQuery by rememberSaveable { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()
        ) {
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
                    text = stringResource(id = R.string.search),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)

                        .weight(1.0f)
                        .align(alignment = Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = {
                    navController.popBackStack(
                        AppScreen.SearchScreen.route, inclusive = true, saveState = true
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

            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(40.dp)),
                query = searchQuery,
                onQueryChange = { queryChanged ->
                    searchQuery = queryChanged // update the query state
                    viewModel.query = (queryChanged) // call the callback
                    GlobalScope.launch {
                        viewModel.getSearchMovies()
                    }
                },
                onSearch = { query ->
                    // Handle search ImeAction.Search here
                },
                active = true,
                onActiveChange = { isActive ->
                },
                placeholder = { Text(stringResource(id = R.string.searchAMovie)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            ) {
                // Show suggestions here
                // for example a LazyColumn with suggestion items

            }

            if (moviePagingItems.itemCount == 0) {
                Text(
                    text = stringResource(id = R.string.searchNoResultsFound),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center

                )
            }
        }

    }) {


        LazyColumn(
            modifier = Modifier.padding(it)
        ) {


            item { Spacer(modifier = Modifier.padding(4.dp)) }
            items(moviePagingItems.itemCount) { index ->
                ItemMovie(itemEntity = moviePagingItems[index]!!, onClick = {
                    mainViewModel.selectedMovieId = moviePagingItems[index]!!.id
                    navController.navigate(AppScreen.DetailsScreen.route)
                })
            }
            moviePagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = moviePagingItems.loadState.refresh as LoadState.Error
                        Log.e("loadState.refresh", error.error.localizedMessage!!)
                        item {
                            ErrorMessage(modifier = Modifier.fillParentMaxSize(),
                                message = stringResource(id = R.string.movieDetailsError),
                                onClickRetry = { retry() })
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = moviePagingItems.loadState.append as LoadState.Error
                        Log.e("loadState.append", error.error.localizedMessage!!)
                        item {
                            ErrorMessage(modifier = Modifier,
                                message = stringResource(id = R.string.movieDetailsError),
                                onClickRetry = { retry() })
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.padding(4.dp)) }
        }


    }
}

