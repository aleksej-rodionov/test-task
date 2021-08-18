package space.rodionov.chucknorris.ui.jokes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.chucknorris.data.Joke
import space.rodionov.chucknorris.data.JokeRepository
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val repo: JokeRepository,
    private val state: SavedStateHandle
) : ViewModel() {
    var countValue = state.get<Int>("count") ?: 0
        set(value) {
            field = value
            state.set("count", value)
        }

    var jokeList= mutableListOf<Joke>()

    private val _jokesFlow = MutableStateFlow(jokeList.toList())
    val jokesFlow = _jokesFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun getJokes(count: Int) {
        viewModelScope.launch {
            _jokesFlow.value = repo.getJokes(count)
        }
    }
}





