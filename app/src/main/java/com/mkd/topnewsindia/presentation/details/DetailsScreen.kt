package com.mkd.topnewsindia.presentation.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mkd.topnewsindia.R
import com.mkd.topnewsindia.data.model.Article
import com.mkd.topnewsindia.data.model.Source
import com.mkd.topnewsindia.presentation.details.components.DetailsTopBar
import com.mkd.topnewsindia.utils.ButtonState
import com.mkd.topnewsindia.utils.TextToSpeechHolder
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DetailsScreen(article: Article, navigateUp: () -> Unit, navigateToWebView: (Article) -> Unit) {

    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val articleDate = dateFormat.parse(article.publishedAt.toString())
    val tts = remember { TextToSpeechHolder(context) }
    var playButtonState by remember { mutableStateOf(ButtonState.Play) }


    val cleanContent = article.content?.substringBefore("...")
    Log.i("06470647", "Clean Content: $cleanContent")

    val articleString =
        "Title - ${article.title}, written by ${article.author}, content - ${article.content}, source - ${article.source?.name}. To read the complete article click the button below."

    Scaffold(topBar = {
        DetailsTopBar(onBackClick = navigateUp,
            title = article.source?.name.toString(),
            buttonState = playButtonState,
            onPlayClick = {
                handleButtonClick(
                    tts = tts,
                    buttonState = playButtonState,
                    extractedText = articleString,
                    setButtonState = { playButtonState = it },
                    speed = 1f
                )
            })
    }, content = { paddingValues ->

        LazyColumn(contentPadding = paddingValues, modifier = Modifier.padding(all = 16.dp)) {

            item {
                AsyncImage(
                    modifier = Modifier
                        .height(200.dp)
                        .fillParentMaxWidth()
                        .clip(shape = MaterialTheme.shapes.large),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.top_story_image),
                    model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                    error = painterResource(id = R.drawable.no_image),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(article.title.toString(), style = MaterialTheme.typography.headlineLarge)
                Spacer(Modifier.height(8.dp))
                Text(article.description.toString(), style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(8.dp))
            }

            item {
                Row {
                    Image(
                        imageVector = Icons.Filled.AccountBox,
                        contentDescription = null,
                        modifier = Modifier.size(45.dp),
                        colorFilter = ColorFilter.tint(LocalContentColor.current),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = article.author.toString(),
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = articleDate!!.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            item {
                Text(
                    text = article.content.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                ReadArticleButton(onClick = { navigateToWebView(article) })

            }

        }
    })

    DisposableEffect(Unit) {
        onDispose {
            tts.release()
        }
    }
}

@Composable
fun ReadArticleButton(onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
    ) {
        Text("Read Complete Article", color = Color.White)
    }
}

fun handleButtonClick(
    tts: TextToSpeechHolder,
    buttonState: ButtonState,
    extractedText: String,
    setButtonState: (ButtonState) -> Unit,
    speed: Float,
) {
    if (tts.isInitialized()) {
        when (buttonState) {
            ButtonState.Play -> {
                tts.setSpeed(speed)
                tts.speak(extractedText)
                setButtonState(ButtonState.Stop)
            }

            ButtonState.Stop -> {
                tts.stop()
                setButtonState(ButtonState.Play)
            }
        }
    }
}


@Preview
@Composable
fun DetailsScreenPreview() {
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
    DetailsScreen(article = topArticle, navigateUp = {}, navigateToWebView = {})
}