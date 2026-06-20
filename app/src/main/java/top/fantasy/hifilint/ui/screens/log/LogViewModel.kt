package top.fantasy.hifilint.ui.screens.log

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import top.fantasy.hifilint.repository.LogRepository
import javax.inject.Inject

@HiltViewModel
class LogViewModel @Inject constructor(
    private val logRepository: LogRepository
) : ViewModel(){
    val logs = logRepository.logs
}