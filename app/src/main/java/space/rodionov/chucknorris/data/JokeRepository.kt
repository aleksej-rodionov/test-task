package space.rodionov.chucknorris.data

import android.util.Log
import space.rodionov.chucknorris.api.ChuckNorrisApi
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "Repo LOGS"

@Singleton
class JokeRepository @Inject constructor(
    private val api: ChuckNorrisApi
) {

    suspend fun getJokes(count: Int) : List<Joke> {
        val list = api.getJokes(count).value
        Log.d(TAG, "list = $list")
        return list
    }
}





