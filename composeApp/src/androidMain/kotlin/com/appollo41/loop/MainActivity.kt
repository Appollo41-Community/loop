package com.appollo41.loop

import App
import NostrExample
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.appollo41.loop.db.getDatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = getDatabaseBuilder(applicationContext).getDao()
        setContent {
            NostrExample(dao = dao)
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    NostrExample()
//}