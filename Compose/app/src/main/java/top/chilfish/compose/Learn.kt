package top.chilfish.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

class Learn {
    private val padding = 16.dp

    @Composable
    fun Show() {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF))
                .padding(horizontal = padding)
        ) {
            items(6) { index ->
                Card1(
                    avatar = "https://p.chilfish.top/avatar.webp",
                    title = "Chilfish $index",
                    subtitle = "@chilfish_$index",
                )
            }

            item {
                Count()
                Parent()
                Remember()
                RememberSavable()

                ListComposable(myList = listOf("A", "B", "C"))
                TextList()
            }
        }
    }

    @Composable
    fun Card1(
        avatar: Any,
        title: String,
        subtitle: String,
        onClick: () -> Unit = { },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = padding, vertical = padding / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = avatar,
                contentDescription = "avatar",
                placeholder = painterResource(R.drawable.avatar),
                modifier = Modifier
                    .width(64.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Column(modifier = Modifier.padding(horizontal = padding)) {
                Text(
                    text = title,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Text(
                    text = subtitle,
                    modifier = Modifier
                        .offset(x = 4.dp),
                    color = Color(0xFF666666),
                    maxLines = 2,
                )
            }
        }
    }


    @Composable
    fun ListComposable(myList: List<String>) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                for (item in myList) {
                    Text("Item: $item")
                }
            }
            Text("Count: ${myList.size}")
        }
    }

    @Composable
    fun Count() {
        val (count, setCount) = rememberSaveable { mutableStateOf(0) }
        val (show, setShow) = remember { mutableStateOf(false) }

        Button(onClick = { setCount(count + 1); setShow(!show) }) {
            Text("Count: $count ${if (show) "Show" else "Hide"}")
        }
    }

    @Composable
    fun Parent() {
        val (text, setText) = remember { mutableStateOf("Hello, World!") }

        Child1(onButtonClicked = { setText("Button clicked!") })
        Child2(text = text)
    }

    @Composable
    fun Child1(onButtonClicked: () -> Unit) {
        Button(onClick = { onButtonClicked() }) {
            Text(text = "Click me!")
        }
    }

    @Composable
    fun Child2(text: String) {
        Text(text = text)
    }

    @Composable
    fun Remember() {
        Column(modifier = Modifier.padding(16.dp)) {
            val (name, setName) = remember { mutableStateOf("") }

            if (name.isNotEmpty()) {
                Text(
                    text = "Remember, $name",
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            OutlinedTextField(
                value = name,
                onValueChange = { setName(it) },
                label = { Text("Name") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )
        }
    }

    @Composable
    fun RememberSavable() {
        val (name, setName) = rememberSaveable { mutableStateOf("") }

        HelloContent(name = name, onNameChange = { setName(it) })
    }

    @Composable
    fun HelloContent(name: String, onNameChange: (String) -> Unit) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "RememberSavable, $name",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Name") }
            )
        }
    }

    @Composable
    fun TextList() {
        val (textList, setTextList) = rememberSaveable { mutableStateOf(emptyList<String>()) }
        Column {
            Row {
                Button(onClick = { setTextList(textList + "Text") }) {
                    Text("Add Text")
                }
                Button(
                    onClick = { setTextList(textList.dropLast(1)) },
                    modifier = Modifier.padding(horizontal = padding)
                ) {
                    Text("Pop Text")
                }
                Button(onClick = { setTextList(emptyList()) }) {
                    Text("Clear Text")
                }
            }
            TextListView(textList = textList)
        }
    }

    @Composable
    fun TextListView(textList: List<String>) {
        Column {
            textList.forEach { text ->
                Text(text)
            }
        }
    }


    @Preview
    @Composable
    fun PrevList() {
        LazyColumn(
            modifier = Modifier
                .background(Color(0xFFFFFFFF))
        ) {
            items(4) { index ->
                Card1(
                    avatar = R.drawable.avatar,
                    title = "Chilfish $index",
                    subtitle = "@chilfish_$index",
                )
            }
        }
    }
}