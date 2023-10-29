package com.example.escolhacaotica

import android.content.Context
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
        var prefsNumbers = getSharedPreferences("dataBaseNumbers", Context.MODE_PRIVATE)
        var prefsNumbersResult = prefsNumbers.getString("lastNumberSorted",null)
        if(prefsNumbersResult != null){
            txtResultNumbers.text = getString(R.string.txt_cards_result) + " " +  prefsNumbersResult
        }

        buttonNumbers.setOnClickListener {
            if(editMinimum.text.toString().isEmpty() || editMaximum.text.toString().isEmpty()){
                Toast.makeText(this,R.string.toast_cardNumbers_isEmpty,Toast.LENGTH_SHORT).show()
            }else {
                var random = Random()
                var min = editMinimum.text.toString().toInt()
                var max = editMaximum.text.toString().toInt()
                if (min > max) {
                    Toast.makeText(this, R.string.toast_cardNumbers_invalidMinimum, Toast.LENGTH_SHORT).show()
                } else {
                    var numberResult = random.nextInt(max - min + 1) + min
                    txtResultNumbers.text = numberResult.toString()
                }

                var editorNumbers = prefsNumbers.edit()
                editorNumbers.putString("lastNumberSorted",txtResultNumbers.text.toString())
                editorNumbers.apply()
            }
        }

        var editName: EditText = findViewById(R.id.input_cardNames_name)
        var buttonAdd: Button = findViewById(R.id.button_cardNames_add)
        var buttonSort: Button = findViewById(R.id.button_cardNames_sort)
        var buttonClear: Button = findViewById(R.id.button_cardNames_clear)
        var txtResultNames: TextView = findViewById(R.id.txt_cardNames_result)
        var listNames = mutableSetOf<String>()
        var prefsNames = getSharedPreferences("dataBaseNames", Context.MODE_PRIVATE)
        var prefsNamesResult = prefsNames.getString("lastNameSorted",null)
        if(prefsNamesResult != null){
            txtResultNames.text = getString(R.string.txt_cards_result) + " " +  prefsNamesResult
        }

        buttonAdd.setOnClickListener {
            var name = editName.text.toString()
            if(name.isEmpty()){
                Toast.makeText(this,R.string.toast_cardNames_isEmpty,Toast.LENGTH_SHORT).show()
            } else{
                listNames.add(name)
                editName.text = null
            }
        }

        buttonSort.setOnClickListener {
            if(listNames.size == 0) {
                Toast.makeText(this,R.string.toast_cardNames_isEmpty,Toast.LENGTH_SHORT).show()
            } else{
                txtResultNames.text = listNames.random()
                var editorNames = prefsNames.edit()
                editorNames.putString("lastNameSorted",txtResultNames.text.toString())
                editorNames.apply()
            }
        }

        buttonClear.setOnClickListener {
            if(listNames.size == 0){
                Toast.makeText(this,R.string.toast_cardNames_clear,Toast.LENGTH_SHORT).show()
            } else{
                listNames.clear()
                txtResultNames.text = null
            }
        }
    }
}