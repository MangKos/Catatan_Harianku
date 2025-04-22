package com.example.nugas_bro3

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var etDiaryContent: EditText
    private lateinit var tvCurrentDate: TextView
    private lateinit var btnSave: Button
    private lateinit var tvPreviousNotes: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnDate = findViewById<Button>(R.id.btn_date_picker) // Pastikan button sudah ditambahkan di XML
        btnDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                val selectedDate = "$day/${month+1}/$year"
                tvCurrentDate.text = "Tanggal: $selectedDate" // Update TextView tanggal
                Toast.makeText(this, "Tanggal dipilih: $selectedDate", Toast.LENGTH_SHORT).show()
            },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etDiaryContent = findViewById(R.id.etDiaryContent)
        tvCurrentDate = findViewById(R.id.tvCurrentDate)
        btnSave = findViewById(R.id.btnSave)
        tvPreviousNotes = findViewById(R.id.tvPreviousNotes)

        setCurrentDate()

        btnSave.setOnClickListener {
            saveDiaryEntry()


        }
    }

    private fun setCurrentDate() {
        val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        tvCurrentDate.text = currentDate
    }

    private fun saveDiaryEntry() {
        val content = etDiaryContent.text.toString().trim()

        if (content.isEmpty()) {
            Toast.makeText(this, "Silakan tulis catatan terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Catatan berhasil disimpan!", Toast.LENGTH_SHORT).show()

        tvPreviousNotes.text = "Catatan terakhir:\n${SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())} - $content"

        etDiaryContent.text.clear()
    }

    private fun showAlert() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Yakin ingin menghapus catatan?")
            .setPositiveButton("Ya") { _, _ ->
                etDiaryContent.text.clear()
                Toast.makeText(this, "Catatan dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    companion object {
        private fun saveDiaryEntry(mainActivity: MainActivity) {
            val phone = mainActivity.findViewById<EditText>(R.id.etPhone).text.toString()
            if (phone.isNotEmpty() && !Patterns.PHONE.matcher(phone).matches()) {
                Toast.makeText(mainActivity, "Format nomor tidak valid", Toast.LENGTH_SHORT).show()
                return
            }
            // ... lanjutkan penyimpanan
        }
    }
}