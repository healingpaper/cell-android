package com.gangnam.sister.cell.listener

import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout

interface OnItemSelectedListener {
    fun onItemSelected(parent: ViewGroup, view: View?, position: Int)
}