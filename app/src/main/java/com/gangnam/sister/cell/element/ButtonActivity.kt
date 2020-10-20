package com.gangnam.sister.cell.element

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gangnam.sister.cell.R
import kotlinx.android.synthetic.main.activity_button.*

class ButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button)
        disabledLargeBtn.setOnClickListener { disabledLargeBtn.isButtonEnabled = true }
        disabledMediumBtn.setOnClickListener { disabledMediumBtn.isButtonEnabled = true }
        disabledSmallBtn.setOnClickListener { disabledSmallBtn.isButtonEnabled = true }
    }
}