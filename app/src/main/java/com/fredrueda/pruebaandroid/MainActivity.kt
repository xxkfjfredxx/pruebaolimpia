package com.fredrueda.pruebaandroid

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.fred.prueba.data.AppDatabase
import com.fredrueda.pruebaandroid.models.DataBook
import com.fredrueda.pruebaandroid.ui.adapters.PostAdapter
import com.fredrueda.pruebaandroid.ui.user.RegisterActivity
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.awaitAll
import java.io.File

class MainActivity : AppCompatActivity() {

    var permissionValid = false
    private var postAdapter: PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPermission()
        createFolder()
        initObjects()

    }

    private fun createFolder() {
        val folder = File(
            Environment.getExternalStorageDirectory().toString().toString()
                    + "/imgsubir/"
        )
        if (!folder.exists()) {
            Log.e("Crea", "folder foto")
            folder.mkdir()
        }
    }

    private fun getPermission() {
        val permissionLocation: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionCamera: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permissionLocation == PackageManager.PERMISSION_GRANTED && permissionCamera == PackageManager.PERMISSION_GRANTED) {
            permissionValid = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA
                ),
                200
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            200 -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                permissionValid = true
            } else {
                Toast.makeText(
                    this,
                    "Para mejorar la experiencia en la app se necesita permisos",
                    Toast.LENGTH_LONG
                ).show()
                // Permission Denied
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun initObjects() {
        this.postAdapter = PostAdapter(applicationContext)
        this.rcvPost.layoutManager = LinearLayoutManager(applicationContext)
        this.rcvPost.setHasFixedSize(false)
        this.rcvPost.isNestedScrollingEnabled = false
        this.rcvPost.descendantFocusability = ScrollView.FOCUS_BEFORE_DESCENDANTS
        this.rcvPost.swapAdapter(this.postAdapter, true)
        this.rcvPost.adapter = this.postAdapter
        SmartLocation.with(this).location().oneFix().start {
            Log.e("Lat", "" + it.latitude)
        }
        button.setOnClickListener {
            if (permissionValid) {
                startActivity(Intent(this, RegisterActivity::class.java))
            } else {
                getPermission()
            }
        }
    }

    private fun initRecyclerView(leagues: List<DataBook>) {
        this.postAdapter?.setData(leagues)
    }

    private fun loadNewData() {
        try {
            val db = Room.databaseBuilder(this, AppDatabase::class.java, "pruebaFredDatabase").build()
            AsyncTask.execute { // Insert Data
                initRecyclerView(db.dao().getAllPosts())
            }
        } catch (e: Exception) {

        }
    }

    override fun onResume() {
        super.onResume()
        loadNewData()
        this.postAdapter?.notifyDataSetChanged()
    }
}