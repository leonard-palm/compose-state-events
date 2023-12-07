package de.palm.composestateeventslib.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.palm.composestateeventslib.sample.ui.flower.FlowerScreen
import de.palm.composestateeventslib.sample.ui.flower_detail.FlowerDetailScreen

class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            MaterialTheme {

                val scaffoldState: ScaffoldState = rememberScaffoldState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    content = { innerPadding ->

                        NavHost(
                            navController = navController,
                            startDestination = "flowers"
                        ) {
                            composable(route = "flowers") {
                                FlowerScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    scaffoldState = scaffoldState,
                                    navController = navController
                                )
                            }
                            composable(route = "details") {
                                FlowerDetailScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    navController = navController
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
