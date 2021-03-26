package id.ac.polinema.pendapatanpasar

import ResultLogin
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.ac.polinema.pendapatanpasar.network.MainPresenter
import id.ac.polinema.pendapatanpasar.network.MainPresenter.INPUT_BUKTI_SETOR
import id.ac.polinema.pendapatanpasar.network.MainPresenter.INPUT_BUKTI_STS
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.imageBitmap
import java.io.File

class MainActivity : AppCompatActivity() {

    private var btn: Button? = null
    private var imageview: ImageView? = null
    private var btn2: Button? = null
    private var imageview2: ImageView? = null
    private var fileBuktiSetor: File? = null
    private var fileSts: File? = null

    private lateinit var datas: ResultLogin
    private lateinit var dates: Array<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datas = intent.getParcelableExtra("DATA_MAIN")
        if (datas.pendapatan.size > 0) {
            Log.i(
                MainActivity::class.simpleName,
                "onCreate: getting data from intent = $datas"
            )

            dates = arrayOfNulls(datas.pendapatan.size)
            datas.pendapatan.forEachIndexed { index, element ->
                Log.i(
                    MainActivity::class.simpleName,
                    "onCreate: index = $index element = ${element.tanggal}"
                )

                dates.set(index, element.tanggal)
            }

            datas = intent.getParcelableExtra("DATA_MAIN")
            Log.i(
                MainActivity::class.simpleName,
                "onCreate: getting data from intent = $dates"
            )

            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, dates).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                tvNamaOp.adapter = this
            }

            btn = findViewById<View>(R.id.btnCamera) as Button
            imageview = findViewById<View>(R.id.iv) as ImageView
            btn2 = findViewById<View>(R.id.btnCamera2) as Button
            imageview2 = findViewById<View>(R.id.iv2) as ImageView

            tvNamaPasar.setText(datas.pasar?.pasarNama ?: "Pasar Tradisional")
            btn!!.setOnClickListener {
                AlertDialog.Builder(this)
                    .chooseAction(this, INPUT_BUKTI_SETOR)
            }

            btn2!!.setOnClickListener {
                AlertDialog.Builder(this)
                    .chooseAction(this, INPUT_BUKTI_STS)
            }
        }

        btnSubmit.setOnClickListener {
            var date = tvNamaOp.selectedItem.toString()
            val id = fun (): Int {
                var result = -1
                datas.pendapatan.forEach {
                    if (it.tanggal == date) {
                        result = it.id
                    }
                } // end val date

                return result
            }; // end val id

            val realisasi = edtRealisasi.text.toString()
            var message: String

            Log.i(MainActivity::class.simpleName, "onCreate: id = ${ id() }")
            Log.i(MainActivity::class.simpleName, "onCreate: realisasi = $realisasi")

            if (fileBuktiSetor != null && fileSts != null && realisasi.isNotEmpty() && id() != -1) {
                MainPresenter.save(id(), realisasi.toInt(), fileBuktiSetor!!, fileSts!!) {
                    message =
                        if (it) "Berhasil menyimpan data"
                        else "Terjadi kesalahan saat menyimpan data"

                    edtRealisasi.setText("")
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                    finish()
                }
            } else {
                message = "Mohon lengkapi semua data"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i(MainActivity::class.simpleName, "onActivityResult: requestCode: $requestCode")
        Log.i(MainActivity::class.simpleName,
            "onActivityResult: resultCode: $resultCode and ${ resultCode == RESULT_OK }")

        if (resultCode != RESULT_OK) return

        val imageUri = when (requestCode) {
            REQUEST_ACCESS_GALERY_FOR_STS -> data?.data
            REQUEST_ACCESS_GALERY_FOR_BUKTI_SETOR -> data?.data
            else -> photoUri
        }?.let { uri ->
            getBitmap(this, uri)?.let { bitmap ->
                val file = File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "${ System.currentTimeMillis() }_selectedImg.jpg"
                )

                if (inputFor == INPUT_BUKTI_STS) {
                    fileSts = file
                    fileSts?.convertBitmapToFile(bitmap)

                    iv2.imageBitmap = bitmap
                } else {
                    fileBuktiSetor = file
                    fileBuktiSetor?.convertBitmapToFile(bitmap)

                    iv.imageBitmap = bitmap
                }
            } // let bitmap
        } // let Uri
    } // let when
}