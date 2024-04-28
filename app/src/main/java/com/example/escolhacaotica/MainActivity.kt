package com.example.escolhacaotica

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.escolhacaotica.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefsNumbers: SharedPreferences
    private lateinit var prefsNames: SharedPreferences
    var listNames = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var prefsNumbers = getSharedPreferences("dataBaseNumbers", Context.MODE_PRIVATE)
        var prefsNumbersResult = prefsNumbers.getString("lastNumberSorted",null)
        if(prefsNumbersResult != null){
            binding.cardNumbers.txtResult.text = getString(R.string.txt_cards_result) + " " +  prefsNumbersResult
        }

        prefsNames = getSharedPreferences("dataBaseNames", Context.MODE_PRIVATE)
        var prefsNamesResult = prefsNames.getString("lastNameSorted",null)
        if(prefsNamesResult != null){
            binding.cardNames.txtResult.text = getString(R.string.txt_cards_result) + " " + prefsNamesResult
        }

        binding.cardNumbers.buttonSorted.setOnClickListener(this)
        binding.cardNames.buttonAdd.setOnClickListener(this)
        binding.cardNames.buttonClear.setOnClickListener(this)
        binding.cardNames.buttonSort.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.cardNumbers.buttonSorted.id -> numberButtonResultListener()
            binding.cardNames.buttonAdd.id -> namesButtonAddListener()
            binding.cardNames.buttonClear.id -> namesButtonClearListener()
            binding.cardNames.buttonSort.id -> namesButtonSortListener()
        }
    }


    private fun numberButtonResultListener(){
        binding.cardNumbers.apply {
            if(binding.cardNumbers.editMinimum.text.toString().isEmpty() ||
                binding.cardNumbers.editMaximum.text.toString().isEmpty()){
                Toast.makeText(this@MainActivity,R.string.toast_cardNumbers_isEmpty,Toast.LENGTH_SHORT).show()
                return
            }
            var random = Random()
            var min = editMinimum.text.toString().toInt()
            var max = editMaximum.text.toString().toInt()
            if (min > max) {
                Toast.makeText(this@MainActivity, R.string.toast_cardNumbers_invalidMinimum, Toast.LENGTH_SHORT).show()
            } else {
                var numberResult = random.nextInt(max - min + 1) + min
                txtResult.text = numberResult.toString()
            }
            var editorNumbers = prefsNumbers.edit()
            editorNumbers.putString("lastNumberSorted",txtResult.text.toString())
            editorNumbers.apply()
        }
    }

    private fun namesButtonAddListener(){
        binding.cardNames.editName.text.apply {
            if(toString().isEmpty()){
                Toast.makeText(this@MainActivity,R.string.toast_cardNames_isEmpty,Toast.LENGTH_SHORT).show()
            } else{
                listNames.add(toString())
                null
            }
        }
    }

    private fun namesButtonClearListener(){
        if(listNames.size == 0){
            Toast.makeText(this,R.string.toast_cardNames_clear,Toast.LENGTH_SHORT).show()
        } else{
            listNames.clear()
            binding.cardNames.txtResult.text = null
        }
    }

    private fun namesButtonSortListener(){
        binding.cardNames.txtResult.text.apply {
            if (listNames.size == 0) {
                Toast.makeText(this@MainActivity, R.string.toast_cardNames_isEmpty, Toast.LENGTH_SHORT).show()
            } else {
                listNames.random()
                var editorNames = prefsNames.edit()
                editorNames.putString("lastNameSorted", toString())
                editorNames.apply()
            }
        }
    }
}