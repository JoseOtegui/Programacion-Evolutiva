package Seleccion;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class Seleccion_Torneo_Probabilista {


	public static void SeleccionTorneoProbabilista(ArrayList<Individuo<?>> poblacion, 	ArrayList<Individuo<?>> new_poblacion, int tamPoblacion, int participantes, double prob) {
		
		Random r = new Random();
		int aux;
		double fitness_mejor = 0, fitness_peor = Double.MAX_VALUE;
		//indice donde se encuentra el individuo ganador del torneo
		int ind_ganador = 0; //el ganador  puede ser el peor o el mejor segun la probabilidad
		Individuo<?> ind;
		
		for (int j = 0; j < tamPoblacion; j++) {
			
			//obtenemos un numero aleatorio entre 0 y 1
			//si es mayor que p gana el mejor si no el peor
			double d = r.nextDouble();
			
			//seleccionamos aleatoriamente los participantes del torneo y nos quedamos con el mejor y el peor
			for(int i = 0; i < participantes; i++) {
				aux  = (int)(Math.random()*tamPoblacion); //tomamos un entero entre 0 y tamPoblacion para seleccionar para el torneo
			
				if(d > prob) {
					if(poblacion.get(aux).getFitness() > fitness_mejor) {
						ind_ganador = aux;
						fitness_mejor = poblacion.get(aux).getFitness();
					}
				}
				else {
					if(poblacion.get(aux).getFitness() < fitness_peor) {
						ind_ganador = aux;
						fitness_peor = poblacion.get(aux).getFitness();
					}
				}
				
			}
			
			//añadimos el individuo vencedor a la nueva poblacion
			ind = poblacion.get(ind_ganador).copia();
			new_poblacion.add(ind);
			
			ind_ganador = 0;
			fitness_mejor = 0;
			fitness_peor = Double.MAX_VALUE;
		}

	}
}
