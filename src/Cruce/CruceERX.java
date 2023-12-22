package Cruce;

import java.util.ArrayList;

import Geneticos.Individuo;

public class CruceERX extends Cruce {

	public CruceERX(ArrayList<Individuo<?>> poblacion, double pCruce, int tamPoblacion) {
		super(poblacion,pCruce,tamPoblacion);
	}


	public void cruzar(Individuo<?> individuo, Individuo<?> individuo2) {
		individuo.cruzaERX(individuo2);	
		
		individuo.setFitness(individuo.getFitness());
		individuo2.setFitness(individuo2.getFitness());
	}
	
}
