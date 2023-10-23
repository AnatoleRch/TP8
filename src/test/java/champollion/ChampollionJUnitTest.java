package champollion;

import java.util.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChampollionJUnitTest {
	Enseignant untel;
	UE uml, java;
		
	@BeforeEach
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		uml = new UE("UML");
		java = new UE("Programmation en java");		
	}
	

	@Test
	public void testNouvelEnseignantSansService() {
		assertEquals(0, untel.heuresPrevues(),
                        "Un nouvel enseignant doit avoir 0 heures prévues");
	}
	
	@Test
	public void testAjouteHeures() {
                // 10h TD pour UML
		untel.ajouteEnseignement(uml, 0, 10, 0);

		assertEquals(10, untel.heuresPrevuesPourUE(uml),
                        "L'enseignant doit maintenant avoir 10 heures prévues pour l'UE 'uml'");

                // 20h TD pour UML
                untel.ajouteEnseignement(uml, 0, 20, 0);
                
		assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml),
                         "L'enseignant doit maintenant avoir 30 heures prévues pour l'UE 'uml'");		
		
	}
	
	@Test
	void calculDeAPlanifier() {
		untel.ajouteEnseignement(uml, 0, 10, 0);
		assertEquals(10, untel.resteAPlanifier(uml, TypeIntervention.TD),"La fonction reste a planifier ne retourne pas le bon nombre d'heures");
		Intervention inter = new Intervention(1, new Date(), untel, uml, TypeIntervention.TD, null);
		untel.ajouteIntervention(inter);
		assertEquals(9, untel.resteAPlanifier(uml, TypeIntervention.TD),"La fonction resteAPlanifier ne retourne pas le bon nombre d'heures");
	}

	@Test
	void heuresPlanifiees() {
		assertEquals(0, untel.heuresPlanifiee());
		untel.ajouteEnseignement(uml, 0, 10, 0);
		Intervention inter = new Intervention(1, new Date(), untel, uml, TypeIntervention.TD, null);
		untel.ajouteIntervention(inter);
		assertEquals(1, untel.heuresPlanifiee(),"La fonction heuresPlanifiee ne retourne pas le bon nombre d'heures");
	}
			
	@Test
	void ajoutInterventionSansServicePrevu() {
		// L'enseignant a un service prévu de 10h donc on le remplit
		untel.ajouteEnseignement(uml, 0, 10, 0);
		Intervention inter = new Intervention(1, new Date(), untel, java, TypeIntervention.TD, null);
		
		try {
			// si l'enseignant n'a plus d'heures de service prévu
			untel.ajouteIntervention(inter);
			fail("L'enseignant n'a pas de service prévu dans cette UE");
		} catch (IllegalArgumentException ex) {
		}
	}
	
	@Test
	void nouvelEnseignantCréeSansUE() {
		assertTrue( untel.UEPrevues().isEmpty(),"L'enseignant ne devrait pas avoir d'UE car li vient d'etre crée" );
	}

	@Test
	void enSousService() {
		assertTrue( untel.enSousService() ,"L'enseignant devrait etre en sous service");
	}
	
	@Test
	void negatifInterdit() {
		try {
			untel.ajouteEnseignement(uml, 0, -1, 0);
			fail("il ne devrait pas etre possible d'ajouter un volume d'heure négatif");
		} catch (IllegalArgumentException ex) {
		}
	}

	@Test
	void calculeHeureService() {
		untel.ajouteEnseignement(uml, 0, 10, 0);
		untel.ajouteEnseignement(uml, 20, 0, 0);	// 20CM -> 30TD
		untel.ajouteEnseignement(java, 0, 0, 20);// 20TP -> 15TD
	   assertEquals(10 + 30 + 15, untel.heuresPrevues(),"heure prevue ne calcule pas correctement le service");		
	}
	
}

