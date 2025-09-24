import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Universe {
  private int numBodies;
  private double radius;
  private Body[] bodies;


  public double getRadius() { return radius; }
  public int getNumBodies() { return numBodies; }
  public Vector getPosition(int i) { return bodies[i].getPosition(); }
  public Body[] getBodies() { return bodies; }

  public Universe(String fname) {
    try {
      Scanner in = new Scanner(new FileReader(fname));
      numBodies = Integer.parseInt(in.next());
      // the set scale to draw on the canvas
      radius = Double.parseDouble(in.next());
      // read and make the n bodies
      bodies = new Body[numBodies];
      for (int i = 0; i < numBodies; i++) {
        double rx = Double.parseDouble(in.next());
        double ry = Double.parseDouble(in.next());
        double vx = Double.parseDouble(in.next());
        double vy = Double.parseDouble(in.next());
        double mass = Double.parseDouble(in.next());
        double[] position = {rx, ry};
        double[] velocity = {vx, vy};
        Vector r = new Vector(position);
        Vector v = new Vector(velocity);
        bodies[i] = new Body(r, v, mass);
        System.out.println(bodies[i]);
      }
    } catch (FileNotFoundException e) { e.printStackTrace(); }
  }

  public Universe(Body[] bodies, double radius) {
      this.numBodies = bodies.length;
      this.radius = radius;
      this.bodies = bodies;
  }
}