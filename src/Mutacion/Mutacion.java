package Mutacion;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public abstract class Mutacion {
	
	public Mutacion(ArrayList<Individuo<?>> poblacion, double pMut, int tamPob) {
		
		Random r = new Random();
		double d;
		boolean mutado = false;
		
		for(int i = 0; i < tamPob; i++) {
			Individuo<?> ind = poblacion.get(i);
		
			d = r.nextDouble();
				
			if(d < pMut) {
				muta(ind);
				ind.setFitness(ind.getFitness());
			}
		}
	}
	//muta segun el tipo de cruce
	public abstract void muta(Individuo<?> ind);
}
