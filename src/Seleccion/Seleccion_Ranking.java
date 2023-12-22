package Seleccion;

import java.util.ArrayList;
import java.util.Random;

import Geneticos.Individuo;

public class Seleccion_Ranking {

	static private final double _beta = 1.5;
	
	public static void SeleccionRanking(ArrayList<Individuo<?>> poblacion, 	ArrayList<Individuo<?>> new_poblacion, int tamPoblacion) {
		
		//realizamos el ranking
		double accPunt = 0.0;
		for(int i = 0; i < tamPoblacion; i++) {
			double probOfIth= (double)i/tamPoblacion;
			probOfIth*= 2*(_beta-1);
			probOfIth= _beta -probOfIth;
			probOfIth= (double)probOfIth* ((double)1/tamPoblacion);
			poblacion.get(i).setPuntuacionAc(accPunt);
			poblacion.get(i).setPuntuacion(probOfIth);
			accPunt+= probOfIth;
		}
		
		Seleccion_Ruleta.SeleccionRuleta(poblacion, new_poblacion, tamPoblacion);
		
		
		
	}
	
}
