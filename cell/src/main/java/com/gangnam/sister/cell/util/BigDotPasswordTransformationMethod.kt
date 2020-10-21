package com.gangnam.sister.cell.util

import android.text.method.PasswordTransformationMethod
import android.view.View

class BigDotPasswordTransformationMethod : PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence?, view: View?): CharSequence {
        return BigDotCharSequence(source)
    }

    private class BigDotCharSequence(private val source: CharSequence?) : CharSequence {
        override val length: Int
            get() {
                return source?.length ?: 0
            }

        override fun get(index: Int): Char {
            return '\u25cf'
        }

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            return source?.subSequence(startIndex, endIndex) ?: ""
        }

    }
}