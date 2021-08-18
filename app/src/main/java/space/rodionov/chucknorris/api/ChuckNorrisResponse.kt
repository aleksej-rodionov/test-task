package space.rodionov.chucknorris.api

import space.rodionov.chucknorris.data.Joke

data class ChuckNorrisResponse(
    val value: List<Joke>
) {
}