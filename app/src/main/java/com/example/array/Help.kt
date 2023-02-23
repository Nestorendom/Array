package com.example.array

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks
import org.w3c.dom.Text

class Help : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        title = Settings.text
        val mTextView = findViewById<TextView>(R.id.text_view_1)
        val googleLink = Link("Help")
            .setTextColor(Color.BLUE)
            .setUnderlined(true)
            .setBold(false)
            .setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                startActivity(i)
            }

        mTextView.applyLinks(googleLink)
        if (Settings.str == "Serbian" || Settings.str == "Srpski" || Settings.str == "Serbio") {
            val context = LocaleHelper.setLocale(this, "sr")
            val resources = context.getResources()

            mTextView.setText("Otvori Pomoc")
            val serbianGoogleLink = Link("Pomoc")
                .setTextColor(Color.BLUE)
                .setUnderlined(true)
                .setBold(false)
                .setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                    startActivity(i)
                }
            mTextView.applyLinks(serbianGoogleLink)
        } else if (Settings.str == "Spanish" || Settings.str == "Spanski" || Settings.str == "Español") {
            val context = LocaleHelper.setLocale(this, "es")
            val resources = context.getResources()
            mTextView.setText("Abierto Ayuda")
            val spanishGoogleLink = Link("Ayuda")
                .setTextColor(Color.BLUE)
                .setUnderlined(true)
                .setBold(false)
                .setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                    startActivity(i)
                }
            mTextView.applyLinks(spanishGoogleLink)
        }
    }

    fun openApp() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun openInfo() {
        val intent = Intent(this, Info::class.java)
        startActivity(intent)
    }

    fun openSettings() {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu3, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.item3 -> {
                openApp()
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show()
                true

            }
            R.id.item1 -> {
                openSettings()
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.item4 -> {
                openInfo()
                Toast.makeText(this, "Item 4 selected", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}