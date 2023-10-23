package champollion;

import java.util.Date;

public class Intervention {

    private int duree;
    private Date date;
    private Enseignant intervenant;
    private UE matiere;
    private TypeIntervention type;
    private Salle salle;

    public Intervention(int duree, Date date, Enseignant intervenant, UE matiere, TypeIntervention type, Salle salle) {
        this.duree = duree;
        this.date = date;
        this.intervenant = intervenant;
        this.matiere = matiere;
        this.type = type;
        this.salle = salle;
    }

    public int getDuree() {
        return duree;
    }

    public Date getDate() {
        return date;
    }

    public Enseignant getIntervenant() {
        return intervenant;
    }

    public UE getMatiere() {
        return matiere;
    }

    public TypeIntervention getType() {
        return type;
    }

    public Salle getLieu() {
        return salle;
    }



}
