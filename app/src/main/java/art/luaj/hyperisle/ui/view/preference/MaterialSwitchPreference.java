package art.luaj.hyperisle.ui.view.preference;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.SwitchPreferenceCompat;

import art.luaj.hyperisle.R;

public class MaterialSwitchPreference extends SwitchPreferenceCompat {
    public MaterialSwitchPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public MaterialSwitchPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MaterialSwitchPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialSwitchPreference(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        setWidgetLayoutResource(R.layout.pref_m3_switch);
    }
}
