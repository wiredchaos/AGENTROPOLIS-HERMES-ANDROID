package io.agentropolis.hermex

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.agentropolis.hermex.data.HermesRepository
import io.agentropolis.hermex.data.SecureStore
import io.agentropolis.hermex.ui.HermexApp
import io.agentropolis.hermex.ui.theme.HermexTheme
import io.agentropolis.hermex.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val secureStore = SecureStore(this)
        val repository = HermesRepository(secureStore)
        val viewModel = AppViewModel(repository)

        setContent {
            HermexTheme {
                var pendingUploadEndpoint by remember { mutableStateOf<String?>(null) }
                val filePicker = rememberLauncherForActivityResult(
                    ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    val endpoint = pendingUploadEndpoint
                    if (uri != null && endpoint != null) {
                        viewModel.uploadFile(contentResolver, uri, endpoint)
                    }
                    pendingUploadEndpoint = null
                }

                HermexApp(
                    viewModel = viewModel,
                    onPickFile = { endpoint ->
                        pendingUploadEndpoint = endpoint
                        filePicker.launch("*/*")
                    }
                )
            }
        }
    }
}
