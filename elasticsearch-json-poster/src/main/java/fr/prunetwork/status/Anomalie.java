package fr.prunetwork.status;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/11/14
 */
public enum Anomalie {
    travail_hors_zone,
    travail_trop_rapide,
    travail_trop_lent,
    zone_traitée_en_double,
    travail_dans_la_mauvaise_zone,
    absence_du_personnel,
    alerte_déclenchée_par_le_personnel,
    machine_sortie_de_zone_autorisée,
    machine_immobile,
    machine_qui_se_déplace_sans_brosser,
    machine_durée_utilisation_supérieure_au_contrat,
    machine_géolocalisation_en_panne,
    machine_muette,
    capteur_défaillant
}
