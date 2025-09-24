public class LeapfrogIntegrator implements Integrator {
    @Override
    public void step (Universe u, double dt){
        int numBodies = u.getNumBodies();;
        Body[] bodies = u.getBodies();
        Vector[] forces = new Vector[numBodies];

        for (int i = 0; i < numBodies; i++) {
            forces[i] = new Vector(new double[2]);
            for(int j = 0; j < numBodies; j++){
                if(i != j){
                    forces[i] = forces[i].plus(bodies[i].forceFrom(bodies[j]));
                }
            }
        }

        //Aplicamos Leapfrog: actualizamos velocidades a mitad de paso
        for(int i = 0; i < numBodies; i++){
            Vector a = forces[i].scale(1.0/bodies[i].getMass());
            bodies[i].setVelocity(bodies[i].getBodyVelocity().plus(a.scale(dt/2.0)));
        }

        //Actualizamos las posiciones con las velocidades intermedias
        for (int i = 0; i < numBodies; i++) {
            bodies[i].setPosition(bodies[i].getBodyPosition().plus(bodies[i].getBodyVelocity().scale(dt)));
        }

        //Recalculamos las fuerzas con las posiciones actualizadas
        Vector[] newForces = new Vector[numBodies];
        for (int i = 0; i < numBodies; i++) {
            newForces[i] = new Vector(new double[2]);
            for (int j = 0; j < numBodies; j++) {
                if (i != j) {
                    newForces[i] = newForces[i].plus(bodies[i].forceFrom(bodies[j]));
                }
            }
        }

        //Actualizamos velocidades con la segunda mitad
        for (int i = 0; i < numBodies; i++) {
            Vector a = newForces[i].scale(1 / bodies[i].getMass());
            bodies[i].setVelocity(bodies[i].getBodyVelocity().plus(a.scale(dt / 2.0)));
        }
    }
}
