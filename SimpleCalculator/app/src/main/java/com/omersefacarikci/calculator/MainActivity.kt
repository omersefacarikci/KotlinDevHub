package com.omersefacarikci.calculator

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.omersefacarikci.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btntemizle.setOnClickListener {
            binding.input.text = ""
            binding.output.text = ""
        }
        binding.btn0.setOnClickListener {
            binding.input.text = sayiekle("0")
        }
        binding.btn1.setOnClickListener {
            binding.input.text = sayiekle("1")
        }
        binding.btn2.setOnClickListener {
            binding.input.text = sayiekle("2")
        }
        binding.btn3.setOnClickListener {
            binding.input.text = sayiekle("3")
        }
        binding.btn4.setOnClickListener {
            binding.input.text = sayiekle("4")
        }
        binding.btn5.setOnClickListener {
            binding.input.text = sayiekle("5")
        }
        binding.btn6.setOnClickListener {
            binding.input.text = sayiekle("6")
        }
        binding.btn7.setOnClickListener {
            binding.input.text = sayiekle("7")
        }
        binding.btn8.setOnClickListener {
            binding.input.text = sayiekle("8")
        }
        binding.btn9.setOnClickListener {
            binding.input.text = sayiekle("9")
        }
        binding.btnnokta.setOnClickListener {
            binding.input.text = sayiekle(".")
        }
        binding.btncikar.setOnClickListener {
            binding.input.text = sayiekle("-")
        }
        binding.btntopla.setOnClickListener {
            binding.input.text = sayiekle("+")
        }
        binding.btncarp.setOnClickListener {
            binding.input.text = sayiekle("×")
        }
        binding.btnbol.setOnClickListener {
            binding.input.text = sayiekle("÷")
        }
        binding.btnesit.setOnClickListener {
            sonucgoster()
        }
        binding.btnyuzde.setOnClickListener {
            val ifade = binding.input.text.toString()
            if (ifade.isNotEmpty()) {
                try {
                    val expression = ExpressionBuilder(ifade).build()
                    val result = expression.evaluate() / 100
                    binding.output.text = result.toString()
                } catch (e: Exception) {
                    binding.output.text = "Hata"
                }
            }
        }
        binding.btnartieksi.setOnClickListener {
            val ifade = binding.input.text.toString()
            if (ifade.isNotEmpty()) {
                try {
                    val expression = ExpressionBuilder(ifade).build()
                    val result = expression.evaluate() * -1
                    binding.input.text = result.toString()
                } catch (e: Exception) {
                    binding.output.text = "Hata"
                }
            }
        }

    }

    fun sonucgoster(){
        val ifade = binding.input.text.toString()
            .replace("×", "*")
            .replace("÷", "/")

        try {
            val expression = ExpressionBuilder(ifade).build()
            val sonuc = expression.evaluate()
            binding.output.text = sonuc.toString()
        } catch (e: Exception) {
            binding.output.text = "Hata"
        }

    }
    fun sayiekle(buttondeger: String): String{
        return "${binding.input.text}$buttondeger"
    }
}