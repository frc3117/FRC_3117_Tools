package frc.robot.Library.FRC_3117_Tools.Reflection;

import java.util.ArrayList;
import java.util.List;

public class PackageInfo {
    public PackageInfo(String name) {
        Name = name;
        Classes = new ArrayList<>();
    }

    public String Name;
    public List<ClassInfo> Classes;

    public List<Class<?>> GetClasses() {
        var classes = new ArrayList<Class<?>>();

        for (var classInfo : Classes)
        {
            var cls = classInfo.GetClass();
            if (cls != null)
                classes.add(cls);
        }

        return classes;
    }
}
