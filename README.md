****activity_main.xml****

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fillViewport="true">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="24dp">

            <!-- Header Catatan -->
            <TextView
                android:id="@+id/tvTitle"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="CATATAN HARIANKU"
                android:textSize="28sp"
                android:textStyle="bold"
                android:textColor="#5D4037"
                android:gravity="center"
                android:layout_marginBottom="24dp" />

            <!-- Tanggal Catatan -->
            <TextView
                android:id="@+id/tvCurrentDate"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Selasa, 22 April 2025"
                android:textSize="16sp"
                android:textColor="#795548"
                android:gravity="right"
                android:layout_marginBottom="16dp"/>

            <!-- Isi Catatan -->
            <EditText
                android:id="@+id/etDiaryContent"
                android:layout_width="match_parent"
                android:layout_height="400dp"
                android:hint="Tulis perasaan dan pikiranmu hari ini..."
                android:inputType="textMultiLine"
                android:textSize="16sp"
                android:textColor="#3E2723"
                android:background="@android:color/transparent"
                android:padding="12dp"
                android:gravity="top|left"
                android:textStyle="italic"/>

            <!-- Mood Hari Ini -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                android:layout_marginTop="16dp"
                android:gravity="center">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Mood hari ini:"
                    android:textSize="16sp"
                    android:textColor="#5D4037"/>

            </LinearLayout>

            <!-- Tombol Simpan -->
            <Button
                android:id="@+id/btnSave"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Simpan Catatan"
                android:layout_marginTop="24dp"
                android:backgroundTint="#5D4037"
                android:textColor="@android:color/white"
                android:textStyle="bold"/>

            <!-- Garis Pembatas -->
            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#BCAAA4"
                android:layout_marginVertical="16dp"/>

            <!-- Catatan Sebelumnya -->
            <TextView
                android:id="@+id/tvPreviousNotes"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Catatan Sebelumnya"
                android:textSize="14sp"
                android:textColor="#795548"
                android:layout_marginBottom="8dp"/>

            <Button
                android:id="@+id/btn_date_picker"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Pilih Tanggal"
                android:layout_marginTop="16dp"/>

            <Button
                android:id="@+id/btn_show_alert"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Hapus Catatan"
                android:layout_marginTop="8dp"
                android:backgroundTint="#FF5252"
                android:textColor="@android:color/white"/>

            <EditText
                android:id="@+id/etPhone"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Nomor Telepon (Opsional)"
                android:inputType="phone"
                android:layout_marginTop="16dp"
                android:drawableStart="@android:drawable/ic_menu_call"/>

        </LinearLayout>
    </ScrollView>

****MainActivity****

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


**Tampilannya**

![image](https://github.com/user-attachments/assets/89ca0fe4-3f7a-4419-9768-e22af6698a19)
