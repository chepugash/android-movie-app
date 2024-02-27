package com.practice.feature.auth_impl.presentation.confirm

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practice.core.common.navigation.DestinationScreen
import com.practice.feature.auth_impl.presentation.signin.util.MaskVisualTransformation
import com.practice.core.designsystem.R
import com.practice.feature.auth_impl.presentation.confirm.mvi.ConfirmEffect
import com.practice.feature.auth_impl.presentation.confirm.mvi.ConfirmEvent
import com.practice.feature.auth_impl.presentation.confirm.mvi.ConfirmState
import com.practice.feature.auth_impl.presentation.confirm.mvi.ConfirmViewModel
import org.koin.androidx.compose.koinViewModel

private const val CODE_LENGTH = 6
private const val CODE_MASK = "#    #    #    #    #    #"
private const val SEC_IN_MIN = 60
private const val DIGITS_COUNT = 10

@Composable
fun ConfirmScreen(
    phone: String,
    navController: NavController,
    viewModel: ConfirmViewModel = koinViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(null)

    ConfirmContent(
        deliveredPhone = phone,
        state = state.value,
        eventHandler = remember { viewModel::event }
    )

    ConfirmScreenEffect(
        navController = navController,
        effect = effect
    )
}

@Composable
fun ConfirmScreenEffect(
    navController: NavController,
    effect: ConfirmEffect?
) {
    LaunchedEffect(effect) {
        when (effect) {
            null -> Unit
            ConfirmEffect.NavigateBack -> {
                navController.navigateUp()
            }

            ConfirmEffect.NavigateToHome -> {
                navController.navigate(
                    DestinationScreen.HomeScreen.route
                )
            }
        }
    }
}

@Composable
fun ConfirmContent(
    deliveredPhone: String,
    state: ConfirmState,
    eventHandler: (ConfirmEvent) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    LaunchedEffect(Unit) {
        if (deliveredPhone.isNotBlank()) run {
            eventHandler(ConfirmEvent.OnPhoneDelivered(phone = deliveredPhone))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.step4),
                end = dimensionResource(id = R.dimen.step4),
                bottom = dimensionResource(id = R.dimen.step4),
                top = dimensionResource(id = R.dimen.toolbar_height)
            )
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "navigation back button",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        eventHandler(ConfirmEvent.OnBackClick)
                    }
            )
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.step3)))
            Text(
                text = stringResource(R.string.auth_enter_code_message),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step2)))

        Text(
            text = stringResource(R.string.auth_code_was_sent_message, state.phone),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step3)))

        OutlinedTextField(
            value = state.code,
            onValueChange = { input ->
                if (input.length <= CODE_LENGTH) {
                    eventHandler(ConfirmEvent.OnCodeChanged(input.filter { it.isDigit() }))
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.round_shape)),
            visualTransformation = MaskVisualTransformation(CODE_MASK),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    if (state.code.length == CODE_LENGTH) {
                        eventHandler(ConfirmEvent.OnConfirmClick)
                    }
                }
            ),
            textStyle = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = dimensionResource(id = R.dimen.tf_border_width),
                    color = if (state.error != null) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.round_shape))
                )
                .focusRequester(focusRequester)
        )

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.step3)))

        if (state.time > 0) {
            Text(
                text = stringResource(
                    R.string.auth_sent_code_again_message,
                    timerMinutes(state.time),
                    timerSeconds(state.time)
                ),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LaunchedEffect(eventHandler) {
                eventHandler.invoke(ConfirmEvent.OnTimerStop)
            }
            Text(
                text = stringResource(R.string.auth_sent_code_again_btn),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.error,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        eventHandler(ConfirmEvent.OnTimerStart)
                    }
            )
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    eventHandler(ConfirmEvent.OnConfirmClick)
                },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.round_shape)),
                enabled = (state.code.length == CODE_LENGTH),
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

private fun timerMinutes(time: Int): String {
    val minutes = time / SEC_IN_MIN
    return if (minutes >= DIGITS_COUNT) "$minutes" else "0$minutes"
}

private fun timerSeconds(time: Int): String {
    val seconds = time % SEC_IN_MIN
    return if (seconds >= DIGITS_COUNT) "$seconds" else "0$seconds"
}
