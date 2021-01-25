import com.google.gson.annotations.SerializedName


data class ResultLogin(
    @field:SerializedName("pasar")
    val pasar: Pasar? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("status")
    val status: Int? = null
)