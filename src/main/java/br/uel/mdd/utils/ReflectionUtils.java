package br.uel.mdd.utils;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 02/06/14.
 */
public class ReflectionUtils {

    public static Class findClassByName(String packageName, String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(packageName + "." + className);
        } catch (ClassNotFoundException e) {
        }
        return clazz;
    }
}
