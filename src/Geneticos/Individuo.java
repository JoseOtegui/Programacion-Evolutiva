package Geneticos;

import java.util.Set;

public abstract class Individuo<T> {
	protected T[] cromosoma;
	protected int tamTotal;

	protected int[] tamGenes;
	protected double fitness, fitness_desp;
	protected double puntuacion; // fitness/suma
	protected double puntuacion_ac;

	
	public T[] getCromosoma() {
		return cromosoma;
	}
	
	public void setCromosoma(T[] cromosoma) {
		for(int i = 0; i < cromosoma.length;i++) {
			this.cromosoma[i] = cromosoma[i];
		}
	}
	
	public int getTamCromosoma() {
		return tamTotal;
	}
	
	public void setFitness(double f) {
		fitness = f;
	}
	
	public void setFitnessDesp(double f) {
		fitness_desp = f;
	}
	
	public double getFitnessDesp() {
		return fitness_desp;
	}
	
	
	public void setPuntuacion(double p) {
		puntuacion = p;
	}
	
	public void setPuntuacionAc(double pa) {
		puntuacion_ac = pa;
	}
	
	public abstract double getFitness();
	protected abstract void inicializaCromosoma();
	

	public double getPuntuacion() {
		return puntuacion;
	}
	public double getPuntuacionAcumulada() {
		return puntuacion_ac;
	}


	protected abstract void setCromosoma(int i, T aux);

	protected abstract String soltoString();

	protected abstract String soltoString1();

	public abstract Individuo<?> copia();

	public abstract void cruzaPMX(Individuo<?> individuo2, int puntoCorte1, int puntoCorte2);
	
	public abstract void cruzaOX(Individuo<?> individuo2, int puntoCorte1, int puntoCorte2);

	public abstract void cruzaCiclos(Individuo<?> individuo2);

	public abstract double getFenotipo();

	public abstract void cruzaERX(Individuo<?> individuo2);

	public abstract void cruzaCO(Individuo<?> individuo2, int puntoCruce);

	public abstract void mutaInversion(int puntoCorte1, int puntoCorte2);

	public abstract void mutaIntercambio(int p2, int p22);
	
	public abstract void mutaInsercion(Set<Integer> ciudades ,Set<Integer> posiciones);

	public abstract void mutaSimetrica(int p1);

	public abstract void mutaHeuristica(int n);

	public abstract void cruzaOXOrdenPri(Individuo<?> individuo2, Set<Integer> posiciones);

	public abstract void cruzaOXPosPri(Individuo<?> individuo2, Set<Integer> posiciones);

	
}

