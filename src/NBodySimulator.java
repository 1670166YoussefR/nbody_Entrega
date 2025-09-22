public class NBodySimulator {
  private final Universe universe;
  private final double timeStep;
  private final int pauseTime;
  private final boolean trace;

  //Constructor del NBodySimulator que recibe el universo y los parametros de la simulacion
  public NBodySimulator(Universe universe, double dt, int pt, boolean doTrace) {
    this.universe = universe;
    this.timeStep = dt;
    this.pauseTime = pt;
    this.trace = doTrace;
  }

  //Crea el canvas donde se va a dibujar
  private void createCanvas() {
    StdDraw.enableDoubleBuffering();
    StdDraw.setPenRadius(0.025);
    double radius = universe.getRadius();
    StdDraw.setXscale(-radius, +radius);
    StdDraw.setYscale(-radius, +radius);
  } //

  // Dibuja todos los cuerpos del universo
  private void drawUniverse() {
    int n = universe.getNumBodies();
    for (int i = 0; i < n; i++) {
      Vector p = universe.getPosition(i);
      StdDraw.point(p.cartesian(0), p.cartesian(1));
    }
  }

  // Bucle principal de la simulación
  public void simulate() {
    createCanvas();
    if (trace) StdDraw.clear(StdDraw.GRAY);
    while (true) {
      if (!trace) StdDraw.clear();
      universe.update(timeStep);
      drawUniverse();
      StdDraw.show();
      StdDraw.pause(pauseTime);
    }
  }
}
