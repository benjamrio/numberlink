package control;


/*
 * **********************
 * ***** IMPORTANT ******
 * **********************
 *
 * Le contrôleur sert d'intermédiaire entre l'interface homme-machine et les objets
 * métiers qui représentent l'état du jeu. Il ne mémorise donc pas les caractéristiques 
 * et l'état du jeu.
 * 
 * Pour chaque demande de l'IHM, vous devez donc :
 * - déterminer quel objet a la responsabilité de mémoriser (ou de calculer) l'information demandée ;
 * - déterminer comment le contrôleur va faire pour obtenir cette information.
 * 
 * Par exemple, c'est la grille qui connait le nombre de lignes et de colonnes. Il se trouve que
 * le contrôleur a un lien vers la grille, il suffit donc au contrôleur de déléguer la demande à 
 * la grille.
 * 
 * C'est bien sûr plus compliqué pour d'autres méthodes, à vous de concevoir une bonne solution 
 * (et qui respecte la loi de Demeter !). Si vous êtes amenés à ajouter des liens entres des objets,
 * et donc des associations entre classes, n'oubliez pas de mettre à jour votre diagramme de classes
 * et d'expliquer dans votre rapport pourquoi ces nouvelles associations sont nécessaires.
 */

/*
 * **************************
 * ***** DOCUMENTATION ******
 * **************************
 *
 * La documentation de cette interface est au format Javadoc.
 * Ce format permet de générer une documentation au format HTML.
 * Avec Eclipse, il suffit de choisir la commande "Generate Javadoc…" dans me menu "Project".
 * 
 * Une documentation de ce format Javadoc est disponible sur :
 *     https://www.oracle.com/fr/technical-resources/articles/java/javadoc-tool.html
 */


/**
 * Interface du controleur pour le jeu Numberlink 
 * 
 * @author Dominique Marcadet
 * @version 1.2
 *
 */
public interface IController {

    /**
     * Méthode appellée par l'IHM quand le joueur clique sur une case.
     * 
     * <ul>
     * <li> Si cette case contient une extrémité :
     *   <ul>
     *   <li> si un chemin avec la même étiquette que l'extrémité existe, il
     *        est détruit ;
     *   </li>
     *   <li> un nouveau chemin est créé, il prend comme point de départ cette
     *        extrémité et devient le chemin courant ;
     *   </li>
     *   <li> la méthode retourne true ;
     *   </li>
     *   </ul>
     * </li>
     * <li> Sinon, il ne se passe rien et la méthode retourne false.
     * </li>
     * </ul>
     * @param line numéro de la ligne de la case sélectionnée (de 0 à 
     *             getNbLignes() - 1)
     * @param column numéro de la colonne de la case sélectionnée (de 0 à 
     *               getNbColonnes() - 1)
     * @return true si une extrémité est présente sur la case, false sinon.
     */
    boolean clickCell( int line, int column );

    /**
     * Méthode appellée par l'IHM quand je joueur appuie sur l'une des
     * flèches du clavier.
     * 
     * S'il n'y a pas de chemin courant, rien ne se passe.
     * S'il y a un chemin courant, celui-ci doit s'agrandir, si
     * il le peut, dans la direction indiquée en argument.
     * 
     * @param direction direction de la progression demandée par le joueur
     * @return true si la grille est terminée, false sinon
     */
    boolean action( Direction direction );

    /**
     * Méthode appellée par l'IHM pour connaître le nombre de
     * lignes de la grille.
     * 
     * @return le nombre de lignes de la grille à afficher
     */
    int getNbLines();

    /**
     * Méthode appellée par l'IHM pour connaître le nombre de
     * colonnes de la grille.
     * 
     * @return le nombre de colonnes de la grille à afficher
     */
    int getNbColumns();

    /**
     * Méthode appellée par l'IHM pour connaître le nombre
     * d'étiquettes de la grille.
     * 
     * @return le nombre d'étiquettes de la grille à afficher
     */
    int getNbTags();

    /**
     * Méthode appellée par l'IHM pour obtenir la position de 
     * départ du chemin correspondant à une étiquette donnée.
     * 
     * @param tag identifie l'étiquette du chemin dont l'IHM demande 
     *            la position de départ
     * @return un tableau de 2 entiers [ligne, colonne] donnant la
     *         position de départ du chemin associé à l'étiquette
     *         indiquée. S'il n'y a pas encore de chemin pour cette 
     *         étiquette, la position de l'une des extrémités de cette 
     *         étiquette.
     */
    int[] getStartPathPosition( int tag );

    /**
     * Méthode appellée par l'IHM pour obtenir la position de 
     * la seconde extrémité de l'étiquette donnée. La seconde 
     * extrémité est celle qui n'est pas retournée par {@link 
     * #getStartPathPosition(int) getStartPathPosition(int)}
     * 
     * @param tag identifie l'étiquette dont l'IHM demande 
     *            la seconde extrémité
     * @return un tableau de 2 entiers [ligne, colonne] donnant la
     *         position de la seconde extrémité de cette étiquette (la 
     *         première est retournée par {@link #getStartPathPosition(int)
     *         getStartPathPosition(int)}.
     */
    int[] getSecondEndPosition( int tag );

    /**
     * Méthode appellée par l'IHM pour obtenir le tracé d'un chemin
     * sur la grille.
     * 
     * @param tag identifie l'étiquette du chemin cherché. 
     * @return un tableau de valeurs de l'énumération Direction.
     *         Le point de départ du chemin est celui donné par 
     *         {@link #getStartPathPosition(int)
     *         getStartPathPosition(int)}.
     */
    Direction[] getDirections( int tag );

}

