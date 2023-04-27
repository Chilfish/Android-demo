package top.chilfish.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldWithErrorState(modifier: Modifier = Modifier) {
    val errorMessage = "Text input too long"
    val (text, setText) = rememberSaveable { mutableStateOf("") }
    val (isError, setIsError) = rememberSaveable { mutableStateOf(false) }
    val charLimit = 10

    fun validate(text: String) {
        setIsError(text.length > charLimit)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                validate(text)
                setText(it)
            },
            singleLine = true,
            label = { Text(if (isError) "Username*" else "Username") },
            supportingText = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Limit: ${text.length}/$charLimit ${if (isError) errorMessage else ""}",
                    textAlign = TextAlign.Start,
                )
            },
            isError = isError,
            keyboardActions = KeyboardActions { validate(text) },
        )
    }
}
