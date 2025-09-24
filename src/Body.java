/******************************************************************************
 *  Compilation:  javac Body.java
 *  Execution:    java Body
 *  Dependencies: Vector.java StdDraw.java
 *
 *  Implementation of a 2D Body with a position, velocity and mass.
 *
 *
 ******************************************************************************/

public class Body {
  private Vector r;           // position
  private Vector v;           // velocity
  private final double mass;  // mass
  private final double G;
  private static final double DEFAULT_G = 6.67e-11;
  public Vector getBodyPosition() { return r; }
  public Vector getBodyVelocity() { return v; }
  public void setPosition(Vector r) { this.r = r; }
  public void setVelocity(Vector v) { this.v = v; }
  public double getMass() { return mass; }



    public Body(Vector r, Vector v, double mass) {
        this(r, v, mass, DEFAULT_G);
    }
    public Body(Vector r, Vector v, double mass, double G) {
        this.r = r;
        this.v = v;
        this.mass = mass;
        this.G = G;
    }

    public void move(Vector f, double dt) {
        Vector a = f.scale(1/mass);
        v = v.plus(a.scale(dt));
        r = r.plus(v.scale(dt));
    }

    public Vector forceFrom(Body b) {
        Body a = this;
        Vector delta = b.r.minus(a.r);
        double dist = delta.magnitude();
        double magnitude = (this.G * a.mass * b.mass) / (dist * dist);
        return delta.direction().scale(magnitude);
    }

    @Override
    public String toString() {
        return "position "+r.toString()+", velocity "+v.toString() + ", mass "+mass;
    }
}
