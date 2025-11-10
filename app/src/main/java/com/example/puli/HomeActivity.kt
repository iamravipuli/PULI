package com.example.puli

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.btnCredit).setOnClickListener {
            // TODO: Add Credit logic later
        }

        findViewById<Button>(R.id.btnDebit).setOnClickListener {
            // TODO: Add Debit logic later
        }

        findViewById<Button>(R.id.btnCalculate).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
