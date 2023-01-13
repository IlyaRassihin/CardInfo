package com.example.cardinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import model.ApiInterface
import model.CardInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var etEnterCardNumber: EditText? = null
    private var btnGetInfo: Button? = null
    private var btnEnter: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEnterCardNumber = findViewById(R.id.et_enter_card_number)
        //btnGetInfo = findViewById(R.id.btn_get_weather)
        btnEnter = findViewById(R.id.btn_enter)
    }

    override fun onStart() {
        super.onStart()

        btnEnter?.setOnClickListener {
            if (etEnterCardNumber?.text?.toString()?.trim()?.equals("")!!){
                etEnterCardNumber!!.error = "Enter card number"
                etEnterCardNumber!!.requestFocus()
            }
            else {
                val cardNumber = etEnterCardNumber?.text?.trim()?.toString()
                val apiInterface = ApiInterface.create().getCardInfo(cardNumber!!)
                apiInterface.enqueue(object : Callback<CardInfo> {
                    override fun onResponse(call: Call<CardInfo>, response: Response<CardInfo>) {
                        if(response.body() != null) {
                            val bankName = response.body()!!.bank
                            Toast.makeText(this@MainActivity, "Bank: $bankName", Toast.LENGTH_LONG).show()
                        }
                        else {
                            etEnterCardNumber!!.error = "Nonexistent card"
                            etEnterCardNumber!!.requestFocus()

                        }
                    }

                    override fun onFailure(call: Call<CardInfo>, t: Throwable) {

                        Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_LONG).show()
                        Log.d("btnCLick", "$t")
                    }
                })
            }
        }
    }
}