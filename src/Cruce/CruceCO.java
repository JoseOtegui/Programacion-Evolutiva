package Cruce;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class CruceCO extends Cruce {

	public CruceCO(ArrayList<Individuo<?>> poblacion, double pCruce, int tamPoblacion) {
		super(poblacion,pCruce,tamPoblacion);
	}


	public void cruzar(Individuo<?> individuo, Individuo<?> individuo2) {
		
		Random r = new Random();
		//numero aleatorio entre 0 y tamaño del cromosoma 
		int puntoCruce = r.nextInt(individuo.getTamCromosoma());	
		
		individuo.cruzaCO(individuo2, puntoCruce);	
		
		individuo.setFitness(individuo.getFitness());
		individuo2.setFitness(individuo2.getFitness());
	}
	
}