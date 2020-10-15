package com.gangnam.sister.cell

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gangnam.sister.cell.component.inputset.CellInputSet
import com.gangnam.sister.cell.data.Header
import com.gangnam.sister.cell.data.Item
import com.gangnam.sister.cell.element.badge.CellBadge
import com.gangnam.sister.cell.element.badge.CellBadgeStack
import com.gangnam.sister.cell.element.button.ButtonStyle
import com.gangnam.sister.cell.element.button.ButtonStyles
import com.gangnam.sister.cell.element.divider.CellDivider
import com.gangnam.sister.cell.element.divider.CellDividerItemDecoration
import com.gangnam.sister.cell.listener.OnItemClickListener
import com.gangnam.sister.cell.util.DisplayManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = MainAdapter().apply {
            val list = arrayListOf<Any>()
            list.add(Header("Element"))
            list.add(Item("CellButton"))
            list.add(Item("CellBadge"))
            list.add(Item("CellTextField"))
            list.add(Header("Component"))
            setData(list)
            setOnClickListener { navigateToActivity(it) }
        }
        recyclerView.addItemDecoration(CellDividerItemDecoration(this))
    }

    private fun navigateToActivity(text: String) {
        val cls = when (text) {
            "CellButton" -> ButtonActivity::class.java
            "CellBadge" -> BadgeActivity::class.java
            "CellTextField" -> TextFieldActivity::class.java
            else -> ButtonActivity::class.java
        }
        val intent = Intent(this, cls)
        startActivity(intent)
    }
}