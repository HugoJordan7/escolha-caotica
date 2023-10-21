package com.example.escolhacaotica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var editMinimum: EditText = findViewById(R.id.input_cardNumbers_minimum)
        var editMaximum: EditText = findViewById(R.id.input_cardNumbers_maximum)
        var buttonNumbers: Button = findViewById(R.id.button_cardNumbers)
        var txtResultNumbers: TextView = findViewById(R.id.txt_cardNumbers_result)

        buttonNumbers.setOnClickListener {
            var random = Random()
            var min = editMinimum.text.toString().toInt()
            var max = editMaximum.text.toString().toInt()
            if(min>max){
                Toast.makeText(this,"O valor mínimo deve ser menor que o máximo!",Toast.LENGTH_LONG).show()
            } else{
                var numberResult = random.nextInt(max-min+1) + min
                txtResultNumbers.text = numberResult.toString()
            }

        }
    }

    fun clickButtonNumbers(button: Button){

    }
}