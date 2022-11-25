package de.palm.composestateeventslib.sample.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.sample.extensions.collectAsStateLifecycleAware

@Composable
fun FlowerScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        val viewModel: FlowerScreenViewModel = viewModel()
        val viewState: FlowerScreenViewState by viewModel.stateStream.collectAsStateLifecycleAware()
        val context = LocalContext.current

        Log.d("FlowerScreenViewState", viewState.toString())

        EventEffect(
            event = viewState.downloadSucceededEvent,
            onConsumed = viewModel::onConsumedDownloadSucceededEvent,
        ) {
            scaffoldState.snackbarHostState.showSnackbar("Success")
        }

        EventEffect(
            event = viewState.downloadFailedEvent,
            onConsumed = viewModel::onConsumedDownloadFailedEvent,
        ) { stringRes ->
            scaffoldState.snackbarHostState.showSnackbar(context.getString(stringRes))
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.loadFlowersWithSuccess() }
        ) {
            Text("Load flowers with success")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.loadFlowersWithFailure() }
        ) {
            Text("Load flowers with failure")
        }

        LazyColumn {
            items(viewState.flowers.size) { index ->
                Text(viewState.flowers[index].toString())
            }
        }
    }
}
