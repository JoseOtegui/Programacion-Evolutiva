package Cruce;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class CruceOX extends Cruce{
	
	public CruceOX(ArrayList<Individuo<?>> poblacion, double pCruce, int tamPoblacion) {
		super(poblacion,pCruce,tamPoblacion);
	}
	
	
	public  void cruzar(Individuo<?> individuo, Individuo<?> individuo2) {
		Random r = new Random();
		
		int puntoCorte1 = r.nextInt(individuo.getTamCromosoma());
		int puntoCorte2 = r.nextInt(individuo.getTamCromosoma());
		
		//numero aleatorio entre 0 y tama�o del cromosoma 
		while(Math.abs(puntoCorte1-puntoCorte2) == 0) {
			puntoCorte1 = r.nextInt(individuo.getTamCromosoma());
			puntoCorte2 = r.nextInt(individuo.getTamCromosoma());
		}
		
		
		
		if(puntoCorte1>puntoCorte2)
			individuo.cruzaOX(individuo2, puntoCorte2, puntoCorte1);
		else individuo.cruzaOX(individuo2, puntoCorte1, puntoCorte2);
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
