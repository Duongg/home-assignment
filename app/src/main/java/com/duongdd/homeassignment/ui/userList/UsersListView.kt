package com.duongdd.homeassignment.ui.userList


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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.duongdd.homeassignment.R
import com.duongdd.homeassignment.datasource.entity.UserItemEntity
import com.duongdd.homeassignment.navigation.UserRoutes
import com.duongdd.homeassignment.ui.theme.HomeAssignmentTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun UserListView(navController: NavController, viewModel: UserListViewModel = hiltViewModel()) {
    val viewState = viewModel.viewState
    val statusBarColor = rememberSystemUiController()
    SideEffect {
        statusBarColor.setStatusBarColor(color = Color.White, darkIcons = true)
    }
    LaunchedEffect(true){
        viewModel.onUiEvent(UserListUiEvent.UserListLoaded)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))

    ) {
        TopBar()
        Column(modifier = Modifier
            .fillMaxSize()) {
            when{
                !viewState.userList.isNullOrEmpty() ->
                    ResultView(navController = navController,
                        listItem = viewState.userList,
                        onItemScrolled = remember {
                            { viewModel.onUiEvent(UserListUiEvent.ItemScrolled(it)) }
                        }
                    )

                viewState.userList?.isEmpty() == true -> EmptyView()
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFFFFFF))
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.title_user_list),
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
fun ResultView(
    listItem: List<UserModel>? = null,
    onItemScrolled: ((Int) -> Unit)? = null,
    navController: NavController,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(requireNotNull(listItem)) { index, item ->
            LaunchedEffect(key1 = true) {
                onItemScrolled?.invoke(index)
            }
            UserItemCard(
                itemModel = item,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserItemCard(
    itemModel: UserModel,
    navController: NavController,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { testTagsAsResourceId = true },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        border = BorderStroke(0.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .clickable {
                    navController.navigate(UserRoutes.UserDetailsScreen.route + "?username=${itemModel.login}")
                }
                .padding(16.dp)

        )
        {
            Row {
                AsyncImage(
                    modifier = Modifier.size(72.dp, 72.dp),
                    model =  itemModel.imageUrl,
                    contentDescription = ""
                )
                Column(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = itemModel.login ?: "",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color(0xFF0E0D0D),
                            fontWeight = FontWeight.W800
                        )
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Divider(modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth())
                    Spacer(modifier = Modifier.size(6.dp))

                    ClickableText(
                        text = AnnotatedString(itemModel.htmlUrl) ,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color(0xFF2979FF),
                            fontWeight = FontWeight.W400,
                            textDecoration = TextDecoration.Underline
                        ),
                        onClick = {
                            itemModel.htmlUrl
                        })
                }
            }


        }
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

    }
}
