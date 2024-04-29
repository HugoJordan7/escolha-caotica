package com.example.escolhacaotica.model

import android.content.Context
import java.util.*

class PreferencesModel(context: Context) : Observable() {

    companion object{
        const val NUMBERS_DB = "dataBaseNumbers"
        const val NAMES_DB = "dataBaseNames"
    }

    private val prefsNumbers = context.getSharedPreferences(NUMBERS_DB,Context.MODE_PRIVATE)
    private val prefsNames = context.getSharedPreferences(NAMES_DB,Context.MODE_PRIVATE)

    fun setLastNumberSorted(key: String, value: String){
        val editorNumbers = prefsNumbers.edit()
        editorNumbers.putString(key,value)
        editorNumbers.apply()
        setChanged()
        notifyObservers(Pair(key,value))
    }

    fun setLastNameSorted(key: String, value: String){
        val editorNames = prefsNames.edit()
        editorNames.putString(key,value)
        editorNames.apply()
        setChanged()
        notifyObservers(Pair(key,value))
    }

    fun getLastNumberSorted(): String?{
        return prefsNumbers.getString(NUMBERS_DB,null)
    }

    fun getLastNameSorted(): String?{
        return prefsNames.getString(NAMES_DB,null)
    }
}