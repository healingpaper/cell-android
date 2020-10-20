package com.gangnam.sister.cell.component

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.gangnam.sister.cell.R
import kotlinx.android.synthetic.main.activity_button_stack.*

class ButtonStackActivity : AppCompatActivity() {
    private var isKeyboardHidden = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_stack)
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
    }
}