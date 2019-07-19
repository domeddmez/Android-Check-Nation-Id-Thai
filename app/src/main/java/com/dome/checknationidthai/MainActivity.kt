package com.dome.checknationidthai

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Float.parseFloat


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btConfirm.setOnClickListener {
            hideSoftKeyboard(this, it)
            if (checkFieldsForIdCard(etIdCard.length())) {
                etIdCard.setText(idFormat(etIdCard.text.toString().replace("-", "")))
                btConfirm.visibility = View.GONE
                btTryAgain.visibility = View.VISIBLE
                etIdCard.isEnabled = false
                tvStatus.text = "Correct"
            } else {
                tvStatus.text = "Wrong"
            }
        }

        btTryAgain.setOnClickListener {
            btConfirm.visibility = View.VISIBLE
            btTryAgain.visibility = View.GONE
            etIdCard.isEnabled = true
            etIdCard.isFocusable = true
            etIdCard.setText("")
            tvStatus.text = "Status"

        }


        root.setOnTouchListener { v, event ->
            hideSoftKeyboard(this, v)
            return@setOnTouchListener true
        }


    }

    private fun checkFieldsForIdCard(id: Int): Boolean {
        if (id != 13) return false
        var i = 0
        var sum = 0
        while (i < 12) {
            sum += (parseFloat(etIdCard.text[i].toString().replace("-", "")) * (13 - i)).toInt()
            i++
        }
        return ((11 - sum % 11) % 10).toFloat() == parseFloat(etIdCard.text[12].toString().replace("-", ""))
    }

    private fun idFormat(both: String): String {
        var data = unDashedFormat(both)
        if (data.length == 13) {
            data =
                data.substring(0, 1) + "-" + data.substring(1, 5) + "-" + data.substring(5, 10) + "-" + data.substring(
                    10,
                    12
                ) + "-" + data.substring(12, data.length)
        }
        return data
    }

    private fun unDashedFormat(value: String?): String {
        return if (value == null || value.isEmpty()) "" else value.replace("-", "")

    }

    private fun hideSoftKeyboard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}
