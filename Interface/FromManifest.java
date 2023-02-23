package frc.robot.Library.FRC_3117_Tools.Interface;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FromManifest
{
    String EntryName();
}
