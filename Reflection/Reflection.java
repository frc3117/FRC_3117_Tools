package frc.robot.Library.FRC_3117_Tools.Reflection;

import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Reflection
{
    private static HashMap<String, PackageInfo> BakedPackages = new HashMap<>();

    public static PackageInfo GetPackageInfo(String packageName)  {
        return BakedPackages.get(packageName);
    }

    public static void BakePackages(String... includePackages) {
        var classPathEntries = System.getProperty("java.class.path").split(
                System.getProperty("path.separator")
        );

        for (var classPathEntry : classPathEntries)
        {
            if (classPathEntry.endsWith(".jar"))
            {
                var jar = new File(classPathEntry);
                try
                {
                    var stream = new JarInputStream(new FileInputStream(jar));
                    JarEntry entry;

                    while ((entry = stream.getNextJarEntry()) != null)
                    {
                        var name = entry.getName().replaceAll("[\\|/]", ".");
                        if (name.endsWith(".class"))
                        {
                            var className = name.substring(0, name.lastIndexOf('.'));
                            var packageName = className.substring(0, className.lastIndexOf('.'));

                            if (ShouldInclude(packageName, includePackages))
                            {
                                if (!BakedPackages.containsKey(packageName))
                                    BakedPackages.put(packageName, new PackageInfo(packageName));

                                BakedPackages.get(packageName).Classes.add(new ClassInfo(className));
                            }
                        }
                    }
                } catch (Exception e) { }
            }
            else
            {
                for (var p : includePackages)
                {
                    var path = classPathEntry + "/" + p.replaceAll("\\.", "/");

                    LoadPackageRecursive(new File(path), p);
                }
            }
        }
    }
    private static boolean ShouldInclude(String packageName, String... includeList) {
        for (var s : includeList)
        {
            if (packageName.startsWith(s))
                return true;
        }

        return false;
    }
    private static void LoadPackageRecursive(File file, String packageName) {
        try
        {
            for (var child : file.listFiles())
            {
                var name = packageName + "." + child.getName();

                if (child.isDirectory())
                    LoadPackageRecursive(child, name);
                else
                {
                    var className = name.substring(0, name.length() - 6);

                    if (!BakedPackages.containsKey(packageName))
                        BakedPackages.put(packageName, new PackageInfo(packageName));

                    BakedPackages.get(packageName).Classes.add(new ClassInfo(className));
                }
            }
        } catch (Exception e) { }
    }

    public static List<Class<?>> GetAllClassInPackage(String packageName) {
        return GetPackageInfo(packageName).GetClasses();
    }
    public static List<Class<?>> GetAllClass() {
        var classes = new ArrayList<Class<?>>();

        for (var p : BakedPackages.values())
        {
            classes.addAll(p.GetClasses());
        }

        return classes;
    }
    public static List<Class<?>> GetAllInheritedClass(Class<?> parentClass) {
        var inherited = new ArrayList<Class<?>>();

        for (var p : BakedPackages.values())
        {
            for (var cls : p.GetClasses())
            {
                if (parentClass.isAssignableFrom(cls))
                    inherited.add(cls);
            }
        }

        return inherited;
    }
    public static List<Class<?>> GetAllClassWithAnnotation(Class<? extends Annotation> annotation) {
        var annotated = new ArrayList<Class<?>>();

        for (var p : BakedPackages.values())
        {
            for (var cls : p.GetClasses())
            {
                if (cls.isAnnotationPresent(annotation))
                    annotated.add(cls);
            }
        }

        return annotated;
    }
}
