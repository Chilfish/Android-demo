package top.chilfish.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import top.chilfish.compose.Profile
import top.chilfish.compose.R
import top.chilfish.compose.theme.Purple80


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

@Composable
fun NavBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.inverseOnSurface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavBarItem(
            { navController.navigate("home") },
            icon = R.drawable.outline_chat_24,
            contentDescription = "home",
        )
        NavBarItem(
            { navController.navigate("contact") },
            icon = R.drawable.contact,
            contentDescription = "contact",
        )
        NavBarItem(
            { navController.navigate("profile/${true}") },
            icon = R.drawable.outline_person_24,
            contentDescription = "profile",
        )
    }
}

@Composable
fun RowScope.NavBarItem(
    navigator: () -> Unit,
    @DrawableRes icon: Int,
    contentDescription: String,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = navigator,
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