package top.chilfish.compose.ui.room

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import top.chilfish.compose.R
import top.chilfish.compose.components.Alert
import top.chilfish.compose.room.MAX_AGE
import top.chilfish.compose.room.UserEntity
import top.chilfish.compose.room.UserSaver

@Composable
fun RoomPage(
    modifier: Modifier = Modifier,
    viewModel: RoomViewModel
) {
    val (inputUser, setInputUser) = rememberSaveable(stateSaver = UserSaver) {
        mutableStateOf(UserEntity())
    }
    val (isAction, setIsAction) = rememberSaveable {
        mutableStateOf(false)
    }
    val (isShowAlert, setIsShowAlert) = rememberSaveable {
        mutableStateOf(false)
    }
    val (users, setUsers) = rememberSaveable {
        mutableStateOf(emptyList<UserEntity>())
    }

    LaunchedEffect(viewModel.pageState) {
        viewModel.pageState.collect { pageState ->
            if (pageState.isShowAlert) {
                setIsShowAlert(true)
            }
            setUsers(pageState.users)
        }
    }

    if (isShowAlert) {
        Alert(
            title = stringResource(R.string.alert_title_delete),
            message = stringResource(R.string.alert_message_delete),
            confirm = stringResource(R.string.confirm),
            cancel = stringResource(R.string.cancel),
            onConfirm = {
                viewModel.confirmDeleteUser()
                setIsShowAlert(false)
            },
            onCancel = { setIsShowAlert(false) }
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        InputSheet(inputUser, setInputUser)
        ButtonSheet(viewModel, inputUser, isAction, setIsAction)
        LazyColumn {
            items(users.size) { index ->
                UserCard(
                    viewModel, users[index],
                    setInputUser, setIsAction
                )
            }
        }
    }
}

@Composable
fun UserCard(
    viewModel: RoomViewModel,
    user: UserEntity,
    setInputUser: (UserEntity) -> Unit,
    setIsAction: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserInfo(
            modifier = Modifier.width(150.dp),
            title = "name", content = user.name
        )
        UserInfo(title = "age", content = user.age.toString())

        Row(modifier = Modifier.padding(start = 24.dp)) {
            TextButton(onClick = {
                setIsAction(true)
                setInputUser(user)
            }
            ) {
                Text(text = stringResource(R.string.edit))
            }
            TextButton(onClick = { viewModel.deleteUser(user) }) {
                Text(text = stringResource(R.string.delete))
            }
        }
    }
}

@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    title: String, content: String
) {
    Row(modifier = modifier.padding(8.dp, 4.dp)) {
        Text(
            text = "$title: ",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 2.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun ButtonSheet(
    viewModel: RoomViewModel,
    inputUser: UserEntity,
    isAction: Boolean,
    setIsAction: (Boolean) -> Unit
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)

    if (isAction) {
        ActionBtn(viewModel, modifier, inputUser, setIsAction)
    } else {
        OperatorBtn(viewModel, modifier, inputUser)
    }
}

@Composable
private fun ActionBtn(
    viewModel: RoomViewModel,
    modifier: Modifier = Modifier,
    inputUser: UserEntity,
    setIsAction: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        FilledTonalButton(onClick = { setIsAction(false) }) {
            Text(text = stringResource(R.string.cancel))
        }
        FilledTonalButton(
            onClick = {
                setIsAction(false)
                viewModel.editUser(inputUser)
            },
            modifier = Modifier.padding(24.dp, 0.dp)
        ) {
            Text(text = stringResource(R.string.confirm))
        }
    }
}

@Composable
private fun OperatorBtn(
    viewModel: RoomViewModel,
    modifier: Modifier = Modifier,
    inputUser: UserEntity,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        FilledTonalButton(onClick = { viewModel.addUser(inputUser) }) {
            Text(text = stringResource(R.string.add))
        }
        FilledTonalButton(
            onClick = { viewModel.queryUser(inputUser) },
            modifier = Modifier.padding(24.dp, 0.dp)
        ) {
            Text(text = stringResource(R.string.query))
        }
        FilledTonalButton(onClick = { viewModel.loadUsers() }) {
            Text(text = stringResource(R.string.load))
        }
    }
}

@Composable
fun InputSheet(
    inputUser: UserEntity,
    setInputUser: (UserEntity) -> Unit
) {
    val (name, setName) = rememberSaveable { mutableStateOf("") }
    val (age, setAge) = rememberSaveable { mutableStateOf("") }
    val (isFirst, setIsFirst) = rememberSaveable { mutableStateOf(true) }
    val (isAgeError, setIsAgeError) = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(inputUser) {
        setName(inputUser.name)
        setAge((inputUser.age.toString()))
        Log.d("Room", "inputUser: $inputUser")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInput(
            title = stringResource(R.string.name),
            value = name,
            onValueChange = {
                if (it.length > 20) return@UserInput
                setName(it)
                setInputUser(
                    UserEntity(
                        id = inputUser.id,
                        name = it,
                        age = age.toIntOrNull() ?: 0
                    )
                )
            }
        )
        UserInput(
            title = stringResource(R.string.age),
            value = if (isFirst) "" else age,
            keyboardType = KeyboardType.Number,
            onValueChange = {
                if (!it.isDigitsOnly() || it.length > 4) return@UserInput
                setAge(it)

                val itAge = it.toIntOrNull() ?: -1

                if ((itAge < 0 || itAge > MAX_AGE)) {
                    if (it != "") setIsAgeError(true)
                    return@UserInput
                } else {
                    setIsAgeError(false)
                }
                setIsFirst(false)
                setInputUser(
                    UserEntity(
                        id = inputUser.id,
                        name = name,
                        age = itAge
                    )
                )
            },
            isError = isAgeError,
            errorText = stringResource(R.string.age_error)
        )
    }
}

@Composable
private fun UserInput(
    title: String,
    value: String,
    placeholder: String = "",
    errorText: String = "",
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(8.dp)
            .width(260.dp),
        label = { Text(text = title) },
        placeholder = { Text(text = placeholder) },

        singleLine = true,
        maxLines = 1,
        trailingIcon = { Icons.Default.Clear },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),

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
