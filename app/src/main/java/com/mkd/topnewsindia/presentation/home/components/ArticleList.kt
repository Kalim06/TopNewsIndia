package com.mkd.topnewsindia.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.mkd.topnewsindia.data.model.Article
import com.mkd.topnewsindia.presentation.home.screens.EmptyScreen
import com.mkd.topnewsindia.presentation.home.screens.SortOrder
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit,
    sortOrder: SortOrder
) {
    val handlePagingResult = handlePagingResultWithSort(articles)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    var articleList by remember { mutableStateOf<List<Article>>(emptyList()) } // Provide a default empty list

    if (handlePagingResult && articles.itemSnapshotList.isNotEmpty()) { // Check if articles are not empty
        LaunchedEffect(articles) {
            val list = mutableListOf<Article>()
            articles.itemSnapshotList.forEach { article ->
                if (article != null) {
                    list.add(article)
                }
            }
            articleList = list
        }

        Box(modifier = modifier.fillMaxSize()) {
            articleList.let { list ->
                val sortedArticles = when (sortOrder) {
                    SortOrder.Ascending -> list.sortedBy { dateFormat.parse(it.publishedAt) }
                    SortOrder.Descending -> list.sortedByDescending { dateFormat.parse(it.publishedAt) }
                    else -> list
                }
                LazyColumn {
                    items(
                        count = sortedArticles.size,
                    ) {
                        sortedArticles[it].let { article ->
                            NewsItem(article = article, onClick = { onClick(article) })
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun handlePagingResultWithSort(articles: LazyPagingItems<Article>): Boolean {
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffectWithSort()
            false
        }

        error != null -> {
            EmptyScreen(error = error)
            false
        }

        else -> {
            true
        }
    }
}

@Composable
fun ShimmerEffectWithSort() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(10) {
            ShimmerArticleItem()
        }
    }
}