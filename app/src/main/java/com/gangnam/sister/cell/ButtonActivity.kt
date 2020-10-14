package com.gangnam.sister.cell

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_button.*

class ButtonActivity : AppCompatActivity() {
    private var isKeyboardHidden = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button)
        bigBlock.setButtonItemClickListener { _, _ ->
            if (isKeyboardHidden) {
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                isKeyboardHidden = !isKeyboardHidden
                bigBlock.isAboveKeyboard = true
            } else {
                val imm = getSystemService(
                    INPUT_METHOD_SERVICE
                ) as InputMethodManager
                imm.hideSoftInputFromWindow(bigBlock.windowToken, 0)
                isKeyboardHidden = !isKeyboardHidden
                bigBlock.isAboveKeyboard = false
            }
        }
        disabledLargeBtn.setOnClickListener { disabledLargeBtn.isButtonEnabled = true }
        disabledMediumBtn.setOnClickListener { disabledMediumBtn.isButtonEnabled = true }
        disabledSmallBtn.setOnClickListener { disabledSmallBtn.isButtonEnabled = true }
    }
}