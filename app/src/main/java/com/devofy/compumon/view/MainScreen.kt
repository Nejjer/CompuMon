import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devofy.compumon.view.PCStatusScreen
import com.devofy.compumon.viewmodels.PCStatusViewModel
import com.devofy.compumon.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val settings: SettingsViewModel = viewModel()
    settings.loadServerAddresses()

    val pcViewModel = PCStatusViewModel(settings.pcServer.value, 500)
    val selfServerViewModel = PCStatusViewModel(settings.selfHostedServer.value, 500)
    val remoteServerViewModel = PCStatusViewModel(settings.remoteServer.value, 500)

    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf("PC 1", "PC 2", "PC 3")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Server Status") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // TabRow для переключения между вкладками
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            // Содержимое для текущей вкладки
            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedTab) {
                    0 -> PCStatusScreen(viewModel = pcViewModel)
                    1 -> PCStatusScreen(viewModel = selfServerViewModel)
                    2 -> PCStatusScreen(viewModel = remoteServerViewModel)
                }
            }
        }
    }
}
