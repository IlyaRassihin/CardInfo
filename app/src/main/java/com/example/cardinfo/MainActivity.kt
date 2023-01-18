package com.example.cardinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
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
    private var tvScheme: TextView? = null
    private var tvCardType: TextView? = null
    private var tvPrepaid: TextView? = null
    private var tvVisa: TextView? = null
    private var tvCredit: TextView? = null
    private var tvPrepaidType: TextView? = null
    private var tvCardNumber: TextView? = null
    private var tvBrand: TextView? = null
    private var tvCountry: TextView? = null
    private var tvRealCardNumber: TextView? = null
    private var tvBrandName: TextView? = null
    private var tvCountryName: TextView? = null
    private var tvBank: TextView? = null
    private var tvBankInfo: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEnterCardNumber = findViewById(R.id.et_enter_card_number)
        //btnGetInfo = findViewById(R.id.btn_get_weather)
        btnEnter = findViewById(R.id.btn_enter)
        tvScheme = findViewById(R.id.tv_scheme)
        tvCardType = findViewById(R.id.tv_card_type)
        tvPrepaid = findViewById(R.id.tv_prepaid)
        tvVisa = findViewById(R.id.tv_visa)
        tvCredit = findViewById(R.id.tv_credit)
        tvPrepaidType = findViewById(R.id.tv_prepaid_type)
        tvCardNumber = findViewById(R.id.tv_card_number)
        tvBrand = findViewById(R.id.tv_brand)
        tvCountry = findViewById(R.id.tv_country)
        tvRealCardNumber = findViewById(R.id.tv_real_card_number)
        tvBrandName = findViewById(R.id.tv_brand_name)
        tvCountryName = findViewById(R.id.tv_country_name)
        tvBank = findViewById(R.id.tv_bank)
        tvBankInfo = findViewById(R.id.tv_bank_info)
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

                            tvVisa!!.text = response.body()!!.scheme.capitalize()
                            tvCredit!!.text =  response.body()!!.type.capitalize()
                            tvPrepaidType!!.text = if(response.body()!!.prepaid.toString().equals("true")) "Yes" else "No"
                            tvRealCardNumber!!.text = "Length: " + response.body()!!.number.length.toString() + "\n" + "Luhn: " + if(response.body()!!.number.luhn.toString().equals("true")) "Yes" else "No"
                            tvCountryName!!.text = response.body()!!.country.alpha2 + " " + response.body()!!.country.name + "\n" + "Latitude: " + response.body()!!.country.latitude + "\n" + "Longitude: " + response.body()!!.country.longitude
                            tvBrandName!!.text = response.body()!!.brand
                            tvBankInfo!!.text = response.body()!!.bank.name + ", " + response.body()!!.bank.city + "\n" + response.body()!!.bank.url + "\n" + response.body()!!.bank.phone


                            //val bankName = response.body()!!.bank
                            //Toast.makeText(this@MainActivity, "Bank: $bankName", Toast.LENGTH_LONG).show()
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