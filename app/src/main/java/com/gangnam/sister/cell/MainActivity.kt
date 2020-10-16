package com.gangnam.sister.cell

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gangnam.sister.cell.component.BadgeStackActivity
import com.gangnam.sister.cell.component.ButtonStackActivity
import com.gangnam.sister.cell.component.TextAreaComponentActivity
import com.gangnam.sister.cell.component.TextFieldComponentActivity
import com.gangnam.sister.cell.data.Header
import com.gangnam.sister.cell.data.Item
import com.gangnam.sister.cell.element.BadgeActivity
import com.gangnam.sister.cell.element.ButtonActivity
import com.gangnam.sister.cell.element.TextAreaActivity
import com.gangnam.sister.cell.element.TextFieldActivity
import com.gangnam.sister.cell.element.divider.CellDividerItemDecoration
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
            list.add(Item("CellTextArea"))
            list.add(Header("Component"))
            list.add(Item("CellButtonStack"))
            list.add(Item("CellBadgeStack"))
            list.add(Item("CellTextFieldComponent"))
            list.add(Item("CellTextAreaComponent"))
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
            "CellTextArea" -> TextAreaActivity::class.java
            "CellButtonStack" -> ButtonStackActivity::class.java
            "CellBadgeStack" -> BadgeStackActivity::class.java
            "CellTextFieldComponent" -> TextFieldComponentActivity::class.java
            "CellTextAreaComponent" -> TextAreaComponentActivity::class.java
            else -> ButtonActivity::class.java
        }
        val intent = Intent(this, cls)
        startActivity(intent)
    }
}