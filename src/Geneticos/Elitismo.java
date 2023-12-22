package Geneticos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Elitismo {

	
	//incluye a la elite en la poblacion eliminando los dos peores
		public static void incluyeElite(AlgoritmoGenetico AG, ArrayList<Individuo<?>> elite) {
			//ordenamos la poblacion por fitness
			
			
			
			for(int i = 0; i < elite.size(); i++) {
				Individuo<?> ind =  elite.get(i).copia();
				AG.getPoblacion().set(i, ind );
			}		
			
		}

		//funcion que guarda en elite los tam_elite individuos mejores de la poblacion
		public static void elitismoMejores(ArrayList<Individuo<?>> elite,double tam_elite, AlgoritmoGenetico AG) {
			//ordenamos la poblacion por fitness
			
			
			
			//seleccionamos los individuos de la elite
			int tamPob = AG.getTamPoblacion();
			int inds_elite =  (int) (tam_elite*tamPob);
			
			for(int i = tamPob-inds_elite; i < tamPob; i++) {
				Individuo<?> ind =  AG.getPoblacion().get(i).copia();
				elite.add(ind);
			}
			
		}
}
