package champollion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Enseignant extends Personne {

	private final Set<Intervention> planification = new HashSet<>();
	private final Map<UE, ServicePrevu> enseignements = new HashMap<>();

	public Enseignant(String nom, String email) {
		super(nom, email);
	}

	public void ajouteIntervention(Intervention inter) {
		if (!enseignements.containsKey(inter.getMatiere())) {
			throw new IllegalArgumentException("La matière ne fait pas partie des enseignements");
		}

		planification.add(inter);
	}

	public int heuresPlanifiee() {
		float nbHeures = 0f;
		for (Intervention inter : planification) {
		switch (inter.getType()) {
			case CM:
				nbHeures = inter.getDuree() * 1.5f;
				break;
			case TD:
				nbHeures = inter.getDuree();
				break;
			case TP:
				nbHeures = inter.getDuree() * 0.75f;
				break;
		}
    }

		return Math.round(nbHeures);
	}

	
	public boolean enSousService() {
		return heuresPrevues() < 192;
	}


	/**
	 * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure de
	 * cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h "équivalent
	 * TD"
	 *
	 * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
	 *
	 */
	public int heuresPrevues() {
		float nbHeures = 0f;
		for (UE ue : enseignements.keySet()) {
			nbHeures += heuresPrevuesPourUE(ue);
		}
		return Math.round(nbHeures);
	}

	/**
	 * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour le
	 * calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut
	 * 0,75h "équivalent TD"
	 *
	 * @param ue l'UE concernée
	 * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant arrondi à l'entier le plus proche
	 *
	 */
	public int heuresPrevuesPourUE(UE ue) {
		float nbHeures = 0;

		ServicePrevu p = enseignements.get(ue);
		if (p != null) { // La clé existe, l'enseignant intervient dans l'UE
			nbHeures =  p.getVolumeCM()*1.5f + p.getVolumeTD() + p.getVolumeTP()*0.75f;
		}
		return Math.round(nbHeures);
	}

	/**
	 * Ajoute un enseignement au service prévu pour cet enseignant
	 *
	 * @param ue l'UE concernée
	 * @param volumeCM le volume d'heures de cours magitral (positif ou 0)
	 * @param volumeTD le volume d'heures de TD (positif ou 0)
	 * @param volumeTP le volume d'heures de TP (positif ou 0)
	 */
	public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
		if (volumeCM < 0 || volumeTD < 0 || volumeTP < 0) {
			throw new IllegalArgumentException("il ne peut pas y avoir un volume d'heures negatif");
		}

		ServicePrevu sPrevu = enseignements.get(ue);
		if (sPrevu == null) {
			sPrevu = new ServicePrevu(volumeCM, volumeTD, volumeTP, ue);
			enseignements.put(ue, sPrevu);
		} else {
			sPrevu.setVolumeCM(volumeCM + sPrevu.getVolumeCM());
			sPrevu.setVolumeTD(volumeTD + sPrevu.getVolumeTD());
			sPrevu.setVolumeTP(volumeTP + sPrevu.getVolumeTP());
		}
	}

	/**
	 * @return les UE dans lequel cet enseignant doit intervenir
	 */
	public Set<UE> UEPrevues() {
		// on renvoie l'ensemble des clés de la Map
		return enseignements.keySet();
	}

	public int resteAPlanifier(UE ue, TypeIntervention type) {
		float planifiees = 0f;
		ServicePrevu p = enseignements.get(ue);
		if (null == p) // L'enseignant n'a pas de service prévudans cette UE
			return 0;
		
		float aPlanifier = p.getVolumePour(type);

		for (Intervention inter : planification) {
			if ((ue.equals(inter.getMatiere())) && (type.equals(inter.getType()))) {
				planifiees += inter.getDuree();
			}
		}
		return Math.round(aPlanifier - planifiees);
	}

}
