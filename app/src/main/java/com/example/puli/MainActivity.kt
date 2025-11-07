package com.example.puli

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.puli.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var date1: Date? = null
        var date2: Date? = null

        binding.btnDate1.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.txtDate1.text = dateFormat.format(selectedDate)
                date1 = selectedDate
            }
        }

        binding.btnDate2.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.txtDate2.text = dateFormat.format(selectedDate)
                date2 = selectedDate
            }
        }

        binding.btnCalculate.setOnClickListener {
            val amount = binding.edtAmount.text.toString().toDoubleOrNull() ?: 0.0
            val roi = binding.edtRate.text.toString().toDoubleOrNull() ?: 0.0

            if (date1 == null || date2 == null) {
                return@setOnClickListener
            }

            val daysBetween = ((date2!!.time - date1!!.time) / (1000 * 60 * 60 * 24)).toInt()
            if (daysBetween < 0) {
                return@setOnClickListener
            }

            // Simple Interest: (P × R × T) / (100 × 30) → since R = ₹ per ₹100 per month (~30 days)
            val interest = (amount * roi * daysBetween) / (100 * 30)
            val total = amount + interest

            // Update UI with distinct colors
            binding.txtPrincipal.text = "Principal Amount: ₹${"%.2f".format(amount)}"
            binding.txtInterest.text = "Interest Amount: ₹${"%.2f".format(interest)}"
            binding.txtTotal.text = "Total Amount (P + I): ₹${"%.2f".format(total)}"
        }
    }

    private fun showDatePicker(onDateSelected: (Date) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val selected = Calendar.getInstance()
                selected.set(year, month, day)
                onDateSelected(selected.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
