public class NBodySimulator {
  private final Universe universe;
  private final double timeStep;
  private final int pauseTime;
  private final boolean trace;

  public NBodySimulator(Universe universe, double dt, int pt, boolean doTrace) {
    this.universe = universe;
    this.timeStep = dt;
    this.pauseTime = pt;
    this.trace = doTrace;
  } //

  private void createCanvas() {
    // StdDraw.setCanvasSize(700, 700); // opcional
    StdDraw.enableDoubleBuffering();
    StdDraw.setPenRadius(0.025);
    double radius = universe.getRadius();      // del fichero (2ª línea)
    StdDraw.setXscale(-radius, +radius);
    StdDraw.setYscale(-radius, +radius);
  } //

  private void drawUniverse() {
    int n = universe.getNumBodies();
    for (int i = 0; i < n; i++) {
      Vector p = universe.getBodyPosition(i);
      StdDraw.point(p.cartesian(0), p.cartesian(1));
    }
  } // (esto es lo que pedía completar)

  public void simulate() {
    createCanvas();
    if (trace) StdDraw.clear(StdDraw.GRAY); // fondo gris para traza
    while (true) {
      if (!trace) StdDraw.clear();        // sin traza, limpiamos cada frame
      universe.update(timeStep);          // actualizar posiciones
      drawUniverse();                     // dibujar
      StdDraw.show();
      StdDraw.pause(pauseTime);           // puede ser 0
    }
  } // (estructura del bucle según guía del enunciado)
}
