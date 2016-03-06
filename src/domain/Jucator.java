package domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Jucator {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaJudete == null) ? 0 : listaJudete.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jucator other = (Jucator) obj;
		if (listaJudete == null) {
			if (other.listaJudete != null)
				return false;
		} else if (!listaJudete.equals(other.listaJudete))
			return false;
		return true;
	}

	public Map<String, Judet> listaJudete = new LinkedHashMap<String, Judet>();

	//Metoda de calcul total numar trupe pentru un jucator
	public int totalTrupejucator() {
		int trupe = 0;
		for (String den : listaJudete.keySet()) {
			trupe += listaJudete.get(den).getNumarTrupe();
		}

		return trupe;
	}
	
}
