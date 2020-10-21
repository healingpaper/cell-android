package com.gangnam.sister.cell.extension

import com.gangnam.sister.cell.component.textbox.CellTextAreaComponent
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.editorActions
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.cell_text_area_component.view.*

fun CellTextAreaComponent.textChanges() = mainInput.textChanges()

fun CellTextAreaComponent.editorActions() = mainInput.editorActions()

fun CellTextAreaComponent.focusChanges() = mainInput.focusChanges()