package top.fantasy.hifilint.repository

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogRepository @Inject constructor() {
    private val _logs = MutableStateFlow<List<String>>(emptyList())
    val logs: StateFlow<List<String>> = _logs.asStateFlow()

    fun addLog(message: String){
        _logs.update { currentLogs ->
            (currentLogs + message).takeLast(1000)
        }
        Log.d("LogRepository", "addLog: $message")
    }

    fun clearLogs(){
        _logs.value = emptyList()
    }
}