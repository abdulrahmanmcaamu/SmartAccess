package com.example.smartaccess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartaccess.ui.theme.SmartAccessTheme
import com.example.smartaccess.ui.components.ModuleCard

class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

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
                                ModuleCard(module = module, onClick = {
                                    vm.onModuleClick(module)
                                })
                            }
                        }

                        // Transient message
                        state.lastMessage?.let { msg ->
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(msg)
                        }
                    }
                }
            }
        }
    }
}
