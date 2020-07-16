package com.gangnam.sister.cell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gangnam.sister.cell.component.inputset.CellInputSet
import com.gangnam.sister.cell.element.badge.CellBadge
import com.gangnam.sister.cell.element.button.CellButton
import com.gangnam.sister.cell.element.spacing.CellSpacing
import com.gangnam.sister.cell.element.spacing.CellSpacingItemDecoration
import com.gangnam.sister.cell.listener.OnItemClickListener
import com.gangnam.sister.cell.util.DisplayManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectSet.setOnItemSelectedListener { _, _, i -> toast("position: $i") }
        segmentController.setOnItemSelectedListener { _, _, i -> toast("position: $i") }
        cellInputSet.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                cellInputSet.state =
                    if (s.length > 5) CellInputSet.InputSetState.CORRECT else CellInputSet.InputSetState.ERROR
            }
        })
        cellTextBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                cellTextBox.hasError = s.length > 5
            }
        })
        cellBtn1.buttonColor = CellButton.ButtonColor.LIGHT_GRAY
        cellBtn3.buttonButtonTextAppearance = CellButton.ButtonTextAppearance.BIG
        cellBtn5.buttonType = CellButton.ButtonType.FULL_WIDTH
        cellBtn6.buttonType = CellButton.ButtonType.BIG_BLOCK
        cellBtn7.buttonType = CellButton.ButtonType.FULL_WIDTH
        val badges = arrayListOf<String>()
        for (i in 1..15) badges.add("Badge $i")
        badges.add("Badge 1")
        cellBadgeStack.setData(badges)
        cellBadgeStack.badgeColor = CellBadge.BadgeColor.LEMONADE
        cellBadgeStack.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("CellTestActivity", "$position")
                cellBadgeStack.removeView(position)
            }
        })
        selectDropdown.setTextBoxClickListener {
            toast("SelectBox Clicked!")
        }
        recyclerView.apply {
            adapter = TestAdapter()
//            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            addItemDecoration(CellSpacingItemDecoration(context).apply {
                type = CellSpacing.SpacingType.SMALL
            })
            /*addItemDecoration(CellDividerItemDecoration(context).apply {
                marginStart = DisplayManager.dpToPx(16)
                marginEnd = DisplayManager.dpToPx(16)
                type = CellDivider.DividerType.SECTION
                height = DisplayManager.dpToPx(8)
            })*/
        }
    }

    private fun toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    internal class TestAdapter : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
            return TestViewHolder(TextView(parent.context).apply {
                setPadding(
                    0,
                    DisplayManager.dpToPx(context, 8),
                    0,
                    DisplayManager.dpToPx(context, 8)
                )
            })
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
            (holder.view as TextView).text = "안녕하세요. $position"
        }

        internal class TestViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        }
    }
}