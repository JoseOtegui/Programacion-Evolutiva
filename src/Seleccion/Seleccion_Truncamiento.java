package Seleccion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Geneticos.Individuo;


public class Seleccion_Truncamiento {
	//recibe a los individuos ordenados por firness
	public static void SeleccionTruncamiento(ArrayList<Individuo<?>> poblacion, ArrayList<Individuo<?>> new_poblacion, int tamPoblacion) {
		int[] sel_super = new int [tamPoblacion];// array de indices de los individuos seleccionados para sobrevivir
		
		double trunc = 0.5; // valor por defecto de 0.5 (es muy elitista)
		int seleccionados =(int) Math.ceil(trunc*tamPoblacion), contador = 0;
		
		for(int i = tamPoblacion-1; i >= seleccionados; i--) {
			//escogemos el individuo i varias veces
			for(int j = 0; j < tamPoblacion/(trunc*100);j++) {
				if(contador < tamPoblacion) {
				sel_super[contador] = i;
				contador++;
				}
			}
		}
		for(int i = 0; i < tamPoblacion; i++) {
			int indice = sel_super[i];
			Individuo<?> ind = poblacion.get(indice).copia();
			new_poblacion.add(ind);
		}
	}
}