package com.mkd.topnewsindia.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mkd.topnewsindia.R
import com.mkd.topnewsindia.data.model.Article
import com.mkd.topnewsindia.data.model.Source

@Composable
fun NewsItem(article: Article, onClick: () -> Unit) {

    val context = LocalContext.current

    Column(modifier = Modifier.clickable(onClick = onClick)) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            AsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                error = painterResource(id = R.drawable.no_image),
                contentDescription = null,
            )

            Column(
                modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = article.title.toString(),
                    maxLines = 3,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
                val authorText = article.author.takeIf { it != "null" }?.let { "Author: $it" }
                    ?: "Author Not Found"
                Text(
                    text = authorText,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "Source: ${article.source?.name}",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color.LightGray)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsItem() {
    val sampleArticle = Article(
        title = "Sample News Title",
        author = "John Doe",
        source = Source(id = "", name = "Sample Source"),
        urlToImage = "https://example.com/sample-image.jpg",
        content = "Sample content",
        description = "Sample description",
        publishedAt = "2023-08-01T00:00:00Z",
        url = "https://example.com/sample-news",
    )

    NewsItem(article = sampleArticle, onClick = { /* Handle click */ })
}
