package frc.robot.Library.FRC_3117_Tools.Reflection;

import java.lang.module.ModuleFinder;

public class ClassInfo {
    public ClassInfo(String path) {
        Path = path;
    }

    public String Path;

    public Class<?> GetClass() {
        try { return Class.forName(Path); }
        catch (Exception e) { return null; }
    }
}
