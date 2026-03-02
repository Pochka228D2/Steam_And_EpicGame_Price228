package com.example.stimchik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stimchik.ui.theme.PurpleGrey40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color=Color.Black
            ) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "menu") {
                    composable("menu") {
                        PlaysScreen(onNavigateToPlay = { gameId ->
                            navController.navigate("playgame/$gameId")
                        })
                    }

                    composable("playgame/{gameId}") { backStackEntry ->
                        val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
                        GameInfo22(gameId = gameId, onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}

            @Composable
            fun PlaysScreen(onNavigateToPlay: (String) -> Unit) {
                var search by remember { mutableStateOf("") }
                val cheapSharkGames = remember { mutableStateListOf<CheapSharkGame>() }
                var isSearching by remember { mutableStateOf(false) }
                val kek = FontFamily(Font(R.font.lol))


                LaunchedEffect(search) {
                    if (search.isNotEmpty() && search.length > 2) { // Поиск только если больше 2 символов
                        isSearching = true
                        try {
                            cheapSharkGames.clear()
                            val results = RetrofitInstance.api.searchGames(title = search)
                            cheapSharkGames.addAll(results)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            isSearching = false
                        }
                    }
                }



                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,


                    ) {

                    item {
                        Spacer(Modifier.height(20.dp))

                        OutlinedTextField(
                            value = search,
                            shape = AbsoluteRoundedCornerShape(20.dp),
                            label = { Text("Поиск игры", fontFamily = kek) },
                            onValueChange = {
                                search = it
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF00F60A), // Зеленый когда поле в фокусе
                                unfocusedBorderColor = Color.White, // Зеленый когда не в фокусе
                                focusedLabelColor = Color.White, // Цвет лейбла в фокусе
                                cursorColor = Color(0xFF00F60A), // Цвет курсора
                                focusedTextColor = Color(0xFF00F60A), // Цвет текста
                                unfocusedTextColor = Color.White
                            )
                        )
                    }

                    items(cheapSharkGames) { game ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF1A1A1A))
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFF90EE90),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable { onNavigateToPlay(game.gameID) }
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = rememberAsyncImagePainter(game.thumb),
                                    contentDescription = null,
                                    modifier = Modifier  .fillMaxWidth()
                                        .height(120.dp)
                                        .clip(RoundedCornerShape(12.dp)) // Округлые углы у картинки
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFF90EE90).copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                    }

                                Text(text = game.external, fontFamily = kek,color = Color(0xFF90EE90))
                            }
                        }
                    }
                }




