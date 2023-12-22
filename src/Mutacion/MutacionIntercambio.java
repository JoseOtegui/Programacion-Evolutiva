package Mutacion;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class MutacionIntercambio extends Mutacion {

	public MutacionIntercambio(ArrayList<Individuo<?>> poblacion, double pMut, int tamPoblacion) {
		super(poblacion,pMut,tamPoblacion);
	}

	public void muta(Individuo<?> individuo) {
		Random r = new Random();
		//numero aleatorio entre 0 y tamaño del cromosoma 
		int p1 = r.nextInt(individuo.getTamCromosoma());
		int p2 = r.nextInt(individuo.getTamCromosoma());
		
		individuo.mutaIntercambio(p1,p2);
	}
	
}
