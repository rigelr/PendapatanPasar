package id.ac.polinema.pendapatanpasar

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import id.ac.polinema.pendapatanpasar.network.MainPresenter.INPUT_BUKTI_SETOR
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

var photoFile: File? = null
var photoUri: Uri? = null
var inputFor: String? = null

fun AlertDialog.Builder.chooseAction(activity: Activity, input: String) {
    inputFor = input

    setCancelable(false)
    arrayOf("Take Photo", "Choose from Library", "Cancel").also {
        setItems(it) { dialog, i ->
            when(it[i]) {
                "Take Photo" -> context.requestStoragePermission(activity) {
                    photoUri = activity.accessCamera(
                        if (input == INPUT_BUKTI_SETOR) REQUEST_ACCESS_CAMERA_FOR_BUKTI_SETOR
                        else REQUEST_ACCESS_CAMERA_FOR_STS
                    )
                }

                "Choose from Library" -> context.requestStoragePermission(activity) {
                    activity.accessGalery(
                        if (input == INPUT_BUKTI_SETOR) REQUEST_ACCESS_GALERY_FOR_BUKTI_SETOR
                        else REQUEST_ACCESS_GALERY_FOR_STS
                    )
                }

                "Cancel" -> {
                    dialog.dismiss()
                    activity.finish()
                }
            }
        }
    }

    show()
}

@Throws(IOException::class)
fun Context.getBitmap(activity: Activity, uri: Uri): Bitmap? {
    val cursor = contentResolver.query(
        uri, null, null, null, null
    )

    val sizeIndex = cursor?.getColumnIndex(OpenableColumns.SIZE)
    cursor?.moveToFirst()
    val size = sizeIndex?.let { cursor.getLong(it) }

    if (size ?: 0 > 5000000) {
        Toast.makeText(this, "Image is too large, Please change!",
            Toast.LENGTH_LONG).show()
        return null
    }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(contentResolver, uri)
        )
    } else {
        contentResolver.openInputStream(uri).use {
            BitmapFactory.decodeStream(it)
        }
    }
}

@Throws(IOException::class)
fun File.convertBitmapToFile(bitmap: Bitmap) {
    createNewFile()
    ByteArrayOutputStream().use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, it)
        FileOutputStream(this).apply {
            write(it.toByteArray())
            flush()
            close()
        }
    }
}
