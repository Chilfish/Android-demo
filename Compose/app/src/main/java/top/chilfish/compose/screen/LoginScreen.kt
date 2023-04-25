package top.chilfish.compose.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import top.chilfish.compose.R
import top.chilfish.compose.models.LoginViewModel
import top.chilfish.compose.navigation.Routers

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val (username, setUsername) = rememberSaveable { mutableStateOf("") }
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }

    val (isUsnError, setUsnError) = rememberSaveable { mutableStateOf(false) }
    val (isPwdError, setPwdError) = rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp, top = 50.dp),
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Title()
            LoginInput(
                label = stringResource(R.string.username),
                value = username,
                onValueChange = {
                    setUsername(it)
                    setUsnError(false)
                },
                placeholder = stringResource(R.string.username_hint),
                isError = isUsnError,
                errorText = stringResource(R.string.username_invalid)
            )
            LoginInput(
                label = stringResource(R.string.password),
                value = password,
                onValueChange = {
                    setPassword(it)
                    if (password.length < 8) setPwdError(true)
                    else setPwdError(false)
                },
                placeholder = stringResource(R.string.password_hint),
                isPassword = true,
                isError = isPwdError,
                errorText = stringResource(R.string.password_invalid)
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Btn(
                    text = stringResource(R.string.register),
                    onClick = { }
                )
                Btn(
                    text = stringResource(R.string.login),
                    onClick = {
                        if (username.isEmpty()) setUsnError(true)
                        viewModel.onLogin(username, password, navController)
                    }
                )
            }
        }
    }
}

@Composable
fun Title() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.login_prompt),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(0.dp, 10.dp)
        )
    }
}

@Composable
fun VisibilityBtn(
    passwordHidden: Boolean,
    setPasswordHidden: (Boolean) -> Unit
) {
    IconButton(onClick = { setPasswordHidden(!passwordHidden) }) {
        val visibilityIcon =
            if (passwordHidden) painterResource(R.drawable.baseline_visibility_24)
            else painterResource(R.drawable.baseline_visibility_off_24)
        val description =
            if (passwordHidden) "Show password" else "Hide password"
        Icon(painter = visibilityIcon, contentDescription = description)
    }
}

@Composable
fun LoginInput(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorText: String = "",
) {
    val (passwordHidden, setPasswordHidden) = rememberSaveable { mutableStateOf(true) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        val (input, hint) = createRefs()
        Box(Modifier.constrainAs(input) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                modifier = modifier
                    .align(Alignment.Center)
                    .width(300.dp),
                value = value,
                onValueChange = onValueChange,
                label = { Text(text = label) },
                placeholder = { Text(text = placeholder) },
                visualTransformation = if (isPassword && passwordHidden)
                    PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                isError = isError,
                singleLine = true,
                trailingIcon = {
                    if (isPassword) VisibilityBtn(passwordHidden, setPasswordHidden)
                }
            )
        }

        if (isError) {
            Text(
                text = "* $errorText",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .constrainAs(hint) {
                        top.linkTo(input.bottom)
                        start.linkTo(input.start)
                    }
                    .padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun Btn(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(16.dp)
            .width(110.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(text = text, color = Color.White)
    }
}