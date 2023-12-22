package Seleccion;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class Seleccion_Estocastico {
	public static void SeleccionEstocastico(ArrayList<Individuo<?>> poblacion, 	ArrayList<Individuo<?>> new_poblacion, int tamPoblacion) {
		int[] sel_super = new int [tamPoblacion];// array de indices de los individuos seleccionados para sobrevivir
		int pos_super; // posicion del superviviente
		
		Random r = new Random();
		
		double range =1.0/tamPoblacion;
		double random = r.nextDouble();
	   
		double marca  = 0 + random*range; // primera marca
		pos_super = 0;
		for(int i = 0; i < tamPoblacion; i++) {
			
			while((marca > poblacion.get(pos_super).getPuntuacionAcumulada())&&(pos_super < tamPoblacion)) {
				pos_super++;
			}
			sel_super[i] = pos_super;
			marca = marca + (1.0/tamPoblacion);
		}
		for(int i = 0; i < tamPoblacion; i++) {
			int indice = sel_super[i];
			Individuo<?> ind = poblacion.get(indice).copia();
			new_poblacion.add(ind);
		}
	}

}
