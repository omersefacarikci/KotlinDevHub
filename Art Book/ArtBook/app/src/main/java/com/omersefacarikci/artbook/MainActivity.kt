package com.omersefacarikci.artbook

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.omersefacarikci.artbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var artList : ArrayList<Artım>
    private lateinit var artAdapter : ArtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar2)
        artList = ArrayList<Artım>()
        artAdapter = ArtAdapter(artList)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = artAdapter
        try {
            val database = this.openOrCreateDatabase("Sanatlar",MODE_PRIVATE,null)
            val cursor =database.rawQuery("SELECT * FROM sanatlar",null)
            val artnameindex = cursor.getColumnIndex("artname")
            val idindex = cursor.getColumnIndex("id")
            while (cursor.moveToNext()){
                val name = cursor.getString(artnameindex)
                val id = cursor.getInt(idindex)
                val art = Artım(name,id)
                artList.add(art)


            }
            //verisetimiz değişti güncelle hemen demek istiyoruz.
            artAdapter.notifyDataSetChanged()
            cursor.close()


        }
        catch (e: Exception){
            e.printStackTrace()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //inflater
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.art_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Tıklanınca ne olacak
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_art_item){
            //Diğer sayfaya yönlendir
            val intent = Intent(this@MainActivity, ArtActivity::class.java)
            intent.putExtra("bilgi","yeni")
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}