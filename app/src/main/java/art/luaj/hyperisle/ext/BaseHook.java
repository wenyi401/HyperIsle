package art.luaj.hyperisle.ext;

import java.util.Objects;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class BaseHook implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    private String MODULE_PATH = null;
    private ClassLoader MODULE_LOADER = null;
    private LoadPackageParam loadPackageParam;

    @Override
    public void handleLoadPackage(LoadPackageParam arg) {
        if (MODULE_PATH == null) {
            XLog.print("not modulePath");
        }
        this.loadPackageParam = arg;
        if (MODULE_LOADER == null) {
            try {
                MODULE_LOADER = new DoubleParentClassLoader(MODULE_PATH,
                    Objects.requireNonNull(getClass().getClassLoader()).getParent(), arg.classLoader);
                Class<?> Hook = MODULE_LOADER.loadClass(getClass().toString());
                XposedHelpers.callStaticMethod(Hook, "hookLoadedPackage", arg);
            } catch (Throwable e) {
                XposedBridge.log(e);
            }
        }
    }

    @Override
    public void initZygote(StartupParam arg) {
        this.MODULE_PATH = arg.modulePath;
    }

    public String getModulePath() {
        return this.MODULE_PATH;
    }

    public Class<?> findClass(String classname) {
        return getClass(this.loadPackageParam, classname);
    }

    private Class<?> getClass(XC_LoadPackage.LoadPackageParam lpparam, String classname) {
        try {
            return lpparam.classLoader.loadClass(classname);
        } catch (ClassNotFoundException e) {
            XLog.print(e.toString());
            return null;
        }
    }

    class DoubleParentClassLoader extends PathClassLoader {

        private final ClassLoader mother;

        public DoubleParentClassLoader(String dexPath, ClassLoader father, ClassLoader mother) {
            super(dexPath, father);
            this.mother = mother;
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            try {
                return super.loadClass(name, resolve);
            } catch (ClassNotFoundException e) {
                try {
                    return (Class<?>) XposedHelpers.callMethod(mother, "loadClass", name, resolve);
                } catch (Exception exception) {
                    throw new ClassNotFoundException(name);
                }
            }
        }
    }
}