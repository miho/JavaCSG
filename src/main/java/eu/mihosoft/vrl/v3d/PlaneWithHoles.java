/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.v3d;

import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class PlaneWithHoles {

    public CSG toCSG() {
        CSG result = new Cube(Vector3d.ZERO, new Vector3d(30, 30, 1)).toCSG();
        
        CSG spheres = null;

        for (int y = 0; y < 3; y++) {
            
            System.out.println("line: " + y);
            
            for (int x = 0; x < 11; x++) {
                
                double radius = 1;
                double spacing = 0.25;
                
                CSG sphere = new Cylinder(radius,1,16).toCSG().transformed(
                        Transform.unity().translate((x-5)*(radius*2+spacing), (y-5)*(radius*2+spacing), -0.5));
                
                result = result.difference(sphere);
                
                if (spheres ==null) {
                    spheres = sphere;
                } else {
                    spheres = spheres.union(sphere);
                }
                
            }
        }
        
        System.out.println(">> final diff");
        
        //result = result.difference(spheres);

        return result;
    }

    public static void main(String[] args) throws IOException {

        PlaneWithHoles planeWithHoles = new PlaneWithHoles();

        // save union as stl
//        FileUtil.write(Paths.get("sample.stl"), new ServoHead().servoHeadFemale().transformed(Transform.unity().scale(1.0)).toStlString());
        FileUtil.write(Paths.get("sample.stl"), planeWithHoles.toCSG().toStlString());

    }
}
