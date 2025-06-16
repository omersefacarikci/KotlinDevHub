package com.omersefacarikci.artbook

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.omersefacarikci.artbook.databinding.ActivityArtBinding
import java.io.ByteArrayOutputStream

class ArtActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var selectedbitmap : Bitmap? = null
    private lateinit var database : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = this.openOrCreateDatabase("Sanatlar",MODE_PRIVATE,null)
        Launcher()
        val intent = intent
        val info = intent.getStringExtra("bilgi")
        if (info.equals("yeni")){
            binding.imageView.setImageResource(R.drawable.select)
            binding.button.visibility = View.VISIBLE

        }
        else{
            binding.button.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id",1)
            val cursor = database.rawQuery("SELECT * FROM sanatlar WHERE id = ?",arrayOf(selectedId.toString()))
            val artnameix = cursor.getColumnIndex("artname")
            val artistnameix = cursor.getColumnIndex("artistname")
            val yearix = cursor.getColumnIndex("year")
            val imageix = cursor.getColumnIndex("image")

            while (cursor.moveToNext()){
                binding.artnametext.setText(cursor.getString(artnameix))
                binding.artnametext2.setText(cursor.getString(artistnameix))
                binding.artnametext3.setText(cursor.getString(yearix))

                val bytearray = cursor.getBlob(imageix)
                val bitmap = BitmapFactory.decodeByteArray(bytearray,0,bytearray.size)
                binding.imageView.setImageBitmap(bitmap)
            }
            cursor.close()


        }

    }
    fun kaydet(view: View){
        //sqllitea görseli kaydetmek için küçültmemiz gerekiyor.
        val artname = binding.artnametext.text.toString()
        val artistname = binding.artnametext2.text.toString()
        val year = binding.artnametext3.text.toString()
        if (selectedbitmap != null){
            val small = kucukbitmap(selectedbitmap!!,300)
            val outputstream = ByteArrayOutputStream()
            small.compress(Bitmap.CompressFormat.PNG,50,outputstream)
            val bytearray = outputstream.toByteArray()
            try {

                database.execSQL("CREATE TABLE IF NOT EXISTS sanatlar (id INTEGER PRIMARY KEY, artname VARCHAR , artistname VARCHAR, year VARCHAR,image BLOB)")
                //imageleri blob olarak kaydedecez.

                val stringim = "INSERT INTO sanatlar (artname,artistname,year,image) VALUES (?,?,?,?)"
                val statment = database.compileStatement(stringim)
                statment.bindString(1,artname)
                statment.bindString(2,artistname)
                statment.bindString(3,year)
                statment.bindBlob(4,bytearray)
                statment.execute()

            }
            catch (e: Exception){
                e.printStackTrace()
            }
            // mainactivitye geri dönmek için bir finish çalıştırabiliriz.
            //finish()
            //birde intent kullanabiliriz.
            val intent = Intent(this@ArtActivity, MainActivity::class.java) //mainactivitye geri dön ama
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)// önceki tüm activityleri kapat
            startActivity(intent)
        }

    }
    //burada görseli kaydedeceğiz ama küçük halde
    private fun kucukbitmap(image: Bitmap,maxiumumSize: Int) : Bitmap{
        var width = image.width
        var height = image.height
        val bitmapRatio : Double = width.toDouble() / height.toDouble()
        if (bitmapRatio > 1){
            // yatay görsel
            width = maxiumumSize
            val scaleHeight = width / bitmapRatio
            height = scaleHeight.toInt()
        }
        // dikey görsel
        else{
            height = maxiumumSize
            val scaleWidth = height * bitmapRatio
            width = scaleWidth.toInt()
        }

        return Bitmap.createScaledBitmap(image,width,height,true)
    }
    fun resimclick(view: View){
        //API 33 VE YUKARI İÇİN READ_MEDİA_IMAGES GELDİ BU YÜZDEN BUNU KOTNROL EDELİM İF DE ELSE DE İSE ALTI İÇİN READ_EXTERNAL_STORAGE KULLANACAĞIZ.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            //izin kontrol edip alacağız.
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(view,"Galeriye gitmek için izin gerekli!", Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver",
                        View.OnClickListener{
                            //izin istyiecez
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)

                        }).show()
                }
                else{  // izin istiyecez
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }

            }
            else{
                //galeriye git
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intent)

            }
        }
        else{
            //izin kontrol edip alacağız.
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Snackbar.make(view,"Galeriye gitmek için izin gerekli!", Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver",
                        View.OnClickListener{
                            //izin istyiecez
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

                        }).show()
                }
                else{  // izin istiyecez
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

            }
            else{
                //galeriye git
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intent)

            }
        }


    }
    private fun Launcher(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK){
                val intent = result.data
                if (intent !=null){
                    val imagedata = intent.data
                    if(imagedata != null){
                        try {
                            if (Build.VERSION.SDK_INT >= 28){
                                val source = ImageDecoder.createSource(this@ArtActivity.contentResolver,imagedata)
                                selectedbitmap = ImageDecoder.decodeBitmap(source)
                                binding.imageView.setImageBitmap(selectedbitmap)
                            } else{
                                selectedbitmap = MediaStore.Images.Media.getBitmap(contentResolver,imagedata)
                                binding.imageView.setImageBitmap(selectedbitmap)
                            }
                        }
                        catch (e : Exception){
                            e.printStackTrace()
                        }
                    }


                }

            }
        }
        //izin isteme
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {result ->
            //izin verildi
            if(result){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intent)
            }
            //izin verilmedi
            else{
                Toast.makeText(this@ArtActivity,"Permission needed!", Toast.LENGTH_LONG).show()
            }
        }
    }
}