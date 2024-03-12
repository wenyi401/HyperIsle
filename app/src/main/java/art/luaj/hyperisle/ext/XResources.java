package art.luaj.hyperisle.ext;

import android.content.res.Resources;
import android.content.res.XModuleResources;
import android.content.res.XmlResourceParser;

public class XResources {
    private static XModuleResources RESOURCES;

    public static void init(XModuleResources resources) {
        RESOURCES = resources;
    }

    public static XmlResourceParser getLayout(int id) {
        return RESOURCES.getLayout(id);
    }

    public static int getColor(int id) {
        return RESOURCES.getColor(id);
    }

    public static int getColor(int id, Resources.Theme theme) {
        return RESOURCES.getColor(id, theme);
    }

    public static boolean getBoolean(int id) {
        return RESOURCES.getBoolean(id);
    }
}
