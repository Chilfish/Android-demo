package top.chilfish.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import top.chilfish.compose.Profile
import top.chilfish.compose.R


@Composable
fun HomeBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.Primary))
            .padding(12.dp)
    ) {
        Text(
            text = "App Name",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            color = Color.White,
        )
        Icon(
            Icons.Rounded.Search,
            contentDescription = "Search",
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun MessageBar(
    profile: Profile,
    color: Color = colorResource(id = R.color.Primary)
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

@Composable
fun MessageBarPreview() {
    MessageBar(
        Profile(name = "Chilfish", avatar = "https://p.chilfish.top/avatar.webp"),
        Color.White
    )
}

@Composable
fun NavBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(colorResource(id = R.color.Background)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavBarItem(
            icon = R.drawable.outline_chat_24,
            contentDescription = "Home",
        )
        NavBarItem(
            icon = R.drawable.contact,
            contentDescription = "Chat",
        )
        NavBarItem(
            icon = R.drawable.outline_person_24,
            contentDescription = "Profile",
        )
    }
}

@Composable
fun RowScope.NavBarItem(
    @DrawableRes icon: Int,
    contentDescription: String,
    tint: Color = colorResource(id = R.color.Primary)
) {
    Button(
        onClick = {},
        Modifier
            .weight(1f)
            .width(56.dp)
            .padding(12.dp, 0.dp)
            .fillMaxHeight(),
        shape = RectangleShape,
        colors = ButtonDefaults.outlinedButtonColors()
    ) {
        Icon(
            painterResource(id = icon),
            contentDescription,
            Modifier
                .size(24.dp)
                .weight(1f),
            tint
        )
    }
}