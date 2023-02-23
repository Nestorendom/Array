package com.example.array

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.array.Settings.Companion.str

class Info : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val info: TextView = findViewById(R.id.textView2)
        if (str == "Serbian" || Settings.str == "Srpski" || Settings.str == "Serbio") {
            val context = LocaleHelper.setLocale(this, "sr")
            val resources = context.getResources()
            info.setText("Ova aplikacija omogućava svojim korisnicima da unesu niz i izvrše različite operacije na njemu.\n")
        } else if (str == "Spanish" || str == "Spanski" || str == "Español") {
            val context = LocaleHelper.setLocale(this, "sr")
            val resources = context.getResources()
            info.setText("Esta aplicación permite a sus usuarios ingresar una matriz y realizar diferentes operaciones en ella.")
        }
        title = Settings.text
    }

    fun openApp() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun openSettings() {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
    }

    fun openHelp() {
        val intent = Intent(this, Help::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu4, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.item3 -> {
                openApp()
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show()
                true

            }
            R.id.item2 -> {
                openHelp()
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.item1 -> {
                openSettings()
                Toast.makeText(this, "Item 4 selected", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}