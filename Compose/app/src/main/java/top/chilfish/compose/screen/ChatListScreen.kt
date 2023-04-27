package top.chilfish.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import top.chilfish.compose.R
import top.chilfish.compose.data.ChatItem
import top.chilfish.compose.data.Profile
import top.chilfish.compose.models.ChatListViewModel

@Composable
fun ColumnScope.ChatListScreen(
    viewModel: ChatListViewModel,
    navController: NavHostController,
) {
    val chats by viewModel.chats.collectAsState()

    LazyColumn(Modifier.weight(1f)) {
        itemsIndexed(chats) { _, chat ->
            ChatListItem(
                chat = chat,
                onClick = { viewModel.onChatSelected(chat.profile.uid, navController) }
            )
        }
    }
}


@Composable
fun ChatListItem(chat: ChatItem, onClick: () -> Unit) {
    val padding = 12.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(padding),
    ) {
        AsyncImage(
            model = chat.profile.avatar,
            contentDescription = "avatar",
            placeholder = painterResource(R.drawable.default_avatar),
            modifier = Modifier
                .width(56.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Column(
            modifier = Modifier
                .padding(horizontal = padding, vertical = padding / 2)
                .weight(1f)
        ) {
            Text(
                text = chat.profile.name,
                modifier = Modifier.padding(bottom = 4.dp),
                fontSize = 18.sp,
            )
            Text(
                text = chat.content,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1,
            )
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = chat.time,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnScope.ChatListPreview() {
    LazyColumn(Modifier.weight(1f)) {
        items(6) { index ->
            ChatListItem(
                chat = ChatItem(
                    id = "1",
                    profile = Profile(
                        avatar = "https://p.chilfish.top/avatar.webp",
                        uid = "1",
                        name = "Chilfish",
                        bio = "I am Chilfish",
                        email = "chill4fish@gmail.com"
                    ),
                    content = "Hello, I am Chilfish. Index of $index",
                    time = "12:00"
                )
            ) { }
        }
    }
}