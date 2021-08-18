package space.rodionov.chucknorris.ui.web

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private const val TAG = "WebViewModel LOGS"

@HiltViewModel
class WebViewModel @Inject constructor(
    private val state: SavedStateHandle
) : ViewModel() {
    var curUrl = state.getLiveData("curUrl", "https://www.icndb.com/api/")
    var inputUrl = state.getLiveData("inputUrl", "https://www.icndb.com/api/")


    private val _urlFlow = curUrl.asFlow().flatMapLatest {
        MutableStateFlow(it)
    }
    val urlFlow = _urlFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)


    fun submitUrl() {
        curUrl.value = inputUrl.value
        Log.d(TAG, "submitUrl: \n curUrl = ${curUrl.value} \n inputUrl = ${inputUrl.value}")
    }

    fun setCurUrl(url: String?) {
        curUrl.value = url
        Log.d(TAG, "setCurUrl: $url")
    }

    fun setInputUrl(url: String?) {
        inputUrl.value = url
        Log.d(TAG, "setInputUrl: $url")
    }
}





