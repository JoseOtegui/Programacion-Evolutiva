package Seleccion;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class Seleccion_Ruleta {


	public static void SeleccionRuleta(ArrayList<Individuo<?>> poblacion, 	ArrayList<Individuo<?>> new_poblacion, int tamPoblacion) {
		
	
		int[] sel_super = new int [tamPoblacion];// array de indices de los individuos seleccionados para sobrevivir
		double prob; // probabilidad de seleccion
		int pos_super; // posicion del superviviente
		for(int i = 0; i < tamPoblacion; i++) {
			Random r = new Random();
			prob = r.nextDouble();
			pos_super = 0;
			
			while((pos_super < tamPoblacion)&&(prob > poblacion.get(pos_super).getPuntuacionAcumulada())) {
					pos_super++;
			}		
			if (pos_super == tamPoblacion) pos_super--;
			sel_super[i] = pos_super;
		}
		for(int i = 0; i < tamPoblacion; i++) {
			int indice = sel_super[i];
			Individuo<?> ind = poblacion.get(indice).copia();
			new_poblacion.add(ind);
		}
	}

}

