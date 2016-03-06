package domain;

public class Judet {

	
	private String denumire;
	private int numarTrupe;
	private boolean isCucerit;
	private Jucator jucator;

	public Judet(String denumire, int numarTrupe, boolean isCucerit, Jucator jucator) {
		super();
		this.denumire = denumire;
		this.numarTrupe = numarTrupe;
		this.isCucerit = isCucerit;
		this.jucator=jucator;
	}

	@Override
	public String toString() {
		return  denumire + " Numar Trupe Disponibile: " + numarTrupe ;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public int getNumarTrupe() {
		return numarTrupe;
	}

	public void setNumarTrupe(int numarTrupe) {
		this.numarTrupe = numarTrupe;
	}

	public boolean isCucerit() {
		return isCucerit;
	}

	public void setCucerit(boolean isCucerit) {
		this.isCucerit = isCucerit;
	}
	public Jucator getJucator() {
		return jucator;
	}

	public void setJucator(Jucator jucator) {
		this.jucator = jucator;
	}

}
