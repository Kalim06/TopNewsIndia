package com.mkd.topnewsindia.presentation.webPage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkd.topnewsindia.data.model.Article

@Composable
fun WebPage(article: Article) {

    val url = article.url

    Box(modifier = Modifier.fillMaxSize()) {
        WebViewComponent(url = url.toString())
    }

}


