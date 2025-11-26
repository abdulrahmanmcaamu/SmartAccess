package com.example.smartaccess

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartaccess.ui.ModuleCard
import com.example.smartaccess.ui.theme.SmartAccessTheme

class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartAccessTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val state by vm.uiState.collectAsState()

                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)) {
                        TopAppBar(title = { Text("Demo: Module Access UI") })

                        // Cooling banner
                        if (state.coolingActive) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                                Text(state.coolingText)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Module list
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            state.modules.forEach { module ->
                                val allowed = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    vm.hasAccess(module.id)
                                } else {
                                    TODO("VERSION.SDK_INT < O")
                                }
                                ModuleCard(module = module, enabled = allowed) {
                                    vm.onModuleClick(module)
                                }
                            }
                        }

                        // Transient message
                        // add imports at top: import androidx.compose.runtime.LaunchedEffect
                        state.lastMessage?.let { msg ->
                            LaunchedEffect(msg) {
                                // show Android toast
                                android.widget.Toast.makeText(this@MainActivity, msg, android.widget.Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
            }
        }
    }
}
