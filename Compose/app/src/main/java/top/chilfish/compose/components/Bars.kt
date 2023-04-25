package top.chilfish.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import top.chilfish.compose.R
import top.chilfish.compose.data.Profile


@Composable
fun HomeBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(12.dp)
    ) {
        Text(
            text = "App Name",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Icon(
            Icons.Rounded.Search,
            contentDescription = "Search",
            modifier = Modifier.padding(12.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun MessageBar(
    profile: Profile,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Rounded.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.padding(end = 30.dp),
            tint = color
        )
        AsyncImage(
            model = profile.avatar,
            contentDescription = "avatar",
            placeholder = painterResource(R.drawable.default_avatar),
            modifier = Modifier
                .width(40.dp)
                .clip(RoundedCornerShape(50.dp))
        )
        Text(
            text = profile.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            color = color,
        )
        Icon(
            Icons.Rounded.MoreVert,
            contentDescription = "More",
            modifier = Modifier.padding(12.dp),
            tint = color
        )
    }
}
