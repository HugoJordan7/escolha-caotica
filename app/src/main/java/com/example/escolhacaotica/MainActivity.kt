package com.example.escolhacaotica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.escolhacaotica.databinding.ActivityMainBinding
import com.example.escolhacaotica.model.PreferencesModel
import java.util.*

class MainActivity : AppCompatActivity(), Observer {

    private lateinit var model: PreferencesModel

    private val random = Random()
    private lateinit var binding: ActivityMainBinding
    private val listNames = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = PreferencesModel(this)
        model.addObserver(this)
        binding.cardNumbers.apply {
            txtResult.text = model.getLastNumberSorted()
            buttonSorted.setOnClickListener { numberButtonResultListener() }
        }
        binding.cardNames.apply {
            txtResult.text = model.getLastNameSorted()
            buttonAdd.setOnClickListener { namesButtonAddListener() }
            buttonClear.setOnClickListener { namesButtonClearListener() }
            buttonSort.setOnClickListener { namesButtonSortListener() }
        }
    }

    private fun numberButtonResultListener() {
        binding.cardNumbers.apply {
            if (editMinimum.text.toString().isEmpty() || editMaximum.text.toString().isEmpty()) {
                Toast.makeText(this@MainActivity, R.string.toast_cardNumbers_isEmpty, Toast.LENGTH_SHORT).show()
                return
            }
            val min = editMinimum.text.toString().toInt()
            val max = editMaximum.text.toString().toInt()
            if (min > max) {
                Toast.makeText(this@MainActivity, R.string.toast_cardNumbers_invalidMinimum, Toast.LENGTH_SHORT).show()
                return
            }
            val numberResult = random.nextInt(max - min + 1) + min
            model.setLastNumberSorted(PreferencesModel.NUMBERS_DB, numberResult.toString())
        }
    }

    private fun namesButtonAddListener() {
        binding.cardNames.editName.text.apply {
            if (toString().isEmpty()) {
                Toast.makeText(this@MainActivity, R.string.toast_cardNames_isEmpty, Toast.LENGTH_SHORT).show()
            } else {
                listNames.add(toString())
                null
            }
        }
    }

    private fun namesButtonClearListener() {
        if (listNames.size == 0) {
            Toast.makeText(this, R.string.toast_cardNames_clear, Toast.LENGTH_SHORT).show()
        } else {
            listNames.clear()
            binding.cardNames.txtResult.text = null
        }
    }

    private fun namesButtonSortListener() {
        if (listNames.size == 0) {
            Toast.makeText(this@MainActivity, R.string.toast_cardNames_isEmpty, Toast.LENGTH_SHORT).show()
        } else {
            model.setLastNameSorted(PreferencesModel.NAMES_DB, listNames.random())
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is PreferencesModel) {
            val newPair = arg as Pair<String, String>
            val key = newPair.first
            val value = newPair.second
            if (key == PreferencesModel.NUMBERS_DB) {
                binding.cardNumbers.txtResult.text = value
            } else {
                binding.cardNames.txtResult.text = value
            }
        }
    }
}