package com.practice.feature.detail.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.practice.core.designsystem.R
import com.practice.core.designsystem.ui.theme.MovieTheme
import com.practice.core.designsystem.ui.theme.black33Transparent
import com.practice.core.designsystem.ui.theme.imdbBrandColor
import com.practice.core.designsystem.ui.theme.kinopoiskBrandColor
import com.practice.core.designsystem.ui.theme.toolbarBackgroundButton
import com.practice.feature.detail.presentation.mvi.DetailEvent
import com.practice.feature.detail.presentation.mvi.DetailState
import com.practice.feature.detail.presentation.mvi.DetailViewModel
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

private const val NO_TOOLBAR_ANIMATION_TIMING = 500
private const val TOOLBAR_ANIMATION_TIMING = 200

@Composable
fun DetailScreen(
    filmId: Int,
    navController: NavController,
    viewModel: DetailViewModel = koinViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(null)

    DetailContent(
        filmId = filmId,
        state = state.value,
        eventHandler = remember { viewModel::event }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedBoxWithConstraintsScope")
@Composable
private fun DetailContent(
    filmId: Int,
    state: DetailState,
    eventHandler: (DetailEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        eventHandler(DetailEvent.OnFilmIdDelivered(filmId))
    }

    val film = state.film

    val scroll = rememberScrollState()

    val posterHeight = dimensionResource(id = R.dimen.detail_poster)
    val toolbarHeight = dimensionResource(id = R.dimen.toolbar_height)

    val headerHeightPx = with(LocalDensity.current) { posterHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    if (film != null) {
        Scaffold(topBar = {
            CollapsingToolbar(
                scroll = scroll,
                headerHeightPx = headerHeightPx,
                toolbarHeightPx = toolbarHeightPx,
                title = film.nameRu ?: ""
            )
        }) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                DetailPoster(
                    scroll = scroll, height = headerHeightPx, posterUrl = film.posterUrl ?: ""
                )

                Column(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.step4))
                        .verticalScroll(scroll)
                ) {

                    Spacer(
                        modifier = Modifier
                            .height(posterHeight - dimensionResource(id = R.dimen.step9))
                            .background(Color.Transparent)
                    )

                    if (film.ratingImdb != null && film.ratingKinopoisk != null) {
                        DetailRating(
                            ratingImdb = film.ratingImdb, ratingKinopoisk = film.ratingKinopoisk
                        )
                        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.step1)))
                    }

                    DetailTitle(
                        name = film.nameRu ?: "",
                        originalName = film.nameOriginal ?: "",
                        year = film.year
                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.step1)))


                    DetailGenres(genres = film.genres)
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.step1)))

                    if (film.slogan != null) {
                        DetailSlogan(slogan = film.slogan)
                        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.step1)))
                    }

                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.step1)))

                    DetailDescription(description = film.description)

                    Divider(
                        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step2))
                    )

                    DetailCountry(countries = film.countries)

                    Divider(
                        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step2))
                    )

                    DetailLength(filmLength = film.filmLength)

                    Divider(
                        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step2))
                    )

                    DetailAge(age = film.ratingAgeLimits)

