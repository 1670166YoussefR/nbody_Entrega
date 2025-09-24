public class EulerIntegrator implements Integrator {
    @Override
    public void step(Universe u, double dt) {
        // Array de vectores para almacenar la fuerza que se aplica en cada cuerpo
        Body[] bodies = u.getBodies();
        int numBodies = u.getNumBodies();
        Vector[] f = new Vector[numBodies];
        for (int i = 0; i < numBodies; i++) {
            f[i] = new Vector(new double[2]);
        }
        // Bucle para cada par de cuerpos
        for (int i = 0; i < numBodies; i++) {
            for (int j = 0; j < numBodies; j++) {
                if (i != j) {
                    // Se suma la fuerza que el cuerpo j ejerce sobre el cuerpo i
                    f[i] = f[i].plus(bodies[i].forceFrom(bodies[j]));
                }
            }
        }
        // Una vez calculada todas las fuerzas, se mueve el cuerpo
        for (int i = 0; i < numBodies; i++) {
            // Aplicamos la fuerza total a cada cuerpo durante un intervalo de tiempo dt
            bodies[i].move(f[i], dt);
        }
    }
}

