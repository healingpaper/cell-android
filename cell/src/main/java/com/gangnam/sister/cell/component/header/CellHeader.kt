package com.gangnam.sister.cell.component.header

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.forEachIndexed
import com.gangnam.sister.cell.R
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.cell_header.view.*

open class CellHeader @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var menu: Int = 0
        set(value) {
            field = value
            inflateMenu(value)
        }
    var title: String? = null
        set(value) {
            field = value
            titleTxt.text = value
        }
    var showNavigationIcon = true
        set(value) {
            field = value
            navigationIcon = if (!value) {
                null
            } else ContextCompat.getDrawable(context, R.drawable.ic_action_topbar_back_black)
        }
    var navigationIcon: Drawable? = null
        set(value) {
            field = value
            toolbar.navigationIcon = value
        }
    var isCloseMode = false
        set(value) {
            field = value
            showNavigationIcon = if (value) {
                inflateMenu(R.menu.menu_right_x)
                setOnMenuItemClickListener {
                    (context as Activity).onBackPressed()
                    true
                }
                false
            } else true
        }
    var showShadow = true
        set(value) {
            field = value
            toolbarShadow.visibility = if (value) View.VISIBLE else View.GONE
        }

    var layoutCollapseMode: Int = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_OFF
        set(value) {
            field = value
            val params = toolbar.layoutParams
            val newParams = if (params is CollapsingToolbarLayout.LayoutParams) {
                params
            } else CollapsingToolbarLayout.LayoutParams(params)
            newParams.collapseMode = value
            toolbar.layoutParams = newParams
            toolbar.requestLayout()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.cell_header, this, true)
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellHeader, 0, 0)
            .use {
                if (it.hasValue(R.styleable.CellHeader_menu)) {
                    menu = it.getResourceId(R.styleable.CellHeader_menu, 0)
                }
                if (it.hasValue(R.styleable.CellHeader_toolbarTitle)) {
                    title = it.getString(R.styleable.CellHeader_toolbarTitle)
                }
                if (it.hasValue(R.styleable.CellHeader_showNavigationIcon)) {
                    showNavigationIcon = it.getBoolean(R.styleable.CellHeader_showNavigationIcon, true)
                }
                if (it.hasValue(R.styleable.CellHeader_navigationIcon)) {
                    navigationIcon = it.getDrawable(R.styleable.CellHeader_navigationIcon)
                }
                if (it.hasValue(R.styleable.CellHeader_isCloseMode)) {
                    isCloseMode = it.getBoolean(R.styleable.CellHeader_isCloseMode, false)
                }
                if (it.hasValue(R.styleable.CellHeader_logo)) {
                    toolbar.logo = it.getDrawable(R.styleable.CellHeader_logo)
                }
                if (it.hasValue(R.styleable.CellHeader_showShadow)) {
                    showShadow = it.getBoolean(R.styleable.CellHeader_showShadow, true)
                }
                if (it.hasValue(R.styleable.CellHeader_layout_collapseMode)) {
                    layoutCollapseMode = it.getInt(R.styleable.CellHeader_layout_collapseMode, 0)
                }
            }
    }

    fun setOnCloseBtnClickListener(clickListener: OnMenuItemClickListener) {
        setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener {
            clickListener.onMenuItemClick()
            true
        })
    }

    // toolbar right menu
    fun inflateMenu(resId: Int) {
        toolbar.inflateMenu(resId)
        toolbar.setNavigationOnClickListener { (context as Activity).onBackPressed() }
    }

    fun getMenuItem(menuItemId: Int): MenuItem {
        return toolbar.menu.findItem(menuItemId)
    }

    /**
     * Default
     */

    fun setOnMenuItemClickListener(listener: Toolbar.OnMenuItemClickListener) {
        toolbar.setOnMenuItemClickListener(listener)
    }

    /**
     * Custom Right Menu
     * 필수 조건 inflate menu
     */

    // toolbar 오른쪽 메뉴 텍스트 설정
    fun setRightMenuText(menuStr: String) {
        toolbar.menu.findItem(R.id.rightMenuItem)?.let {
            it.actionView.findViewById<TextView>(R.id.menuTxt).text = menuStr
        }
    }

    // toolbar 오른쪽 메뉴 enable 설정
    fun setRightMenuEnabled(isEnabled: Boolean) {
        toolbar.menu.findItem(R.id.rightMenuItem)?.let {
            it.actionView.findViewById<TextView>(R.id.menuTxt).isSelected = isEnabled
        }
    }

    fun setMenuVisibility(isVisible: Boolean) {
        toolbar.menu.findItem(R.id.rightMenuItem).isVisible = isVisible
    }

    fun setOnRightMenuItemClickListener(listener: () -> Unit) {
        toolbar.menu.findItem(R.id.rightMenuItem)?.let {
            it.actionView.setOnClickListener { listener.invoke() }
        }
    }
}