//                    BoxWithConstraints(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(top = dimensionResource(id = R.dimen.step4))
//                    ) {
//                        Button(
//                            onClick = {},
//                            modifier = Modifier.align(Alignment.BottomCenter),
//                        ) {
//                            Text(
//                                text = stringResource(R.string.detail_button_watch),
//                                style = MaterialTheme.typography.labelMedium
//                            )
//                        }
//                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbar(
    title: String,
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float,

) {
    val toolbarBottom by remember {
        mutableFloatStateOf(headerHeightPx - toolbarHeightPx)
    }

    val showToolbar by remember {
        derivedStateOf {
            scroll.value >= toolbarBottom
        }
    }

    AnimatedVisibility(
        visible = showToolbar, enter = slideInVertically(
            animationSpec = tween(TOOLBAR_ANIMATION_TIMING, easing = LinearEasing)
        ), exit = slideOutVertically(
            animationSpec = tween(TOOLBAR_ANIMATION_TIMING, easing = LinearEasing)
        )
    ) {
        TopAppBar(title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
        }, navigationIcon = {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_back),
//                contentDescription = "back icon in details",
//                modifier = Modifier
//                    .padding(dimensionResource(id = R.dimen.step1))
//                    .size(dimensionResource(id = R.dimen.step7))
//            )
        }, actions = {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_favorite),
//                contentDescription = "favorite icon in details",
//                modifier = Modifier
//                    .padding(dimensionResource(id = R.dimen.step1))
//                    .size(dimensionResource(id = R.dimen.step7))
//            )
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
        )
    }
    AnimatedVisibility(
        visible = !showToolbar,
        enter = fadeIn(animationSpec = tween(NO_TOOLBAR_ANIMATION_TIMING)),
        exit = fadeOut(animationSpec = tween(NO_TOOLBAR_ANIMATION_TIMING))
    ) {
        TopAppBar(title = {}, navigationIcon = {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_back),
//                contentDescription = "back icon in details",
//                tint = Color.White,
//                modifier = Modifier
//                    .clip(CircleShape)
//
//                    .background(toolbarBackgroundButton)
//                    .padding(dimensionResource(id = R.dimen.step1))
//                    .size(dimensionResource(id = R.dimen.step7))
//            )
        }, actions = {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_favorite),
//                contentDescription = "favorite icon in details",
//                tint = Color.White,
//                modifier = Modifier
//                    .clip(CircleShape)
//                    .background(toolbarBackgroundButton)
//                    .padding(dimensionResource(id = R.dimen.step1))
//                    .size(dimensionResource(id = R.dimen.step7))
//            )
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Transparent)
        )
    }
}


@Composable
fun DetailPoster(
    scroll: ScrollState,
    height: Float,
    posterUrl: String
) {
    Box(modifier = Modifier
        .graphicsLayer {
            translationY = -scroll.value.toFloat() / 1.5f
            alpha = (-1f / height) * scroll.value + 1
        }
        .height(dimensionResource(id = R.dimen.detail_poster))) {
        SubcomposeAsyncImage(
            model = posterUrl,
            contentDescription = "poster in details",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {
                    val colors = listOf(
                        Color.Black, Color.Black, Color.Transparent
                    )
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(colors), blendMode = BlendMode.DstAtop
                    )
                }) {
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
}

@Composable
fun DetailRating(
    ratingImdb: Double, ratingKinopoisk: Double
) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = imdbBrandColor)
        ) {
            Text(
                text = stringResource(R.string.detail_rating_imdb_prefix, ratingImdb),
                style = MaterialTheme.typography.labelMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.step2))
            )
        }
        Spacer(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.step1))
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = kinopoiskBrandColor)
        ) {
            Text(
                text = stringResource(R.string.detail_rating_kinopoisk_prefix, ratingKinopoisk),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.step2))
            )
        }
    }
}

@Composable
fun DetailTitle(
    name: String, originalName: String, year: String
) {
    Column {
        Text(
            text = "$name ($year)",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = originalName,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DetailGenres(
    genres: ImmutableList<String?>?
) {
    LazyRow {
        genres?.size?.let {
            items(it) { index ->
                Card(
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.step2))
                ) {
                    Text(
                        text = genres[index].toString(),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.step2))
                    )
                }
            }
        }
    }
}

@Composable
fun DetailSlogan(
    slogan: String
) {
    Text(
        text = slogan,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DetailDescription(
    description: String
) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun DetailCountry(
    countries: ImmutableList<String?>?
) {
    val countrySeparator = stringResource(R.string.detail_country_separator)
    val countryString = remember(countries) {
        countries?.joinToString(countrySeparator)
    }
    Text(
        text = stringResource(R.string.detail_country_prefix, countryString ?: ""),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun DetailLength(
    filmLength: String
) {
    Text(
        text = stringResource(R.string.detail_length_prefix, filmLength),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun DetailAge(
    age: String
) {
    Text(
        text = stringResource(R.string.detail_pegi_prefix, age),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Preview()
@Composable
private fun DetailPreview() {
    MovieTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            CircularProgressIndicator()
        }
    }
}
