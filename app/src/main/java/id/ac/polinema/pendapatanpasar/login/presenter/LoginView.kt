package id.ac.polinema.pendapatanpasar.login.presenter

import Pasar

interface LoginView {
    fun onSuccessLogin (msg : String?, data : Pasar?)
    fun onFailedLogin (msg : String?)
}