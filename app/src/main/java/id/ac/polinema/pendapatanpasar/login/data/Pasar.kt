import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pasar(
    @field:SerializedName("nama_pasar")
    val pasarNama: String? = null,
    @field:SerializedName("id_pasar")
    val pasarId: String? = null,
    @field:SerializedName("nama_op")
    val pasarNamaop: String? = null,
    @field:SerializedName("no_telp")
    val pasarNotelp: String? = null,
    @field:SerializedName("alamat")
    val pasarAlamat: String? = null,
    @field:SerializedName("username")
    val pasarUsername: String? = null,
    @field:SerializedName("password")
    val pasarPassword: String? = null
): Parcelable

@Parcelize
data class Pendapatan(
    @SerializedName("id_pendapatan")
    val id: Int,
    val target: Int,
    val tanggal: String,
    val realisasi: Int,
    @SerializedName("bukti_setor")
    val buktiSetor: String,
    val status: Int
): Parcelable