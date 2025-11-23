package com.example.ppd_p01.ui.screens.home

import com.example.ppd_p01.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ppd_p01.data.local.AppDatabase
import com.example.ppd_p01.data.repository.HabitRepositoryImpl
import com.example.ppd_p01.domain.model.HabitStatus
import com.example.ppd_p01.domain.repository.HabitRepository
import com.example.ppd_p01.ui.components.AppDrawer
import com.example.ppd_p01.ui.components.AppHeader
import com.example.ppd_p01.ui.screens.home.components.AddHabitDialog
import com.example.ppd_p01.ui.screens.home.components.HabitSection
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, userId: Int) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val dao = db.habitDao()
    val repository: HabitRepository = HabitRepositoryImpl(dao)
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(repository, userId)
    )
    val uiState = viewModel.uiState

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AddHabitDialog(
            onDismiss = { showDialog = false },
            onConfirm = { habit ->
                viewModel.addHabit(habit)
            },
            userId
        )
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onNavigate = { route -> navController.navigate(route) },
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppHeader (
                    title = "Meus Hábitos",
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onNotificationClick = { scope.launch { drawerState.open() } }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = colorResource(id = R.color.green_primary)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Novo hábito")
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hoje",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "10/09/2025",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    when (uiState) {
                        is HabitState.Loading -> CircularProgressIndicator()
                        is HabitState.Empty -> Text("Nenhum hábito encontrado")
                        is HabitState.Error -> Text("Erro: ${uiState.message}", color = MaterialTheme.colorScheme.error)
                        is HabitState.Success -> {
                            val habits = uiState.habits

                            val upcoming = habits.filter { it.status == HabitStatus.UPCOMING }
                            if (upcoming.isNotEmpty()) {
                                HabitSection(
                                    title = "Em breve",
                                    habits = upcoming,
                                    onStatusChange = { habit, newStatus ->
                                        viewModel.updateHabitStatus(habit.id, newStatus)
                                    }
                                )
                                Spacer(Modifier.height(16.dp))
                            }

                            val pending = habits.filter { it.status == HabitStatus.PENDING }
                            if (pending.isNotEmpty()) {
                                HabitSection(
                                    title = "Pendentes",
                                    habits = pending,
                                    onStatusChange = { habit, newStatus ->
                                        viewModel.updateHabitStatus(habit.id, newStatus)
                                    }
                                )
                                Spacer(Modifier.height(16.dp))
                            }

                            val completed = habits.filter { it.status == HabitStatus.COMPLETED }
                            if (completed.isNotEmpty()) {
                                HabitSection(
                                    title = "Concluídos",
                                    habits = completed,
                                    onStatusChange = { habit, newStatus ->
                                        viewModel.updateHabitStatus(habit.id, newStatus)
                                    }
                                )
                                Spacer(Modifier.height(16.dp))
                            }
                        }
                    }

                }
            }
        }
    }
}
