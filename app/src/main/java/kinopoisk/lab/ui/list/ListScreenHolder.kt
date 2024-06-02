package kinopoisk.lab.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import kinopoisk.lab.App
import kinopoisk.lab.di.injectedViewModel
import kinopoisk.lab.domain.model.FilmType
import kinopoisk.lab.ui.details.DetailsScreen
import kinopoisk.lab.ui.details.DetailsState
import kinopoisk.lab.ui.list.components.BottomBar
import kinopoisk.lab.ui.list.components.ErrorScreen
import kinopoisk.lab.ui.list.components.FilmsList
import kinopoisk.lab.ui.list.components.NoContentScreen
import kinopoisk.lab.ui.list.components.Preloader
import kinopoisk.lab.ui.list.components.TopBar


@Composable
fun ListScreenHolder(navController: NavController) {
    val context = LocalContext.current.applicationContext
    val viewModel = injectedViewModel {
        (context as App).appComponent.listViewModelFactory.create()
    }
    val state by viewModel.state.collectAsState()

    val config = LocalConfiguration.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(config) {
        viewModel.obtainEvent(
            ListEvent.OnOrientationChange(config.orientation)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.actions
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect {
                when (it) {
                    is ListUiAction.Navigate -> {
                        navController.navigate(it.route)
                    }
                }
            }
    }

    ListScreen(state, viewModel::obtainEvent)
}

@Composable
private fun ListScreen(
    state: ListState,
    onEvent: (ListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { TopBar(state, onEvent) },
        bottomBar = { BottomBar(state.filmType, onEvent) },
        modifier = modifier
            .systemBarsPadding()
            .background(Color.White)
    ) { padding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            when (state) {
                is ListState.Data -> {
                    if (state.filmDetails != null) {
                        Row(Modifier.fillMaxSize()) {
                            FilmsList(
                                data = state.visibleData,
                                onEvent = onEvent,
                                modifier = Modifier.weight(1f)
                            )

                            DetailsScreen(
                                state = DetailsState.Data(filmDetails = state.filmDetails),
                                onNavigateBack = {},
                                showNavBack = false,
                                modifier = Modifier.weight(1f)
                            )
                        }

                    } else {
                        FilmsList(
                            data = state.visibleData,
                            onEvent = onEvent
                        )
                    }
                }

                is ListState.Loading -> Preloader()

                is ListState.NoContent -> NoContentScreen()
                ListState.Error -> ErrorScreen(onEvent)
            }
        }
    }
}

@Composable
@Preview
private fun ListScreenHolderPreview() {
    val data = listOf(
        FilmListUiModel(
            filmId = 1,
            filmTitle = "Test film",
            filmYear = 2000,
            filmGenre = "test",
            filmPosterUrl = "",
            isFavorite = true
        ),
        FilmListUiModel(
            filmId = 2,
            filmTitle = "Test film",
            filmYear = 2000,
            filmGenre = "test",
            filmPosterUrl = "",
            isFavorite = false
        ),
        FilmListUiModel(
            filmId = 3,
            filmTitle = "Test film",
            filmYear = 2000,
            filmGenre = "test",
            filmPosterUrl = "",
            isFavorite = true
        )
    )

    ListScreen(ListState.Data(data = data, visibleData = data, filmType = FilmType.POPULAR), {})
}