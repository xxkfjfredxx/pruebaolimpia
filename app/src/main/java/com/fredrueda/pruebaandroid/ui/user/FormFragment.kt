package com.fredrueda.pruebaandroid.ui.user

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.fred.prueba.data.AppDatabase
import com.fredrueda.pruebaandroid.R
import com.fredrueda.pruebaandroid.models.DataBook
import com.fredrueda.pruebaandroid.utils.SharedPref
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.fragment_form.*


class FormFragment : Fragment(R.layout.fragment_form) {

    var latitud = ""
    var longitud = ""
    private var mViewModel: MyViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SmartLocation.with(context).location().oneFix().start {
            latitud = it.latitude.toString()
            longitud = it.longitude.toString()
        }
    }

    fun savedata() {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "pruebaFredDatabase"
        ).build()

        val dataBook = DataBook()
        dataBook.nombre = name.text.toString()
        dataBook.cedula = cedula.text.toString()
        dataBook.direccion = direccion.text.toString()
        dataBook.ciudad = ciudad.text.toString()
        dataBook.pais = pais.text.toString()
        dataBook.celular = celular.text.toString()
        dataBook.isVisible = true
        dataBook.foto = SharedPref.getData(requireContext(),"foto")
        dataBook.lat = latitud
        dataBook.long = longitud
        AsyncTask.execute { // Insert Data
            db.dao().insert(dataBook)
        }

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        ViewModelProvider(requireActivity()).get(MyViewModel::class.java).message!!.observe(
            requireActivity(),
            {
                //Toast.makeText(context, "llega el numero : " + it, Toast.LENGTH_LONG).show()
                if (it == 3) {
                    savedata()
                }
            })
    }

}