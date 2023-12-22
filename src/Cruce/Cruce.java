package Cruce;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public abstract class Cruce {
	public Cruce(ArrayList<Individuo<?>> arrayList, double pCruce, int tamPob) {
		
		Random r = new Random();
		//aray de indices de los dos individuos que toca cruzar en la poblacion
		int[] sel_cruce = new int[2]; 
		//contador seleccionados para cruzar
		int num_sel_cruce = 0;
		
		double d;
		
		for(int i = 0; i < tamPob; i++) {
			d = r.nextDouble();
			//comprobamos si el individuo i se cruza o no
			if(d < pCruce) {
				sel_cruce[num_sel_cruce] = i;
				num_sel_cruce++;
			}
			//si ya tenemos 2 para cruzar, los cruzamos
			if(num_sel_cruce == 2) {
				//cruzamos los dos ultimos individuos seleccionados 
				cruzar(arrayList.get(sel_cruce[0]), arrayList.get(sel_cruce[1]));
				num_sel_cruce = 0;
			}
		}
	}
	
	////cruza segun el tipo del cruce: OX, PMX...
	//evaluamos
	public abstract void cruzar(Individuo<?> individuo, Individuo<?> individuo2);
}
