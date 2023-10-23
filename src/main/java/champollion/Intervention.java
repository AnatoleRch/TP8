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

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Enseignant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Enseignant intervenant) {
        this.intervenant = intervenant;
    }

    public UE getMatiere() {
        return matiere;
    }

    public void setMatiere(UE matiere) {
        this.matiere = matiere;
    }


    public TypeIntervention getType() {
        return type;
    }

    public void setType(TypeIntervention type) {
        this.type = type;
    }


    public Salle getLieu() {
        return salle;
    }

    public void setLieu(Salle salle) {
        this.salle = salle;
    }


}
