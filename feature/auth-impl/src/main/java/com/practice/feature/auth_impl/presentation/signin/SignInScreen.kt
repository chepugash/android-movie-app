package com.practice.feature.auth_impl.presentation.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practice.core.common.navigation.DestinationScreen
import com.practice.feature.auth_impl.presentation.signin.util.MaskVisualTransformation
import com.practice.core.designsystem.R
import com.practice.core.designsystem.ui.theme.MovieTheme
import com.practice.feature.auth_impl.presentation.signin.mvi.SignInEffect
import com.practice.feature.auth_impl.presentation.signin.mvi.SignInEvent
import com.practice.feature.auth_impl.presentation.signin.mvi.SignInState
import com.practice.feature.auth_impl.presentation.signin.mvi.SignInViewModel
import org.koin.androidx.compose.koinViewModel

private const val PHONE_MASK = "+7 (###) ### - ## - ##"
private const val PHONE_DIGITS_COUNT = 10

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = koinViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(null)

    SignInContent(
        state = state.value,
        eventHandler = remember { viewModel::event }
    )

    SignInScreenEffect(
        navController = navController,
        effect = effect
    )
}

@Composable
fun SignInScreenEffect(
    navController: NavController,
    effect: SignInEffect?
) {
    LaunchedEffect(effect) {
        when (effect) {

            null -> Unit

            is SignInEffect.NavigateToConfirm -> {
                navController.navigate(
                    DestinationScreen.ConfirmScreen.withArgs(effect.phone)
                )
            }

            SignInEffect.NavigateToHome -> {
                navController.navigate(
                    DestinationScreen.HomeScreen.route
                )
            }
        }
    }
}

@Composable
private fun SignInContent(
    state: SignInState,
    eventHandler: (SignInEvent) -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.step4),
                end = dimensionResource(id = R.dimen.step4),
                bottom = dimensionResource(id = R.dimen.step4),
                top = dimensionResource(id = R.dimen.toolbar_height)
            )
    ) {

        Text(
            text = stringResource(R.string.auth_welcome_message),
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step2)))

        Text(
            text = stringResource(R.string.auth_enter_number_message),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step3)))

        OutlinedTextField(
            value = state.phone,
            onValueChange = { input ->
                if (input.length <= PHONE_DIGITS_COUNT) {
                    eventHandler(SignInEvent.OnPhoneChanged(input))
                }
            },
            visualTransformation = MaskVisualTransformation(PHONE_MASK),
            singleLine = true,
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.round_shape)),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    if (state.phone.length == PHONE_DIGITS_COUNT
                        && state.phone.toLongOrNull() != null) {
                        eventHandler(SignInEvent.OnConfirmClick)
                    }
                }
            ),
            textStyle = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    eventHandler(SignInEvent.OnConfirmClick)
                },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.round_shape)),
                enabled = (state.phone.length == PHONE_DIGITS_COUNT
                        && state.phone.toLongOrNull() != null),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = stringResource(R.string.auth_btn_continue),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignInPreview() {
    MovieTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
//            SignInContent()
        }
    }
}
