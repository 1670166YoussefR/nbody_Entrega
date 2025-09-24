import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class UniverseFactory {

    // Configuración 1: crear un universo leyendo los datos de un fichero (ej: 3body.txt)
    public static Universe makeUniverseFromFile(String fname) {
        return new Universe(fname);
    }

    // Configuración 2: crear n cuerpos de igual masa, dispuestos en círculo,
    // equiespaciados angularmente y con velocidad tangencial (central configuration).
    public static Universe makeCentralConfiguration(int numBodies, double angleVelPos) {
        final double RADIUS = 1e11;        // radio global del universo
        final double GAMMA = 5e-5;         // parámetro para calcular velocidad
        Body[] bodies = new Body[numBodies];
        final double MASS = 1e33;          // masa de cada cuerpo
        final double DISTANCE = 0.4 * RADIUS; // distancia desde el centro al círculo
        double velocityMagnitude = GAMMA * DISTANCE; // magnitud de la velocidad

        for (int i = 0; i < numBodies; i++) {
            // ángulo de colocación de cada cuerpo en el círculo
            double anglePos = (2 * Math.PI * i) / (numBodies);

            // coordenadas cartesianas a partir del ángulo
            double rx = DISTANCE * Math.cos(anglePos);
            double ry = DISTANCE * Math.sin(anglePos);

            // velocidades tangenciales (girando respecto al centro)
            double vx = velocityMagnitude * Math.cos(anglePos + angleVelPos);
            double vy = velocityMagnitude * Math.sin(anglePos + angleVelPos);

            // crear cuerpo con posición (r), velocidad (v) y masa (MASS)
            bodies[i] = new Body(new Vector(new double[]{rx, ry}),
                    new Vector(new double[]{vx, vy}), MASS);
        }
        return new Universe(bodies, RADIUS);
    }

    // Configuración 3: Planetaria (n+1).
    // Un cuerpo muy masivo en el centro (como un Sol) + n planetas alrededor con masas menores.
    public static Universe makePlanetaryConfiguration(int numPlanets) {
        final double MAX_VELOCITY = 1e05;
        final double MIN_VELOCITY = 1e04;
        final double MIN_MASS = 1e20;
        final double MAX_MASS = 1e30;
        final double RADIUS = 1e12;

        int numBodies = numPlanets + 1;
        Body[] bodies = new Body[numBodies];

        // cuerpo central muy masivo (el “Sol”)
        final double MASS = 1e39;
        bodies[0] = new Body(new Vector(new double[2]), new Vector(new double[2]), MASS);

        // generación de planetas con posiciones, velocidades y masas aleatorias
        for (int i = 1; i < numBodies; i++) {
            // ángulo aleatorio
            double angle = Math.random() * 2 * Math.PI - Math.PI;
            // distancia radial aleatoria (simplificada)
            double rho = (RADIUS / 4.0) + Math.random() * (RADIUS / 2.0 - RADIUS / 4.0);

            // posición (rx, ry)
            double rx = Math.cos(angle) * rho;
            double ry = Math.sin(angle) * rho;

            // velocidades iniciales aproximadas para órbitas
            double vx = -ry / 1000.0 + (MIN_VELOCITY + Math.random() * (MAX_VELOCITY - MIN_VELOCITY));
            double vy = rx / 1000.0 + (MIN_VELOCITY + Math.random() * (MAX_VELOCITY - MIN_VELOCITY));

            // masa aleatoria del planeta
            double mass = MIN_MASS + Math.random() * (MAX_MASS - MIN_MASS);

            // crear planeta
            bodies[i] = new Body(new Vector(new double[]{rx, ry}),
                    new Vector(new double[]{vx, vy}), mass);
        }
        return new Universe(bodies, RADIUS);
    }

    // Configuración 4: Coreografías.
    // Los cuerpos se colocan siguiendo condiciones iniciales de un fichero de coreografías
    // (ej: simo-initial-conditions.txt). Cada coreografía es un conjunto de 3 cuerpos
    // con posiciones y velocidades específicas.
    public static Universe makeChoreography(String fname, int nchoreography) {
        final int NUM_CHOREOGRAPHIES = 345;
        assert (nchoreography >= 1) && (nchoreography <= NUM_CHOREOGRAPHIES);

        double c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0;
        try {
            Scanner in = new Scanner(new FileReader(fname));
            // avanzar hasta leer la coreografía seleccionada
            for (int i = 1; i <= nchoreography; i++) {
                c1 = Double.parseDouble(in.next());
                c2 = Double.parseDouble(in.next());
                c3 = Double.parseDouble(in.next());
                c4 = Double.parseDouble(in.next());
                c5 = Double.parseDouble(in.next());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // posiciones iniciales de los 3 cuerpos
        Vector r1 = new Vector(new double[]{-2 * c1, 0});
        Vector r2 = new Vector(new double[]{c1, c2});
        Vector r3 = new Vector(new double[]{c1, -c2});

        // velocidades iniciales de los 3 cuerpos
        Vector v1 = new Vector(new double[]{0, -2 * c4});
        Vector v2 = new Vector(new double[]{c3, c4});
        Vector v3 = new Vector(new double[]{-c3, c4});


        double radius = 0.5;     // radio de escala del universo
        int numBodies = 3;
        Body[] bodies = new Body[numBodies];
        double mass = 1.0 / 3.0; // masa repartida entre los 3 cuerpos
        double G = 1.0;

        // creación de los 3 cuerpos coreográficos
        bodies[0] = new Body(r1, v1, mass, G);
        bodies[1] = new Body(r2, v2, mass, G);
        bodies[2] = new Body(r3, v3, mass, G);

        return new Universe(bodies, radius);
    }
}
