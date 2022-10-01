package me.rekyb.storyapp.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.core.view.setPadding
import com.google.android.material.textfield.TextInputEditText

class EditText: TextInputEditText {

    companion object {
        private const val DEFAULT_PADDING = 36f
        private const val DRAWABLE_PADDING = 12f
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initView()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun initView() {
        setPadding(DEFAULT_PADDING.toInt())
        compoundDrawablePadding = DRAWABLE_PADDING.toInt()
    }
}
