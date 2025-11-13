package com.example.puli

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL

class PinActivity : AppCompatActivity() {

    private lateinit var edtPin: EditText
    private var dynamicPin: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        edtPin = findViewById(R.id.edtPin)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        
        fetchAndParsePinXml("https://raw.githubusercontent.com/socialmediawestgodavari-spec/puli-data/refs/heads/main/akey") { pin ->
            dynamicPin = pin
        }

        btnSubmit.setOnClickListener {
            val entered = edtPin.text.toString()
            if (entered.length != 4) {
                Toast.makeText(this, "Enter a 4-digit PIN", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (entered == dynamicPin) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid PIN", Toast.LENGTH_SHORT).show()
                edtPin.setText("")
            }
        }
    }

    private fun fetchAndParsePinXml(urlString: String, onSuccess: (String) -> Unit) {
        Thread {
            try {
                val url = URL(urlString)
                (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "GET"
                    connectTimeout = 10000
                    readTimeout = 10000

                    if (responseCode == 200) {
                        val factory = XmlPullParserFactory.newInstance()
                        factory.isNamespaceAware = true
                        val parser = factory.newPullParser()
                        parser.setInput(inputStream, null)

                        var p = ""; var u = ""; var l = ""; var i_val = ""
                        var tagName = ""

                        var eventType = parser.eventType
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            when (eventType) {
                                XmlPullParser.START_TAG -> {
                                    tagName = parser.name
                                    if (tagName in listOf("p", "u", "l", "i")) {
                                        val text = parser.nextText().trim()
                                        when (tagName) {
                                            "p" -> p = text
                                            "u" -> u = text
                                            "l" -> l = text
                                            "i" -> i_val = text
                                        }
                                    }
                                }
                            }
                            eventType = parser.next()
                        }

                      
                        val pin = buildString {
                          
                            append(if (p.length >= 1) p[0] else '0')                           
                            append(if (u.length >= 2) u[1] else '0')                        
                            append(if (l.length >= 3) l[2] else '0')                           
                            append(if (i_val.length >= 4) i_val[3] else '0')
                        }

                        runOnUiThread {
                            onSuccess(pin)
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@PinActivity, "Failed to load PIN (HTTP ${responseCode})", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@PinActivity, "PIN load error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }
}
