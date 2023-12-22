package Mutacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Geneticos.Individuo;

public class MutacionInsercion extends Mutacion {

	public MutacionInsercion(ArrayList<Individuo<?>> poblacion, double pMut, int tamPoblacion) {
		super(poblacion,pMut,tamPoblacion);
	}

	public void muta(Individuo<?> ind) {
		Random r = new Random();
		//numero aleatorio entre 0 y tama�o del cromosoma de inserciones

		int numInserciones = r.nextInt(ind.getTamCromosoma());

		while(numInserciones < 1)
			numInserciones = r.nextInt(ind.getTamCromosoma());

		Set<Integer> ciudades = new HashSet<>();
		
		while(ciudades.size() < numInserciones) {
			int num = r.nextInt(ind.getTamCromosoma());
			ciudades.add(num);
		}
		
		//posiciones al azar donde serán insertadas las ciudades elegidas tambien al azar
		
		Set<Integer> posiciones = new HashSet<>();
		while(posiciones.size() < numInserciones) {
			int num = r.nextInt(ind.getTamCromosoma());
			if(!ciudades.contains(num))
				posiciones.add(num);
		}
		
		ind.mutaInsercion(ciudades, posiciones);
	}
	
}
