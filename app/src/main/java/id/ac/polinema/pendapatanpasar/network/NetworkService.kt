package id.ac.polinema.pendapatanpasar.network
import ResultLogin
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetworkService {
    @FormUrlEncoded
    @POST("loginStaff")
    fun login(@Field("username") username : String?,
              @Field("password") password: String?) : Call<ResultLogin>
}