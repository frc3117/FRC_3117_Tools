package frc.robot.Library.FRC_3117_Tools.Physics;

public class SceneObject 
{
    private SceneObject(Transform transform)
    {
        Transform = transform;
    }

    public Transform Transform;

    public static SceneObject Instantiate(SceneObject sceneObject)
    {
        return new SceneObject(new Transform());
    }
}
