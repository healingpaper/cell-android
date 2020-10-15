package com.gangnam.sister.cell.element

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.element.badge.CellBadge
import com.gangnam.sister.cell.element.badge.CellBadgeStack
import com.gangnam.sister.cell.listener.OnItemClickListener
import kotlinx.android.synthetic.main.activity_badge.*

class BadgeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge)

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
        firstBadgeStack.setDataWithItem(badges.mapIndexed { index: Int, text: String ->
            val color = if(index % 2 == 0) CellBadge.BadgeStyleType.YELLOW else CellBadge.BadgeStyleType.GRAY
            val drawableStart = if(index % 2 == 0) R.drawable.ic_etc_hashtag else 0
            val drawableEnd = if(index % 2 == 1) R.drawable.ic_etc_hashtag else 0
            CellBadgeStack.Item(text, color, drawableStart, drawableEnd)
        })
        firstBadgeStack.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("CellTestActivity", "$position")
                firstBadgeStack.removeView(position)
            }
        })
        secondBadgeStack.setData(badges)
        thirdBadgeStack.setData(badges)
        fourthBadgeStack.setData(badges)
    }
}