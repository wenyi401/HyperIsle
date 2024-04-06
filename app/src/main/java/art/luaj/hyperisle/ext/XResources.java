package art.luaj.hyperisle.ext;

import android.content.res.Resources;
import android.content.res.XModuleResources;
import android.content.res.XmlResourceParser;

public class XResources {
    private static XModuleResources RESOURCES;

    /**
     * 初始化
     * @param resources
     */
    public static void init(XModuleResources resources) {
        RESOURCES = resources;
    }

    /**
     * 获取layout资源
     * @param id id值
     * @return xml
     */
    public static XmlResourceParser getLayout(int id) {
        return RESOURCES.getLayout(id);
    }

    /**
     * 获取color资源
     * @param id id值
     * @return int
     */
    public static int getColor(int id) {
        return RESOURCES.getColor(id);
    }

    /**
     * 获取color资源
     * @param id id值
     * @param theme 主题
     * @return int
     */
    public static int getColor(int id, Resources.Theme theme) {
        return RESOURCES.getColor(id, theme);
    }

    /**
     * 获取bool资源
     * @param id id值
     * @return bool
     */
    public static boolean getBoolean(int id) {
        return RESOURCES.getBoolean(id);
    }
}
