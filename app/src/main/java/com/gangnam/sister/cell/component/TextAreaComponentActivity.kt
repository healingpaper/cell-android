package com.gangnam.sister.cell.component

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.component.textbox.CellTextAreaComponent
import kotlinx.android.synthetic.main.activity_text_area_component.*

class TextAreaComponentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_area_component)
        firstTextArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                firstTextArea.state = when {
                    s.length > 10 -> {
                        CellTextAreaComponent.State.ERROR
                    }
                    s.length > 5 -> {
                        CellTextAreaComponent.State.CORRECT
                    }
                    else -> CellTextAreaComponent.State.NORMAL
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        firstTextArea.maxLength = 100
    }
}