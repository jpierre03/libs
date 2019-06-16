package fr.prunetwork.elasticsearch.generator.status;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/11/14
 */
@SuppressWarnings("unused")
public enum Warning {
    travail_hors_zone,
    travail_trop_rapide,
    travail_trop_lent,
    zone_traitee_en_double,
    travail_dans_la_mauvaise_zone,
    absence_du_personnel,
    alerte_declenchee_par_le_personnel,
    machine_sortie_de_zone_autorisee,
    machine_immobile,
    machine_qui_se_deplace_sans_brosser,
    machine_duree_utilisation_superieure_au_contrat,
    machine_geolocalisation_en_panne,
    machine_muette,
    capteur_defaillant
}
