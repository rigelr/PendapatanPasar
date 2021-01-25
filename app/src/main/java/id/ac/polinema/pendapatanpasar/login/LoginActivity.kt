package id.ac.polinema.pendapatanpasar.login

import Pasar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.ac.polinema.pendapatanpasar.MainActivity
import id.ac.polinema.pendapatanpasar.R
import id.ac.polinema.pendapatanpasar.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var presenter: LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)


        btnLogin.onClick {
            val username = loginEmail.text.toString()
            val password = loginPassword.text.toString()
            presenter.login(username, password)
        }

    }

    fun onSuccessLogin(msg: String?, data: Pasar?) {
        alert {
            title = "Information"
            message = "Login Success"
        }.show()
        startActivity<MainActivity>()
        finish()

    }

     fun onFailedLogin(msg: String?){
        alert {
            title = "Information"
            message = "Login Failed Silahkan Cek Email Password"
        }.show()
    }

}