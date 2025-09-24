public class Main {
  public static void main(String[] args) {

    double dt = Double.parseDouble(args[0]);
    int pauseTime = Integer.parseInt(args[1]);
    boolean trace = args[2].toLowerCase().equals("trace");
    String mode = args[3].toLowerCase();

    Universe universe = null;

    switch (mode) {
      case "file":
          String fname = args[4];
          universe = UniverseFactory.makeUniverseFromFile(fname);
          break;

      case "central":
          int numBodies = Integer.parseInt(args[4]);
          double angleVelPos = Double.parseDouble(args[5]);
          universe = UniverseFactory.makeCentralConfiguration(numBodies, angleVelPos);
          break;

      case "planetary":
          int numPlanets = Integer.parseInt(args[4]);
          universe = UniverseFactory.makePlanetaryConfiguration(numPlanets);
          break;

      case "choreography":
          String choreoFile = args[4];
          int nchoreography = Integer.parseInt(args[5]);
          universe = UniverseFactory.makeChoreography(choreoFile, nchoreography);
          break;

      default:
          System.out.println("Configuración no reconocida: " + mode);
          return;
    }

    String integratorArg = args[args.length - 1].toLowerCase();
    Integrator integrator;
    if (integratorArg.equals("euler")) {
        integrator = new EulerIntegrator();
    }
    else if (integratorArg.equals("leapfrog")) {
        integrator = new LeapfrogIntegrator();
    }
    else{
        System.out.println("Integrador no disponible o no existente");
        return;
    }

    NBodySimulator simulator = new NBodySimulator(universe, dt, pauseTime, trace, integrator);
    simulator.simulate();
  }
}
