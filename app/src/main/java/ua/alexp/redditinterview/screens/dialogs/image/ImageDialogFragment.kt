package ua.alexp.redditinterview.screens.dialogs.image

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_image_dialog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ua.alexp.redditinterview.R
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStream

class ImageDialogFragment(private val imageUrl: String) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.fragment_image_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImageIntoView(url = imageUrl)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            img_dlg_iv_save?.visibility = View.GONE
        }
    }

    private fun loadImageIntoView(url: String) {
        this.view.let {
            GlobalScope.launch(Dispatchers.IO) {
                if (it != null) {
                    val imageBitmap = Glide.with(it)
                        .asBitmap()
                        .load(url)
                        .submit()
                        .get()
                    showImage(imageBitmap)
                }
            }
        }
    }

    private fun showImage(imageBitmap: Bitmap?) {
        GlobalScope.launch(Dispatchers.Main) {
            if (imageBitmap != null) {
                img_dlg_iv_image?.setImageBitmap(imageBitmap)
                img_dlg_iv_save?.setOnClickListener {
                    try {
                        if (Build.VERSION.SDK_INT >= 29) {
                            saveImageHighSDK(bitmap = imageBitmap)
                        } else {
                            val directory = File(Environment.getExternalStorageDirectory().toString() + separator + "redditinterview")
                            // getExternalStorageDirectory is deprecated in API 29

                            if (!directory.exists()) {
                                directory.mkdirs()
                            }
                            val fileName = System.currentTimeMillis().toString() + ".jpg"
                            val file = File(directory, fileName)
                            saveImageToStream(imageBitmap, FileOutputStream(file))
                            if (file.absolutePath != null) {
                                val values = contentValues()
                                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                                // .DATA is deprecated in API 29
                                requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                            }
                        }
                        Toast.makeText(requireContext(), "File saved success", Toast.LENGTH_LONG).show()
                    }catch (e : Exception){
                        Toast.makeText(requireContext(), "File saving failed", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                img_dlg_iv_image?.setImageResource(R.drawable.ic_baseline_error_24)
                img_dlg_iv_save?.visibility = View.GONE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageHighSDK(bitmap: Bitmap) {
        val values = contentValues()
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "redditinterview")
        values.put(MediaStore.Images.Media.IS_PENDING, true)

        val uri: Uri? =
            requireContext().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )

        if (uri != null) {
            saveImageToStream(bitmap, requireContext().contentResolver.openOutputStream(uri))
            values.put(MediaStore.Images.Media.IS_PENDING, false)
            requireContext().contentResolver.update(uri, values, null, null)
        }
    }

    private fun contentValues(): ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}