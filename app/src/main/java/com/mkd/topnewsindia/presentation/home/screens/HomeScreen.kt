package com.mkd.topnewsindia.presentation.home.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.mkd.topnewsindia.data.model.Article
import com.mkd.topnewsindia.data.model.Source
import com.mkd.topnewsindia.presentation.home.components.ArticleList
import com.mkd.topnewsindia.presentation.home.components.DropdownMenuItem
import com.mkd.topnewsindia.presentation.home.components.HomeTopBar
import com.mkd.topnewsindia.presentation.home.components.TopNewsItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>, navigateToDetails: (Article) -> Unit
) {

    var sortOrder by remember { mutableStateOf(SortOrder.None) }
    var showSortMenu by remember { mutableStateOf(false) }

    val topTitles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items.slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83D\uDCF0 \u2B50 ") { it.title.toString() }
            } else {
                "No Highlights"
            }
        }
    }

    val topArticle = Article(
        title = "Cement stocks to feel the heat of falling demand, prices",
        author = "Rajesh Naidu",
        source = Source(id = "the-times-of-india", name = "The Times of India"),
        urlToImage = "https://img.etimg.com/thumb/msid-110464553,width-1070,height-580/photo.jpg",
        content = "In the March 2024 quarter, all-India average cement price fell by 5.1% year-on-year to Rs359 per 50 kg amid weak demand across regions. As a result, cement firms focused more on sales volume.… [+217 chars]",
        description = "In the March 2024 quarter, all-India average cement price fell by 5.1% year-on-year to Rs359 per 50 kg amid weak demand across regions. As a result, cement firms focused more on sales volume. Sales volume of large cement companies grew by 7-22% during the qua…",
        publishedAt = "2024-05-27T10:22:23Z",
        url = "https://economictimes.indiatimes.com/markets/stocks/news/cement-stocks-likely-to-face-heat-in-near-term-amid-lower-demand-falling-prices/articleshow/110464584.cms"
    )

    Scaffold(
        topBar = { HomeTopBar(onSortClick = { showSortMenu = true }) },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {

                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp)
                        .background(Color.LightGray)
                ) {
                    DropdownMenu(modifier = Modifier.background(Color.LightGray),
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false }) {
                        DropdownMenuItem(onClick = {
                            sortOrder = SortOrder.Ascending
                            showSortMenu = false
                        }) {
                            Text(text = "Ascending", color = Color.Black)
                        }
                        DropdownMenuItem(onClick = {
                            sortOrder = SortOrder.Descending
                            showSortMenu = false
                        }) {
                            Text(text = "Descending", color = Color.Black)
                        }
                        DropdownMenuItem(onClick = {
                            sortOrder = SortOrder.None
                            showSortMenu = false
                        }) {
                            Text(text = "Clear", color = Color.Black)
                        }
                    }
                }

                Text(
                    text = topTitles,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .basicMarquee(),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Hot Topic",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                TopNewsItem(article = topArticle, onClick = { navigateToDetails(topArticle) })

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

                Text(
                    text = "Latest News India",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                ArticleList(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    articles = articles,
                    sortOrder = sortOrder,
                    onClick = navigateToDetails,
                )

            }
        })
}

enum class SortOrder {
    Ascending, Descending, None
}
