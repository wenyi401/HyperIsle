package art.luaj.hyperisle.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.preference.SwitchPreferenceCompat
import art.luaj.hyperisle.R

class MaterialSwitchPreference : SwitchPreferenceCompat {
    init {
        widgetLayoutResource = R.layout.pref_switch
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(context: Context) : super(context)
}