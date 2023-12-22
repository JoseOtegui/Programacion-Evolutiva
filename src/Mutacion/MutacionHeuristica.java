package Mutacion;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class MutacionHeuristica extends Mutacion {

	public MutacionHeuristica(ArrayList<Individuo<?>> poblacion, double pMut, int tamPoblacion) {
		super(poblacion,pMut,tamPoblacion);
	}

	public void muta(Individuo<?> individuo) {
		
		//numero de ciudades a permutar 
		int n = 3;
		
		individuo.mutaHeuristica(n);
	}
	
}
