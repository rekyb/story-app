package me.rekyb.storyapp.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import me.rekyb.storyapp.R

class TextLayout : TextInputLayout {

    companion object {
        private const val NAME_TEXT = 97
        private const val EMAIL_TEXT = 33
        private const val PASSWORD_TEXT = 129
    }

    private lateinit var icName: Drawable
    private lateinit var icEmail: Drawable
    private lateinit var icPassword: Drawable

    constructor(context: Context) : super(context) {
        initLayout()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initLayout()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context, attributeSet, defStyleAttr
    ) {
        initLayout()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        renderTextDecor()
    }

    private fun initLayout() {
        boxBackgroundMode = BOX_BACKGROUND_OUTLINE
        boxBackgroundColor = ContextCompat.getColor(context, R.color.white)

        setHelperTextColor(ContextCompat.getColorStateList(context, R.color.red))
        addOnEditTextAttachedListener {
            it.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    validityCheck(s.toString())
                }
            })
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
            helperText = context.getString(R.string.edit_helper)
            return
        }

        when (editText?.inputType) {
            EMAIL_TEXT -> {
                if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                    helperText = context.getString(R.string.email_helper)
                } else isHelperTextEnabled = false
            }
            PASSWORD_TEXT -> {
                if (input.length < 6) {
                    helperText = context.getString(R.string.password_helper)
                } else isHelperTextEnabled = false
            }
        }
    }
}
