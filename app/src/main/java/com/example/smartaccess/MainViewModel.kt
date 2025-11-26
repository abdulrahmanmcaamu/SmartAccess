package com.example.smartaccess

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartaccess.models.AppData
import com.example.smartaccess.models.ModuleModel
import com.example.smartaccess.models.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.Instant

data class UiState(
    val modules: List<ModuleModel> = emptyList(),
    val coolingActive: Boolean = false,
    val coolingText: String = "",
    val lastMessage: String? = null,
    val user: UserModel? = null
)

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var accessManager: AccessManager? = null

    init {
        viewModelScope.launch {
            loadMock()
            while (true) {
                val user = _uiState.value.user
                if (user != null) {
                    accessManager = AccessManager(user)
                    if (accessManager!!.isInCoolingPeriod()) {
                        _uiState.value = _uiState.value.copy(
                            coolingActive = true,
                            coolingText = accessManager!!.getCoolingCountdown()
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(coolingActive = false, coolingText = "")
                    }
                }
                delay(1000)
            }
        }
    }
    fun hasAccess(moduleId: String): Boolean = accessManager?.hasAccess(moduleId) ?: false

    private fun loadMock() {
        val am = getApplication<Application>().assets.open("mock_data.json")
        val json = BufferedReader(InputStreamReader(am)).use { it.readText() }
        val data = Gson().fromJson(json, AppData::class.java)
        _uiState.value = _uiState.value.copy(
            modules = data.modules,
            user = data.user
        )
        accessManager = AccessManager(data.user)
        if (accessManager!!.isInCoolingPeriod()) {
            _uiState.value = _uiState.value.copy(
                coolingActive = true,
                coolingText = accessManager!!.getCoolingCountdown()
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onModuleClick(module: ModuleModel) {
        val am = accessManager ?: return
        val msg = when {
            am.isInCoolingPeriod(Instant.now()) -> "Access denied: cooling period"
            !am.hasAccess(module.id) -> "Access denied: no permission"
            else -> "Navigating to ${module.title}"
        }
        _uiState.value = _uiState.value.copy(lastMessage = msg)

        viewModelScope.launch {
            while (isActive) {
                delay(1000L)
            }
            _uiState.value = _uiState.value.copy(lastMessage = null)
        }
    }
}
