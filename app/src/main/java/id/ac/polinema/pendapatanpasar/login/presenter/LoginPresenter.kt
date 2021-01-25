package id.ac.polinema.pendapatanpasar.login.presenter
import ResultLogin
import id.ac.polinema.pendapatanpasar.login.LoginActivity
import id.ac.polinema.pendapatanpasar.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(val loginView: LoginActivity) {
    fun login(username : String, password : String){
        NetworkConfig.getService()
            .login(username,password)
            .enqueue(object : Callback<ResultLogin>{
                override fun onFailure(call: Call<ResultLogin>, t: Throwable) {
                    loginView.onFailedLogin(t.localizedMessage)
                }
                override fun onResponse(call: Call<ResultLogin>, response: Response<ResultLogin>) {
                    if (response.isSuccessful && response.body()?.status == 200){
                        loginView.onSuccessLogin(response.body()?.message, response.body()?.pasar)
                    } else {
                        loginView.onFailedLogin(response.body()?.message)
                    }
                }
            })
    }
}