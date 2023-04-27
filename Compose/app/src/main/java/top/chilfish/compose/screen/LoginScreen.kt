package top.chilfish.compose.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import top.chilfish.compose.R
import top.chilfish.compose.models.LoginViewModel

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
//                    setUsnError(false)
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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = modifier.width(300.dp),
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            isError = isError,
            singleLine = true,

            visualTransformation = if (isPassword && passwordHidden)
                PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) VisibilityBtn(passwordHidden, setPasswordHidden)
            },

            placeholder = { Text(text = placeholder) },
            supportingText = {
                if (isError) Text(
                    text = "* $errorText",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        )
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