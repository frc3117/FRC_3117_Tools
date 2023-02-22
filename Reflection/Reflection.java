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

    public static PackageInfo GetPackagesInfo(String packageName) {
       return GetPackageInfo(packageName, true);
    }
    public static PackageInfo GetPackageInfo(String packageName, boolean useBakeIfExist) {
        if (useBakeIfExist && BakedPackages.containsKey(packageName))
            return BakedPackages.get(packageName);

        var packagePath = packageName.replaceAll("\\.", File.separator);
        var classPathEntries = System.getProperty("java.class.path").split(
                System.getProperty("path.separator")
        );

        var packageInfo = new PackageInfo(packageName);
        for (var classEntry : classPathEntries)
        {
            if (classEntry.endsWith(".jar"))
            {
                var jar = new File(classEntry);
                try
                {
                    var stream = new JarInputStream(new FileInputStream(jar));
                    JarEntry entry;

                    while ((entry = stream.getNextJarEntry()) != null)
                    {
                        var name = entry.getName();
                        if (name.endsWith(".class") && name.contains(packagePath))
                        {
                            var classPath = name.substring(0, name.length() - 6);
                            classPath = classPath.replaceAll("[\\|/]", ".");

                            packageInfo.Classes.add(new ClassInfo(classPath));
                        }
                    }
                } catch (Exception e) { }
            }
            else
            {
                try
                {
                    var base = new File(classEntry + File.separator + packagePath);
                    for (var file : base.listFiles())
                    {
                        var name = file.getName();
                        if (name.endsWith(".class"))
                        {
                            var classPath = name.substring(0, name.length() - 6);
                            if (!packageName.isEmpty())
                                classPath = packageName + "." + classPath;

                            packageInfo.Classes.add(new ClassInfo(classPath));
                        }
                    }
                } catch (Exception e) { }
            }
        }

        return packageInfo;
    }

    public static void BakeAllPackage() {
        for (var p : Package.getPackages())
            BakePackage(p.getName());
    }
    public static void BakePackage(String packageName) {
        BakedPackages.put(packageName, GetPackageInfo(packageName, false));
    }
    public static void BakePackageAndChild(String packageName) {
        for (var p : Package.getPackages())
        {
            if (p.getName().startsWith(packageName))
                BakePackage(p.getName());
        }
    }
    public static void RemoveBackedPackage(String packageName) {
        BakedPackages.remove(packageName);
    }

    public static List<Class<?>> GetAllClassInPackage(String packageName) {
        return GetAllClassInPackage(packageName, true);
    }
    public static List<Class<?>> GetAllClassInPackage(String packageName, boolean useBakeIfExist) {
        return GetPackageInfo(packageName, useBakeIfExist).GetClasses();
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
