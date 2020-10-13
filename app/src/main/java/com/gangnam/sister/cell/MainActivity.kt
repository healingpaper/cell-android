package com.gangnam.sister.cell

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
import com.gangnam.sister.cell.element.badge.CellBadge
import com.gangnam.sister.cell.element.badge.CellBadgeStack
import com.gangnam.sister.cell.element.divider.CellDivider
import com.gangnam.sister.cell.element.divider.CellDividerItemDecoration
import com.gangnam.sister.cell.listener.OnItemClickListener
import com.gangnam.sister.cell.util.DisplayManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        buttonStack.buttonCount = 1
//        buttonStack.setButtonItemClickListener { _: View, index: Int ->
//            if (index == 0) buttonStack.isAboveKeyboard = true
//        }
        selectSet.setOnItemSelectedListener { _, _, i -> toast("position: $i") }
        segmentController.setOnItemSelectedListener { _, _, i -> toast("position: $i") }
        cellInputSet.textField.addTextChangedListener(object : TextWatcher {
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
        val badges = arrayListOf<String>()
        badges.add("CCTV")
        badges.add("전문의뱃지")
        badges.add("입원실")
        badges.add("안심실명제")
        badges.add("야간진료")
        badges.add("응급의료가능")
        badges.add("분야별전문")
        badges.add("전용회복실")
        badges.add("부작용이없다")
        badges.add("전용숙소")
        badges.add("의료의료")
        badges.add("여성전문의가")
        cellBadgeStack.setData(badges)
        cellBadgeStack.setDataWithItem(badges.mapIndexed { index: Int, text: String ->
            val color = if(index % 2 == 0) CellBadge.BadgeStyle.LEMONADE else CellBadge.BadgeStyle.LIGHT_GRAY
            val drawableStart = if(index % 2 == 0) R.drawable.ic_etc_hashtag else 0
            val drawableEnd = if(index % 2 == 1) R.drawable.ic_etc_hashtag else 0
            CellBadgeStack.Item(text, color, drawableStart, drawableEnd)
        })
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
//            addItemDecoration(CellSpacingItemDecoration(context).apply {
//                type = CellSpacing.SpacingType.SMALL
//            })
            addItemDecoration(CellDividerItemDecoration(context).apply {
                marginStart = DisplayManager.dpToPx(context, 16)
                marginEnd = DisplayManager.dpToPx(context, 16)
                marginTop = DisplayManager.dpToPx(context, 16)
                marginBottom = DisplayManager.dpToPx(context, 16)
                type = CellDivider.DividerType.LIST
                height = DisplayManager.dpToPx(context, 16)
            })
        }
    }

    private fun toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    internal class TestAdapter : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
            return TestViewHolder(TextView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundColor(parent.context.getColor(R.color.confident_orange))
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