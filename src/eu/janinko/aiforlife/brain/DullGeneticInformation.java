package eu.janinko.aiforlife.brain;

import java.util.Random;

public class DullGeneticInformation {
	private double breedery;
	
	private int genlen;
	private double[] dna;

	private Random generator = new Random();
	
	public DullGeneticInformation(int genlen, int length){
		
		this.genlen = genlen;
		
		if(length < 0){
			length = generator.nextInt(5)+5;
		}
		
		dna = new double[length*genlen];
		
		breedery = generator.nextDouble() / 4 + 0.475;
		
		for(int i = 0; i < length; i++){
			for(int j = 0; j < genlen; j++){
				dna[i*genlen+j] = generator.nextDouble()*4-2;
			}
		}
	}
	
	public DullGeneticInformation(int genlen){
		this(genlen, -1);
	}

	public DullGeneticInformation(DullGeneticInformation dna2) {
		this.breedery = dna2.getBreedery();
		this.genlen = dna2.getGenLen();

		int length = dna2.getLength();
		this.dna = new double[length * genlen];
		for(int i = 0; i < length; i++ ){
			for(int j = 0; j < genlen; j++){
				this.dna[i*genlen+j] = dna2.getGenInfo(i, j);
			}
		}
	}

	public double getBreedery() {
		return breedery;
	}

	public void setBreedery(double breedery) {
		this.breedery = breedery;
	}
	
	public int getGenLen(){
		return genlen;
	}
	
	public double getGenInfo(int g, int i){
		if(g >= this.getLength()) throw new IllegalArgumentException("Maximum g : " + (this.getLength() - 1) + " got: " + g);
		if(i >= genlen) throw new IllegalArgumentException("Maximum i : " + (genlen - 1) + " got: " + i);
		
		return dna[g*genlen+i];
	}
	
	public double[] getGen(int gen){
		double[] ret = new double[genlen];
		for(int i = 0; i<genlen; i++){
			ret[i] = dna[gen*genlen+i];
		}
		return ret;
	}
	
	public int getLength(){
		return this.dna.length / genlen;
	}

	public void setGenInfo(int g, int i, double d) {
		if(g >= this.getLength()) throw new IllegalArgumentException("Maximum g : " + (this.getLength() - 1) + " got: " + g);
		if(i >= genlen) throw new IllegalArgumentException("Maximum i : " + (genlen - 1) + " got: " + i);
		
		dna[g*genlen+i] = d;
	}
	
	public void appendGen(double[] gen){
		if(gen.length != genlen) throw new IllegalArgumentException("Exceptet length: " + genlen + " got: " + gen.length);
		
		double[] newdna = new double[dna.length + genlen];
		for(int i=0; i < dna.length; i++){
			newdna[i] = dna[i];
		}
		for(int i=0; i<genlen; i++){
			newdna[dna.length+i] = gen[i];
		}
		
		this.dna = newdna;
	}
	
	public void removeGen(int g){
		if(g >= this.getLength()) throw new IllegalArgumentException("Maximum : " + (this.getLength() - 1) + " got: " + g);
		if(this.getLength() <= 1) return;

		double[] newdna = new double[dna.length - genlen];
		
		for(int i=0, j=0; j< dna.length - genlen; i++, j++){
			if(i==g*genlen){
				i+=genlen;
			}
			newdna[j] = dna[i];
		}
		this.dna = newdna;
	}

}
