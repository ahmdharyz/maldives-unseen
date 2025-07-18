package com.example.maldivesunseen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.maldivesunseen.ui.MaldivesUnseenApp
import com.example.maldivesunseen.ui.theme.MaldivesUnseenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaldivesUnseenTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MaldivesUnseenTheme {
                        MaldivesUnseenApp(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaldivesUnseenTheme {
        MaldivesUnseenApp()
    }
}