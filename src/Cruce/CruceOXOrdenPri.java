package Cruce;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

import Geneticos.Individuo;

public class CruceOXOrdenPri extends Cruce{

	public CruceOXOrdenPri(ArrayList<Individuo<?>> poblacion, double pCruce, int tamPoblacion) {
		super(poblacion,pCruce,tamPoblacion);
	}
	
	public  void cruzar(Individuo<?> individuo, Individuo<?> individuo2) {
		Random r = new Random();
		
		//numero de posiciones a intercambiar
		int numPosiciones = r.nextInt(individuo.getTamCromosoma()/2);
		Set<Integer> posiciones = new HashSet<>();
		
		//crea un array de posiciones que seran intercambiadas
		//utilizamos la estructura set para asegurarnos de que seran distintos
		while(posiciones.size() < numPosiciones) {
			int num = r.nextInt(individuo.getTamCromosoma());
			posiciones.add(num);
		}
		
		individuo.cruzaOXOrdenPri(individuo2, posiciones);
		
		//evaluamos
		
		try {
		individuo.setFitness(individuo.getFitness());
		individuo2.setFitness(individuo2.getFitness());
		}
		catch(Exception e) {
			System.out.println("Error");
		}
	}
}
