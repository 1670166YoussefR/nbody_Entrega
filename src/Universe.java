import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Universe {
  private int numBodies;
  private double radius;
  private Body[] bodies;

  // --- Constructor (del enunciado)
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
  } //

  // --- Getters usados por el simulador/dibujo
  public double getRadius() { return radius; }       // para escalar el canvas
  public int getNumBodies() { return numBodies; }
  public Vector getBodyPosition(int i) { return bodies[i].getPosition(); }
  public Vector getBodyVelocity(int i) { return bodies[i].getVelocity(); }
  public double getBodyMass(int i) { return bodies[i].getMass(); }

  // --- Setters por si los necesitas después
  public void setBodyPosition(int i, Vector p) { bodies[i].setPosition(p); }
  public void setBodyVelocity(int i, Vector v) { bodies[i].setVelocity(v); }

  // --- update(dt): calcula fuerzas (doble bucle) y mueve cada cuerpo
  public void update(double dt) {
    // 1) inicializa fuerzas a 0
    Vector[] f = new Vector[numBodies];
    for (int i = 0; i < numBodies; i++) {
      f[i] = new Vector(new double[2]);
    }
    // 2) acumula fuerzas de todos sobre cada i (i != j)
    for (int i = 0; i < numBodies; i++) {
      for (int j = 0; j < numBodies; j++) {
        if (i != j) {
          f[i] = f[i].plus(bodies[i].forceFrom(bodies[j]));
        }
      }
    }
    // 3) mueve cada cuerpo con su fuerza total
    for (int i = 0; i < numBodies; i++) {
      bodies[i].move(f[i], dt);
    }
  } // (estructura igual que el ejemplo original de increaseTime)
}
