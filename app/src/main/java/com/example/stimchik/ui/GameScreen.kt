package com.example.stimchik

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun GameInfo22(gameId: String, onBack: () -> Unit) {
    var details by remember { mutableStateOf<GameDetails?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(gameId) {
        try {
            isLoading = true
            details = RetrofitInstance.api.getGameDetails(gameId)
        } catch (e: Exception) {
            e.printStackTrace()
        }  finally {
        isLoading = false
    }}
    Column(modifier = Modifier.fillMaxSize()) {
        details?.let { game ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(details!!.info.thumb),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    androidx.compose.ui.graphics.Color.Transparent,
                                    androidx.compose.ui.graphics.Color.Black
                                ),
                                startY = 400f
                            )
                        )
                )
                Text(
                    text = details!!.info.title,
                    color = androidx.compose.ui.graphics.Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    style = androidx.compose.material3.MaterialTheme.typography.headlineLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            val steamDeal = game.deals.find { it.storeID == "1" }
            val epicDeal = game.deals.find { it.storeID == "25" }

            StorePriceCard(title = "Steam", deal = steamDeal, logoUrl = "https://w7.pngwing.com")
            StorePriceCard(title = "Epic Games", deal = epicDeal, logoUrl = "https://upload.wikimedia.org")

        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (isLoading) {
                    androidx.compose.material3.CircularProgressIndicator()
                } else {
                    Text("Ошибка загрузки данных")
                }
            }
        }
    }
}


@Composable
fun StorePriceCard(title: String, deal: Deal?, logoUrl: String) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = AbsoluteRoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color(0xFF1A1A1A))
        )
        {
            Image(
                painter = rememberAsyncImagePainter(logoUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 20.dp),
                alpha = 0.4f,
                contentScale = ContentScale.Fit
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Black,
                                Color.Transparent
                            ),
                            startX = 0f,
                            endX = 700f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )

                if (deal != null) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "$${deal.price}",
                            color = Color(0xFF00FF00),
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(Modifier.width(10.dp))

                        if (deal.price != deal.retailPrice) {
                            Text(
                                text = "$${deal.retailPrice}",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }
                } else {
                    Text(
                        "Не найдено",
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
