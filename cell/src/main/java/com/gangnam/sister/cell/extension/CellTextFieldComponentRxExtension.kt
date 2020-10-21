package com.gangnam.sister.cell.extension

import com.gangnam.sister.cell.component.textbox.CellTextFieldComponent
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.editorActions
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.cell_text_field_component.view.mainInput

fun CellTextFieldComponent.textChanges() = mainInput.textChanges()

fun CellTextFieldComponent.editorActions() = mainInput.editorActions()

fun CellTextFieldComponent.focusChanges() = mainInput.focusChanges()