package com.duongdd.homeassignment.ui.userDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.duongdd.homeassignment.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun UserDetailView(navController: NavController, username: String, viewModel: UserDetailsViewModel = hiltViewModel()) {
    val viewState = viewModel.viewState
    val statusBarColor = rememberSystemUiController()
    SideEffect {
        statusBarColor.setStatusBarColor(color = Color.White, darkIcons = true)
    }

    LaunchedEffect(true){
        viewModel.onUiEvent(UserDetailsUiEvent.UserDetailsLoaded(username))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))

    ) {
        TopBar(navController)
        UserItemCard(viewState = viewState)
        UserInfo(viewState = viewState)
    }


}


@Composable
fun TopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFFFFFF))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { navController.popBackStack() },
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.title_user_details),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color(0xFF050505),
                fontSize = 24.sp,
                fontWeight = FontWeight.W600
            )
        )
    }
}

@Composable
fun UserItemCard(
    viewState: UserDetailsState,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        border = BorderStroke(0.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .height(128.dp)
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row {
                AsyncImage(
                    modifier = Modifier.size(72.dp, 72.dp),
                    model =  viewState.userDetail?.avatarUrl,
                    contentDescription = ""
                )
                Column(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = viewState.userDetail?.login ?: "",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color(0xFF0E0D0D),
                            fontWeight = FontWeight.W800
                        )
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Divider(modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth())
                    Spacer(modifier = Modifier.size(6.dp))
                    Row {
                        Icon(
                            modifier = Modifier
                                .size(12.dp),
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Location",
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = viewState.userDetail?.location ?: "",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color(0xFF0E0D0D),
                            )
                        )
                    }

                }
            }

        }
    }
}

@Composable
fun UserInfo(viewState: UserDetailsState){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column (
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.LightGray,
                            radius = this.size.maxDimension
                        )
                    },
                painter = painterResource(id = R.drawable.users),
                contentDescription = "Person",
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = "${viewState.userDetail?.followers}",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF0E0D0D),
                )
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = stringResource(id = R.string.followers),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF0E0D0D),
                )
            )
        }
        Column (
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.LightGray,
                            radius = this.size.maxDimension
                        )
                    },
                painter = painterResource(id = R.drawable.badge),
                contentDescription = "Person",
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = "${viewState.userDetail?.following}",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF0E0D0D),
                )
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = stringResource(id = R.string.following),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF0E0D0D),
                )
            )
        }
    }

    Spacer(modifier = Modifier.size(8.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.blog),
            style = TextStyle(
                fontSize = 22.sp,
                color = Color(0xFF0E0D0D),
                fontWeight = FontWeight.W600
            )
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = viewState.userDetail?.htmlUrl ?: "",
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFF0E0D0D),
            )
        )
    }
    
}

@Composable
fun LoadingIndicator(isInProgress: Boolean) {
    if (!isInProgress) {
        return
    }
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent
            ) {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        strokeWidth = 4.dp
                    )
                }
            }
        }
    }
}