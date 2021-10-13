package com.fredrueda.pruebaandroid.ui.user

import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.fredrueda.pruebaandroid.BuildConfig
import com.fredrueda.pruebaandroid.R
import com.fredrueda.pruebaandroid.utils.GetImageThumbnail
import com.fredrueda.pruebaandroid.utils.SharedPref
import kotlinx.android.synthetic.main.takephotofragment.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException


class TakePhotosFragment : Fragment(R.layout.takephotofragment) {

    val REQUEST_CODE = 200
    private var output: File? = null
    private val FILENAME = "CameraContentDemo.jpg"
    private val AUTHORITY: String = BuildConfig.APPLICATION_ID + ".fileprovider"
    private val PHOTOS = "photos"
    private var outputUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.photo.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    @Throws(IOException::class)
    private fun dispatchTakePictureIntent() {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        output = File(File(requireContext().filesDir, PHOTOS), FILENAME)
        if (output!!.exists()) {
            output?.delete()
        } else {
            output?.parentFile?.mkdirs()
        }
        outputUri = FileProvider.getUriForFile(
            requireContext(),
            AUTHORITY,
            output!!
        )
        i.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        } else {
            val clip = ClipData.newUri(requireContext().contentResolver, "A photo", outputUri)
            i.clipData = clip
            i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
        startActivityForResult(i, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                var bitmap: Bitmap? = null
                val getImageThumbnail = GetImageThumbnail()
                try {
                    SharedPref.saveData(requireContext(),"foto",outputUri.toString())
                    bitmap = getImageThumbnail.getThumbnail(outputUri, context)
                } catch (e: FileNotFoundException) {
                    Log.e("NO FOUND", "" + e.toString())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                photo.setImageBitmap(bitmap)
            }
        }

    }
}