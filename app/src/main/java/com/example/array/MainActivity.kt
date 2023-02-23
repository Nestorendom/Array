package com.example.array


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.service.autofill.Validators.or
import android.text.TextUtils
import android.view.Display
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.isDigitsOnly
import com.example.array.Settings.Companion.SWITCH1
import com.example.array.Settings.Companion.str
import com.example.array.Settings.Companion.switchOnOff
import com.example.array.Settings.Companion.text
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


class MainActivity : AppCompatActivity() {
    val SHARED_PREFS = "sharedPrefs"
    val TEXT = "text"
    val STRA = "str"
    var prev: String? = ""


    fun getAverage(list: List<Int>): Double {
        return list.average()

    }

    fun openSettings() {
        val intent = Intent(this, Settings::class.java)
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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val niz: EditText = findViewById(R.id.niz)
        val swButton: Button = findViewById(R.id.swButton)
        val avgButton: Button = findViewById(R.id.avgButton)
        val orButton: Button = findViewById(R.id.orButton)
        val result: TextView = findViewById(R.id.result)
        var qrim: ImageView = findViewById(R.id.imageView2)
        var generateQRBtn: Button = findViewById(R.id.button3)

        generateQRBtn.setOnClickListener {
            val data = niz.text.toString().trim()
            if (data.isEmpty()) {
                Toast.makeText(this, "Enter something", Toast.LENGTH_SHORT).show()
            } else {
                val writer = QRCodeWriter()
                try {
                    val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                    for (x in 0 until width) {
                        for (y in 0 until height) {
                            bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    qrim.setImageBitmap(bmp)
                } catch (e: WriterException) {
                    e.printStackTrace()
                }
            }
        }

        val sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.apply()
        loadData()

        prev = str
        title = text
        b()
        if (prev == "Serbian" || Settings.str == "Srpski" || Settings.str == "Serbio") {
            val context = LocaleHelper.setLocale(this, "sr")
            val resources = context.getResources()
            niz.setHint("Unesi niz")
            orButton.setText("Sortiraj")
            swButton.setText("Zameni")
            avgButton.setText("Prosek")
            generateQRBtn.setText("Kr Kod")
            result.setText("Rezultat")
        } else if (str == "Spanish" || str == "Spanski" || str == "Español") {
            val context = LocaleHelper.setLocale(this, "sr")
            val resources = context.getResources()
            niz.setHint("Introduce una cadena")
            orButton.setText("Clasificar")
            swButton.setText("Cambiar")
            avgButton.setText("Promedio")
            generateQRBtn.setText("Código QR")
            result.setText("El resultado")
        }
        fun inputIsNotEmpty(): Boolean {
            var b = true;
            if (niz.text.toString().trim().isEmpty()) {
                niz.error = "Required"
                niz.requestFocus()
                b = false;
            }

            return b;
        }

        fun inputIsNotNumber(): Boolean {
            var b = true;
            if (!niz.text.isDigitsOnly()) {
                niz.error = "Only numbers"
                niz.requestFocus()
                b = false;
            }

            return b;
        }

        fun switch() {

            if (inputIsNotEmpty()) {
                var inputdata = niz.text.toString()
                result.text = inputdata.toString().reversed()

            }

        }


        fun swapArray(a: CharArray, b: Int, c: Int) {
            val temp = a[b]
            a[b] = a[c]
            a[c] = temp
        }

        fun partition(array: CharArray, l: Int, r: Int): Int {
            var left = l
            var right = r
            val pivot = array[(left + right) / 2]
            while (left <= right) {
                while (array[left] < pivot) left++

                while (array[right] > pivot) right--


                if (left <= right) {
                    swapArray(array, left, right)
                    left++
                    right--
                }
            }
            return left
        }

        fun quickSort(array: CharArray, left: Int, right: Int) {
            val index = partition(array, left, right)
            if (left < index - 1) {
                quickSort(array, left, index - 1)
            }
            if (index < right) {
                quickSort(array, index, right)
            }
        }

        fun qSort() {
            if (inputIsNotEmpty()) {
                var inputdata = niz.text.toString().toCharArray()
                quickSort(inputdata, 0, inputdata.size - 1)
                result.text = inputdata.joinToString("")

            }
        }

        fun merge(array: CharArray, helper: CharArray, low: Int, middle: Int, high: Int) {

            for (i in low..high) helper[i] = array[i]

            var helperLeft = low
            var helperRight = middle + 1
            var current = low



            while (helperLeft <= middle && helperRight <= high) {
                if (helper[helperLeft] <= helper[helperRight]) {
                    array[current] = helper[helperLeft]
                    helperLeft++
                } else {
                    array[current] = helper[helperRight]
                    helperRight++
                }
                current++
            }


            val remaining = middle - helperLeft
            for (i in 0..remaining) {
                array[current + i] = helper[helperLeft + i]
            }

        }

        fun mergeSort(
            array: CharArray,
            helper: CharArray = CharArray(array.size),
            low: Int = 0,
            high: Int = array.size - 1
        ) {
            if (low < high) {
                val middle: Int = (low + high) / 2
                mergeSort(array, helper, low, middle)
                mergeSort(array, helper, middle + 1, high)
                merge(array, helper, low, middle, high)
            }
        }

        fun mSort() {
            if (inputIsNotEmpty()) {
                var inputdata = niz.text.toString().toCharArray()
                mergeSort(inputdata)
                result.text = inputdata.joinToString("")
            }
        }

        fun bubbleSortWithSteps(numbers: CharArray) {

            for (pass in 0 until (numbers.size - 1)) {

                for (currentPosition in 0 until (numbers.size - pass - 1)) {

                    if (numbers[currentPosition] > numbers[currentPosition + 1]) {

                        val tmp = numbers[currentPosition]
                        numbers[currentPosition] = numbers[currentPosition + 1]
                        numbers[currentPosition + 1] = tmp
                    }

                }
            }

        }

        fun bSort() {
            if (inputIsNotEmpty()) {
                var inputdata = niz.text.toString().toCharArray()
                bubbleSortWithSteps(inputdata)
                result.text = inputdata.joinToString("")
            }
        }

        fun insertionSort(arr: CharArray) {
            val lastIndex: Int = arr.size - 1

            for (i in 1..lastIndex) {
                val temp: Char = arr[i]
                var holePosition: Int = i
                while (holePosition > 0 && arr[holePosition - 1] > temp) {
                    arr[holePosition] = arr[holePosition - 1]
                    holePosition--
                }
                arr[holePosition] = temp
            }
        }

        fun iSort() {
            if (inputIsNotEmpty()) {
                var inputdata = niz.text.toString().toCharArray()
                insertionSort(inputdata)
                result.text = inputdata.joinToString("")
            }
        }

        fun avg() {
            if (inputIsNotEmpty() && inputIsNotNumber()) {
                var input = niz.text.map { it.digitToInt() }
                val avge = getAverage(input)
                result.text = avge.toString()

            }

        }

        swButton.setOnClickListener {
            switch()
        }
        avgButton.setOnClickListener { avg() }
        orButton.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, orButton)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.quick ->
                        qSort()

                    R.id.merge ->
                        mSort()
                    R.id.bubble ->
                        bSort()
                    R.id.insertion ->
                        iSort()
                }
                true
            })
            popupMenu.show()
        }


    }

    fun loadData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)

        text = sharedPreferences.getString(TEXT, "")
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false)

        str = sharedPreferences.getString(STRA, "")

    }


    fun b() {
        if (switchOnOff == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menuu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.item1 -> {
                openSettings()

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
