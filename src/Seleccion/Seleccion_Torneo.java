package Seleccion;

import java.util.ArrayList;

import Geneticos.Individuo;


public class Seleccion_Torneo {


	public static void SeleccionTorneo(ArrayList<Individuo<?>> poblacion, 	ArrayList<Individuo<?>> new_poblacion, int tamPoblacion, int participantes) {

		int aux;
		double fitness_mejor = 0;
		//indice donde se encuentra el individuo mejor del torneo
		int ind_mejor = 0;
		Individuo<?> ind;
		
		for (int j = 0; j < tamPoblacion; j++) {
			//seleccionamos aleatoriamente los participantes del torneo y nos quedamos con el mejor
			for(int i = 0; i < participantes; i++) {
				aux  = (int)(Math.random()*tamPoblacion); //tomamos un entero entre 0 y tamPoblacion para seleccionar para el torneo
			
				if(poblacion.get(aux).getFitness() > fitness_mejor) {
					ind_mejor = aux;
					fitness_mejor = poblacion.get(aux).getFitness();
				}	
				
			}
			
			//añadimos el individuo vencedor a la nueva poblacion
			ind = poblacion.get(ind_mejor).copia();
			new_poblacion.add(ind);
			
			ind_mejor = 0;
			fitness_mejor = 0;
		}

	}
}