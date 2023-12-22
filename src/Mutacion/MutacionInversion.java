package Mutacion;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class MutacionInversion extends Mutacion {

	public MutacionInversion(ArrayList<Individuo<?>> poblacion, double pMut, int tamPoblacion) {
		super(poblacion,pMut,tamPoblacion);
	}

	public void muta(Individuo<?> individuo) {
		Random r = new Random();
		//numero aleatorio entre 0 y tamaño del cromosoma 
		int puntoCorte1 = r.nextInt(individuo.getTamCromosoma());
		int puntoCorte2 = r.nextInt(individuo.getTamCromosoma());
		
		if(puntoCorte1 < puntoCorte2)
			individuo.mutaInversion(puntoCorte1,puntoCorte2);
		else 
			individuo.mutaInversion(puntoCorte2,puntoCorte1);
	}
	
	
}
