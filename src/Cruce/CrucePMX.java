package Cruce;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class CrucePMX extends Cruce{

	public CrucePMX(ArrayList<Individuo<?>> poblacion, double pCruce, int tamPoblacion) {
		super(poblacion,pCruce,tamPoblacion);
	}


	public void cruzar(Individuo<?> individuo, Individuo<?> individuo2) {
		
		Random r = new Random();
		//numero aleatorio entre 0 y tamaño del cromosoma 
		int puntoCorte1 = r.nextInt(individuo.getTamCromosoma());
		int puntoCorte2 = r.nextInt(individuo.getTamCromosoma());
		
		if(puntoCorte1>puntoCorte2)
			individuo.cruzaPMX(individuo2, puntoCorte2, puntoCorte1);
		else individuo.cruzaPMX(individuo2, puntoCorte1, puntoCorte2);
		//evaluamos
		individuo.setFitness(individuo.getFitness());
		individuo2.setFitness(individuo2.getFitness());
		
	}
		
}
