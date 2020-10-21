package com.gangnam.sister.cell.extension

import com.gangnam.sister.cell.component.button.CellButtonStack
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.longClicks
import kotlinx.android.synthetic.main.cell_button_stack.view.*

fun CellButtonStack.firstClicks() = firstBtn.clicks()
fun CellButtonStack.secondClicks() = secondBtn.clicks()
fun CellButtonStack.thirdClicks() = thirdBtn.clicks()

fun CellButtonStack.firstLongClicks() = firstBtn.longClicks()
fun CellButtonStack.secondLongClicks() = secondBtn.longClicks()
fun CellButtonStack.thirdLongClicks() = thirdBtn.longClicks()