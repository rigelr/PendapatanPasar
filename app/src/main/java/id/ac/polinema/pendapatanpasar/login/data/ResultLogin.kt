import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ResultLogin(
    @SerializedName("staff")
    val pasar: Pasar? = null,
    val pendapatan: List<Pendapatan> = emptyList(),
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("status")
    val status: Int? = null
): Parcelable

data class Response<T> (
    val status: Int,
    val data: T
)

data class ResponseList<T> (
    val status: Int,
    val data: List<T>
)