package top.chilfish.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import top.chilfish.compose.data.Profile
import top.chilfish.compose.models.ContactViewModel
import top.chilfish.compose.models.UIState

@Composable
fun ColumnScope.ContactScreen(
    uiState: UIState,
    viewModel: ContactViewModel,
    navController: NavHostController,
) {
    val contacts by viewModel.contacts.collectAsState()

    LazyColumn(Modifier.weight(1f)) {
        itemsIndexed(contacts) { _, contact ->
            ContactItem(
                profile = contact,
                onClick = { viewModel.onContactSelected(contact.uid, navController) }
            )
        }
    }
}


@Composable
fun ContactItem(profile: Profile, onClick: () -> Unit) {
    val padding = 12.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = profile.avatar,
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
                text = profile.name,
                modifier = Modifier.padding(bottom = 4.dp),
                fontSize = 18.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactItemPreview() {
    ContactItem(
        Profile(
            avatar = "https://p.chilfish.top/avatar.webp",
            name = "Chilfish"
        )
    ) {
    }
}