package com.example.array

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {
    val SHARED_PREFS = "sharedPrefs"
    val TEXT = "text"
    val STR = "str"

    val CHECKED = "checked"

    companion object {

        var text: String? = "1"
        var checked = 0
        val SWITCH1 = "switch1"
        var switchOnOff = false
        var str: String? = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val editText: EditText = findViewById(R.id.editText)
        val applyTextButton: Button = findViewById(R.id.button)
        val saveButton: Button = findViewById(R.id.button2)
        val btn = findViewById<Switch>(R.id.switch1)
        val rad: RadioGroup = findViewById(R.id.radioGroup)
        val messageView: TextView = findViewById(R.id.textView)

        var selectedOption: Int
        var radioButton: RadioButton

        val btn1: RadioButton = findViewById(R.id.radio_one)
        val btn2: RadioButton = findViewById(R.id.radio_two)
        val btn3: RadioButton = findViewById(R.id.radio_three)

        applyTextButton.setOnClickListener {


            closeKeyboard()
        }
        btn.setOnCheckedChangeListener { _, isChecked ->

            if (btn.isChecked) {

                btn.text = "Disable dark mode"
            } else {

                btn.text = "Enable dark mode"
            }
        }

        saveButton.setOnClickListener {


            selectedOption = rad.checkedRadioButtonId
            radioButton = findViewById(selectedOption)


            Toast.makeText(baseContext, radioButton.toString(), Toast.LENGTH_SHORT).show()


            if (editText.text.isNotEmpty()) {
                title = editText.text.toString()
            }
            val sharedPreferences: SharedPreferences =
                getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(TEXT, title.toString())

            editor.putBoolean(SWITCH1, btn.isChecked)
            editor.putString(STR, radioButton.text.toString())
            editor.putInt(CHECKED, selectedOption)
            editor.apply()

            text = title.toString()
            str = radioButton.text.toString()
            openApp()
            Toast.makeText(this, "Settings applied", Toast.LENGTH_SHORT).show()


        }


        title = text



        btn.isChecked = switchOnOff


        loadData()

        selectedOption = checked
        if (str == "English" || str == "Engleski" || str == "Inglés") {
            rad.check(R.id.radio_one)
        } else if (str == "Serbian" || str == "Srpski" || str == "Serbio") {
            rad.check(R.id.radio_two)
            val context = LocaleHelper.setLocale(this, "sr")
            val resources = context.getResources()
            messageView.setText(resources.getString(R.string.languages))
            btn.setText(resources.getString(R.string.enable_dark_mode))
            editText.setHint(resources.getString(R.string.change_title))
            saveButton.setText(resources.getString(R.string.apply_settings))
            btn1.setText(resources.getString(R.string.english))
            btn2.setText(resources.getString(R.string.serbian))
            btn3.setText(resources.getString(R.string.spanish))
            Toast.makeText(this, "Srpski", Toast.LENGTH_SHORT).show()
        } else if (str == "Spanish" || str == "Spanski" || str == "Español") {
            rad.check(R.id.radio_three)
            val context = LocaleHelper.setLocale(this, "es")
            val resources = context.getResources()
            messageView.setText(resources.getString(R.string.languages))
            btn.setText(resources.getString(R.string.enable_dark_mode))
            editText.setHint(resources.getString(R.string.change_title))
            saveButton.setText(resources.getString(R.string.apply_settings))
            btn1.setText(resources.getString(R.string.english))
            btn2.setText(resources.getString(R.string.serbian))
            btn3.setText(resources.getString(R.string.spanish))
            Toast.makeText(this, "Espanol", Toast.LENGTH_SHORT).show()
        }

    }

    fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun loadData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        text = sharedPreferences.getString(TEXT, "")
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false)
        str = sharedPreferences.getString(STR, "")
        checked = sharedPreferences.getInt(CHECKED, -1)

    }

    fun openApp() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun openInfo() {
        val intent = Intent(this, Info::class.java)
        startActivity(intent)
    }

    fun openHelp() {
        val intent = Intent(this, Help::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu2, menu)
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
            R.id.item4 -> {
                openInfo()
                Toast.makeText(this, "Item 4 selected", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

}
