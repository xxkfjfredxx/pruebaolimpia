package com.fredrueda.pruebaandroid.ui.user

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fredrueda.pruebaandroid.R
import kotlinx.android.synthetic.main.fragment_finish.*

class FinishFragment : Fragment(R.layout.fragment_finish) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wifi.text = "WIFI esta " + checkForInternet(requireContext())
        bt.text = "Bluetooth  esta " + checkBt()
        btnsave.setOnClickListener {
            Toast.makeText(context, "guardado", Toast.LENGTH_LONG).show()
            requireActivity().finish()
        }
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun checkBt(): String {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return if(mBluetoothAdapter.isEnabled){
            "Activo"
        }else {
            "Desactivado"
        }
    }

}