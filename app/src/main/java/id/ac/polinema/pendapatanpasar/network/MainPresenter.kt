package id.ac.polinema.pendapatanpasar.network

import Pendapatan
import Response
import ResponseList
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import java.io.File

object MainPresenter {

    const val INPUT_BUKTI_SETOR = "INPUT_FOR_BUKTI_SETOR"
    const val INPUT_BUKTI_STS = "INPUT_FOR_STS"

    fun save(
        id: Int, realisasi: Int,
        buktiSetor: File, sts: File,
        callback: (status: Boolean) -> Unit
    ) {
        val requestBuktiSetor: RequestBody = RequestBody.create(
            "image/*".toMediaTypeOrNull(), buktiSetor
        )

        val requestSTS: RequestBody = RequestBody.create(
            "image/*".toMediaTypeOrNull(), sts
        )

        val partFileBuktiSetor = MultipartBody.Part
            .createFormData("bukti_setor", buktiSetor.name, requestBuktiSetor)

        val partFileSts = MultipartBody.Part
            .createFormData("sts", buktiSetor.name, requestSTS)

        NetworkConfig.getService()
            .updateRealisasi(id, realisasi, partFileBuktiSetor, partFileSts)
            .enqueue(object: Callback<Response<Boolean>> {
                override fun onFailure(call: Call<Response<Boolean>>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<Response<Boolean>>,
                    response: retrofit2.Response<Response<Boolean>>
                ) {
                    callback(response.body()?.data ?: false)
                }
            })
    }
}