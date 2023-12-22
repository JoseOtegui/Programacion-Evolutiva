package Seleccion;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class Seleccion_Restos {
		public static void SeleccionRestos(ArrayList<Individuo<?>> poblacion, 	ArrayList<Individuo<?>> new_poblacion, int tamPoblacion) {
			int k = 120;
			ArrayList<Individuo<?>> poblacionRestos = new ArrayList<Individuo<?>>();
			int tamRestos = 0;
			double copias;
			double individuoAux;
			for(int i = 0; (i < tamPoblacion) && (new_poblacion.size() < tamPoblacion); i++) {
				individuoAux = poblacion.get(i).getPuntuacion();
				copias = individuoAux * k;
				if( copias >= 1) {
					int j = 0;
					while(j < (int)copias && new_poblacion.size() < tamPoblacion) {
						//lo aniadimos a la nueva poblacion
						int indice = i;
						Individuo<?> ind = poblacion.get(indice).copia();
						new_poblacion.add(ind);
						
						j++;
					}
					//Comentamos esta parte porque cogiendo varias copias de un solo individuo
					//se rellena por completo new_poblacion y no hay restos para poder rellenar con otra seleccion
						
				}
				else { //Guarda los elementos no tratados(restos) en otro array
					Individuo<?> indRestos = poblacion.get(i).copia();
					tamRestos ++;
					poblacionRestos.add(indRestos);
				}
			}
			
			//Cuando ya se ha acabado el bucle anterior inicia otra seleccion, en este caso ruleta, con los restos
			if(new_poblacion.size() < tamPoblacion) 
				Seleccion_Ruleta.SeleccionRuleta(poblacionRestos, new_poblacion, tamRestos);
			
	}
}
