package control;

/**
 * Interface du controleur pour le jeu Numberlink 
 * @author Noe Bopp
 * @author Benjamin Rio
 * @author Dominique Marcadet
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
    *        est détruit,
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
    * Méthode appellée par l'IHM quand le joueur appuie sur l'une des
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
    * de tags de la grille.
    * 
    * @return le nombre de tags de la grille à afficher
    */
   int getNbTags();

   /**
    * Méthode appellée par l'IHM pour obtenir les positions de 
    * départs (i, j) des extrémités
    *
    * @param tag identifie l'étiquette du chemin dont l'IHM demande 
    *            la position de départ
    * @return un tableau de 2 tableaux de 2 entiers contenant les positions[ligne, colonne] 
    *         des paires d'extrémités. Chaque paire d'extrémité corresondra à un tag dans 
    *         la grille
    */
	int[][][] getEndsPositions();
	
	/**
	 * Méthode appelée par l'IHM pour obtenir la position de 
	 * la première cell à partir du quel le path actuel (celui
	 * de la cell à cette position) est construit le liste des directions
	 * @param pos La position d'une cell
	 * @return un tableau [ligne, colonne] décrivant la position de la "première"
	 * cell du path associé
	 */
	int[] getPosFirstEnd(int[] pos);
	
	/**
	 * Méthode appelée par l'IHM pour obtenir la liste des directions
	 * à suivre pour tracer le chemin à partir de la position de la cell
	 * constituant le début du path.
	 * @param pos La position sur la grille d'une cell.
	 * @return La liste des directions du path associé.
	 */
	Direction[] getDirections(int[] pos);
}

