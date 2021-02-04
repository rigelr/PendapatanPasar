package id.ac.polinema.pendapatanpasar.network

import Pendapatan
import Response
import ResponseList
import ResultLogin
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    @FormUrlEncoded
    @POST("loginStaff")
    fun login(@Field("username") username : String?,
              @Field("password") password: String?) : Call<ResultLogin>

    @FormUrlEncoded
    @GET("getDataPendapatan")
    fun getDataPendapatan(@Field("id") id: Int): Call<ResponseList<Pendapatan>>

    @Multipart
    @POST("updatePendapatan")
    fun updateRealisasi(
        @Part("id_pendapatan") id: Int,
        @Part("realisasi") relasasi: Int,
        @Part bukti: MultipartBody.Part,
        @Part sts: MultipartBody.Part
    ): Call<Response<Boolean>>
}