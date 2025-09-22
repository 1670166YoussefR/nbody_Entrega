public class Main {
  public static void main(String[] args) {
    int numargs = args.length;
    assert numargs == 4 : "invalid number of arguments";

    double dt = Double.parseDouble(args[0]);
    int pauseTime = Integer.parseInt(args[1]);
    boolean trace = args[2].toLowerCase().equals("trace");
    String fname = args[3];

    Universe universe = new Universe(fname);
    NBodySimulator simulator = new NBodySimulator(universe, dt, pauseTime, trace);
    simulator.simulate();
  }
}
