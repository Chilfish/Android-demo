package top.chilfish.compose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import top.chilfish.compose.Profile
import top.chilfish.compose.R

@Composable
fun ProfileScreen(
    profile: Profile,
    isMe: Boolean,
    onClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Hero(profile)
        ProfileInfo(profile)
        ProfileBtn(isMe, onClick)
    }
}

@Composable
fun Avatar(
    url: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = url,
        contentDescription = "avatar",
        placeholder = painterResource(R.drawable.default_avatar),
        modifier = modifier
    )
}

@Composable
fun Hero(profile: Profile) {
    Row(
        Modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth()
    ) {
        Avatar(
            url = profile.avatar, modifier = Modifier
                .width(100.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Column(Modifier.padding(24.dp)) {
            Text(
                text = profile.name,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(text = "uid: ${profile.uid}")
        }
    }
}

@Composable
fun ProfileInfo(profile: Profile) {
    Card(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .shadow(2.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = "Account",
                modifier = Modifier.padding(12.dp),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            ProfileFrag("Email", profile.email)
            ProfileFrag("Bio", profile.bio)
        }
    }
}

@Composable
fun ProfileFrag(title: String, subtitle: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp)
    ) {
        Text(text = subtitle)
        Text(
            text = title,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
            color = Color.Gray
        )
    }
}

@Composable
fun ProfileBtn(isMe: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        colors = ButtonDefaults.buttonColors(
            if (isMe) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(12.dp)

    ) {
        Text(
            text = if (isMe) "Log Out" else "Send Message",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(12.dp),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        Profile(),
        true,
    ) {}
}