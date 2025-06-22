package com.omersefacarikci.retrofitcryptoapp.view

import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omersefacarikci.retrofitcryptoapp.databinding.ActivityMainBinding
import com.omersefacarikci.retrofitcryptoapp.modal.CryptoModel
import com.omersefacarikci.retrofitcryptoapp.service.CryptoAPI
import com.omersefacarikci.retrofitcryptoapp.adapter.CryptoAdapter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), com.omersefacarikci.retrofitcryptoapp.adapter.CryptoAdapter.Listener {
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var crptoModals : ArrayList<CryptoModel>? = null
    private lateinit var recyclerViewAdapter: CryptoAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)

        binding.recyclerView.layoutManager = layoutManager
        loadData()

    }
    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object: Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>?>,
                response: Response<List<CryptoModel>?>
            ) {
                if (response.isSuccessful){
                    response.body()?.let{
                        crptoModals = ArrayList(it)
                        crptoModals?.let {
                            recyclerViewAdapter = CryptoAdapter(it, this@MainActivity)
                            binding.recyclerView.adapter = recyclerViewAdapter

                        }


                    }
                }
            }

            override fun onFailure(
                call: Call<List<CryptoModel>?>,
                t: Throwable
            ) {
                t.printStackTrace()
            }

        })

    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }
}