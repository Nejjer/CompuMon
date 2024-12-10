import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devofy.compumon.SettingActivity
import com.devofy.compumon.view.PCStatusScreen
import com.devofy.compumon.viewmodels.PCStatusViewModel
import com.devofy.compumon.viewmodels.SettingsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    val settings: SettingsViewModel = viewModel()
    settings.loadServerAddresses()
    val context = LocalContext.current

    val pcViewModel = PCStatusViewModel(settings.pcServer.value, 500)
    val selfServerViewModel = PCStatusViewModel(settings.selfHostedServer.value, 500)
    val remoteServerViewModel = PCStatusViewModel(settings.remoteServer.value, 500)

    val tabs = listOf("PC", "SelfHosted", "Remote")

    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("CompuMon")
                        IconButton(onClick = {
                            context.startActivity(
                                Intent(
                                    context,
                                    SettingActivity::class.java
                                )
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu, // Встроенная иконка "меню"
                                contentDescription = "Menu",
                                tint = Color.Black // Цвет иконки
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Tabs
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }

            // Swipeable Pager
            HorizontalPager(
                count = tabs.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> PCStatusScreen(viewModel = pcViewModel)
                    1 -> PCStatusScreen(viewModel = selfServerViewModel)
                    2 -> PCStatusScreen(viewModel = remoteServerViewModel)
                }
            }
        }
    }
}
