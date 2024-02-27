package com.practice.feature.home_impl.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.practice.core.common.navigation.DestinationScreen
import com.practice.core.designsystem.R
import com.practice.core.designsystem.ui.theme.black33Transparent
import com.practice.feature.home_impl.presentation.model.HomePresentationEntity
import com.practice.feature.home_impl.presentation.mvi.HomeEffect
import com.practice.feature.home_impl.presentation.mvi.HomeEvent
import com.practice.feature.home_impl.presentation.mvi.HomeState
import com.practice.feature.home_impl.presentation.mvi.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(null)

    HomeContent(
        state = state.value,
        eventHandler = remember { viewModel::event }
    )

    HomeScreenEffect(
        navController = navController,
        effect = effect
    )

    BackHandler {
        viewModel::event.invoke(HomeEvent.OnBackCLick)
    }
}

@Composable
private fun HomeScreenEffect(
    navController: NavController,
    effect: HomeEffect?
) {

    LaunchedEffect(effect) {
        when (effect) {
            null -> Unit

            is HomeEffect.NavigateToDetail -> {
                navController.navigate(
                    DestinationScreen.DetailScreen.withArgs(effect.id.toString())
                )
            }

            HomeEffect.NavigateBack -> {
                navController.popBackStack(
                    route = DestinationScreen.SignInScreen.route,
                    inclusive = false
                )
            }

            HomeEffect.NavigateToProfile -> {}
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    eventHandler: (HomeEvent) -> Unit
) {

    val films = state.films

    Column(modifier = Modifier.fillMaxSize()) {
        if (state.userPhone != null) {
            Row(
                horizontalArrangement = Arrangement.Absolute.Right,
                modifier = Modifier
//                    .padding(dimen(id = R.id.))
            ) {

            }
        }
        if (films != null) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.step2))
            ) {
                items(films) { film ->
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(percent = 12))
                            .clickable {
                                eventHandler(HomeEvent.OnFilmClick(film.kinopoiskId))
                            }
                            .padding(dimensionResource(id = R.dimen.step4))
                    ) {
                        Box(
                            modifier = Modifier
                                .width(160.dp)
                                .height(240.dp)
                                .clip(RoundedCornerShape(percent = 12))
                        ) {
                            HomePoster(posterUrl = film.posterUrlPreview)
                        }
                        HomeTitle(
                            nameRu = film.nameRu,
                            nameOriginal = film.nameOriginal,
                            year = film.year
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomePoster(
    posterUrl: String,
) {
    SubcomposeAsyncImage(
        model = posterUrl,
        contentDescription = "poster in home",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = black33Transparent)
            )
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}

@Composable
private fun HomeTitle(
    nameRu: String?,
    nameOriginal: String?,
    year: String
) {
    val name = nameRu ?: nameOriginal

    Box {
        Text(
            text = "$name ($year)",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            minLines = 2
        )
    }

}

@Preview
@Composable
private fun HomeContentPreview() {
    MaterialTheme {
        HomePoster(posterUrl = HomePresentationEntity.preview().posterUrlPreview)
    }
}
