package space.rodionov.chucknorris.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import space.rodionov.chucknorris.data.Joke

interface ChuckNorrisApi {

    companion object {
        const val BASE_URL = "https://api.icndb.com/"
    }

    @GET("jokes/random/{count}")
    suspend fun getJokes(
        @Path("count") count: Int
    ): ChuckNorrisResponse
}





