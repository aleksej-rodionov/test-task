package space.rodionov.chucknorris.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Joke(
    @SerializedName("joke")
    val text: String
) : Parcelable {
}