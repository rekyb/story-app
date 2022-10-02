package me.rekyb.storyapp.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import me.rekyb.storyapp.R

class TextLayout : TextInputLayout {

    companion object {
        private const val NAME_TEXT = 97
        private const val EMAIL_TEXT = 33
        private const val PASSWORD_TEXT = 129
        private const val BOX_RADIUS = 10f
    }

    private lateinit var icName: Drawable
    private lateinit var icEmail: Drawable
    private lateinit var icPassword: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context, attributeSet, defStyleAttr
    ) {
        init()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        renderTextDecor()
    }

    private fun init() {
        val boxRadii = BOX_RADIUS

        isErrorEnabled = true

        setBoxCornerRadii(boxRadii, boxRadii, boxRadii, boxRadii)
        addOnEditTextAttachedListener {
            it.editText?.doOnTextChanged { text, _, _, _ ->
                validityCheck(text.toString())
            }
        }
    }

    private fun renderTextDecor() {
        icName = ContextCompat.getDrawable(context, R.drawable.ic_person) as Drawable
        icEmail = ContextCompat.getDrawable(context, R.drawable.ic_email) as Drawable
        icPassword = ContextCompat.getDrawable(context, R.drawable.ic_password) as Drawable

        endIconMode = END_ICON_CLEAR_TEXT

        when (editText?.inputType) {
            NAME_TEXT -> {
                hint = context.getString(R.string.name_hint)
                startIconDrawable = icName
            }
            EMAIL_TEXT -> {
                hint = context.getString(R.string.email_hint)
                startIconDrawable = icEmail
            }
            PASSWORD_TEXT -> {
                hint = context.getString(R.string.password_hint)
                startIconDrawable = icPassword
                endIconMode = END_ICON_PASSWORD_TOGGLE
            }
        }
    }

    private fun validityCheck(input: String?) {
        if (input.isNullOrEmpty()) {
            error = context.getString(R.string.empty_input_error)
            return
        }

        when (editText?.inputType) {
            EMAIL_TEXT -> {
                error = if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                    context.getString(R.string.email_error)
                } else {
                    null
                }
            }
            PASSWORD_TEXT -> {
                error = if (input.length < 6) context.getString(R.string.password_error) else null
            }
        }
    }
}
