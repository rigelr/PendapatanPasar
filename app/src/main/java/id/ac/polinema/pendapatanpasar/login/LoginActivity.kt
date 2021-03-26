package id.ac.polinema.pendapatanpasar.login

import ResultLogin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.ac.polinema.pendapatanpasar.R
import id.ac.polinema.pendapatanpasar.MainActivity
import id.ac.polinema.pendapatanpasar.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick

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

    fun onSuccessLogin(data: ResultLogin?) {
        Toast.makeText(this, "Berhasil Login", Toast.LENGTH_SHORT).show()
        Intent(this, MainActivity::class.java).apply {
            putExtra("DATA_MAIN", data)
            startActivity(this)
        }
    }

     fun onFailedLogin(msg: String?){
        alert {
            title = "Information"
            message = "Login Failed Silahkan Cek Email Password"
        }.show()
    }
}