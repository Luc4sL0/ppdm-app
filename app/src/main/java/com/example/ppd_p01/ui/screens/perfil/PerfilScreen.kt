package com.example.ppd_p01.ui.screens.perfil

import PerfilViewModelFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ppd_p01.R
import com.example.ppd_p01.data.local.AppDatabase
import com.example.ppd_p01.data.repository.HabitRepositoryImpl
import com.example.ppd_p01.domain.repository.HabitRepository
import com.example.ppd_p01.ui.components.AppDrawer
import com.example.ppd_p01.ui.components.AppHeader
import com.example.ppd_p01.ui.screens.home.HabitState
import com.example.ppd_p01.ui.screens.home.components.HabitItem
import com.example.ppd_p01.ui.screens.perfil.components.HistoryItem
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(navController: NavController, userId: Int, userEmail: String) {

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val dao = db.habitDao()
    val repository: HabitRepository = HabitRepositoryImpl(dao)
    val viewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModelFactory(repository, userId)
    )


    val uiState = viewModel.uiState
    val level = viewModel.level
    val points = viewModel.totalPoints
    val stars = viewModel.stars


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


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
                AppHeader(
                    title = "Perfil",
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onNotificationClick = { scope.launch { drawerState.open() } }
                )
            },
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = userEmail,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )


                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .background(Color(0xFFEAF3E8), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(colorResource(id = R.color.green_primary), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Nível $level",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    repeat(stars) {
                        Text(text = "★", fontSize = 22.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$points Pontos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Histórico",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                when (uiState) {
                    is HabitState.Success -> {
                        Column{
                            uiState.habits.forEach { habit ->
                                HabitItem(habit)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    is HabitState.Empty -> {
                        Text("Nenhum hábito encontrado")
                    }

                    is HabitState.Error -> {
                        Text("Erro: ${uiState.message}", color = Color.Red)
                    }

                    HabitState.Loading -> {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
