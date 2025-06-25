package com.omersefacarikci.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.omersefacarikci.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var timestop : Long = 0
        binding.buttonstart.setOnClickListener {
            //sistem saatini alacağız.
            binding.kronometre.base = SystemClock.elapsedRealtime()+timestop
            binding.kronometre.start()
            binding.buttonstart.visibility = View.GONE
            binding.buttonpause.visibility = View.VISIBLE
            binding.imageView.setImageDrawable(getDrawable(R.drawable.pause))

        }
        binding.buttonpause.setOnClickListener {
            timestop = binding.kronometre.base - SystemClock.elapsedRealtime()
            binding.kronometre.stop()
            binding.buttonstart.visibility = View.VISIBLE
            binding.buttonpause.visibility = View.GONE
            binding.imageView.setImageDrawable(getDrawable(R.drawable.start))
        }
        binding.buttonreset.setOnClickListener {
            binding.kronometre.base = SystemClock.elapsedRealtime()
            binding.kronometre.stop()
            timestop = 0
            binding.buttonstart.visibility = View.VISIBLE
            binding.buttonpause.visibility = View.GONE
            binding.imageView.setImageDrawable(getDrawable(R.drawable.start))

        }
    }
}