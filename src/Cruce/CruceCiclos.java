package Cruce;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class CruceCiclos extends Cruce{

	public CruceCiclos(ArrayList<Individuo<?>> poblacion, double pCruce, int tamPoblacion) {
		super(poblacion,pCruce,tamPoblacion);
	}


	public void cruzar(Individuo<?> individuo, Individuo<?> individuo2) {
		individuo.cruzaCiclos(individuo2);	
		
		individuo.setFitness(individuo.getFitness());
		individuo2.setFitness(individuo2.getFitness());
	}
		
}
