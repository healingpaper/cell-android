package com.gangnam.sister.cell.component

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.component.textbox.CellTextFieldComponent
import kotlinx.android.synthetic.main.activity_text_field_component.*

class TextFieldComponentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_field_component)
        firstTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                firstTextField.state = when {
                    s.length > 10 -> {
                        CellTextFieldComponent.State.ERROR
                    }
                    s.length > 5 -> {
                        CellTextFieldComponent.State.CORRECT
                    }
                    else -> CellTextFieldComponent.State.NORMAL
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        firstTextField.maxLength = 15
    }
}