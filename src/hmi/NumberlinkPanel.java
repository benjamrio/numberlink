package hmi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JPanel;

import control.Direction;
import control.IController;

/**
 * Panneau de l'IHM pour le jeu Numberlink
 * 
 * @author Dominique Marcadet
 * @author No� Bopp
 * @author Benjamin Rio
 * @version 1.2
 *
 */
@SuppressWarnings("serial")
public class NumberlinkPanel extends JPanel implements MouseListener {

    private static final float SELECTED_WIDTH = 4;
    private boolean firstDisplay = true;
    private IController controller;
    private int cellSize;
    private int endDiameter;
    private int halfRadius;
    private int pathWidth;
    private int arcRoundRect;
    private int[] selection;

    /**
     * Initialise le panneau d'affichage du jeu.
     * Le panneau est responsable de l'affichage de l'�tat du jeu et
     * de la transmission au contr�leur des cases cliqu�es.
     * 
     * @param controller le contr�leur qu'il faut informer des clics de
     *                   l'utilisateur dans les cases du jeu
     */
    public NumberlinkPanel( IController controller ) {
        this.controller = controller;
        this.addMouseListener( this );
    }

    private void computeParameters() {
        int cellHeight = getSize().height / controller.getNbLines();
        int cellWidth = getSize().width / controller.getNbColumns();
        cellSize = cellHeight < cellWidth ? cellHeight : cellWidth;
        endDiameter = cellSize * 2 / 3;
        halfRadius = endDiameter / 4;
        pathWidth = cellSize / 4;
        arcRoundRect = cellSize / 4;
        firstDisplay = false;
    }

    @Override
    public void paint( Graphics g ) {
        super.paint( g );
        
        if( firstDisplay ) computeParameters();

        // Le c�t� m�tier raisonne en [ligne, colonne]
        // Le c�t� IHM raisonne en [x, y]
        // Donc x <=> colonne et y <=> ligne
        for( int i = 0; i <= controller.getNbLines(); ++i ) {
            g.drawLine( 0, i * cellSize, controller.getNbColumns() * cellSize, i * cellSize );
        }
        for( int j = 0; j <= controller.getNbColumns(); ++j ) {
            g.drawLine( j * cellSize, 0, j * cellSize, controller.getNbLines() * cellSize );
        }

        int[][][] endsPos = controller.getEndsPositions();
        for( int tag = 0; tag < controller.getNbTags(); ++tag ) {
            setColorForTag( g, tag );
            // Affichage des deux extr�mit�s (r�les sym�triques)
            for (int end_nb = 0; end_nb < 2; end_nb++) {
            	int[] pos = endsPos[tag][end_nb];
            	g.fillOval( pos[1] * cellSize + halfRadius,
                        pos[0] * cellSize + halfRadius,
                        endDiameter, endDiameter );
            	// D�coration de l'extr�mit� si elle est s�lectionn�e
       
                if( Arrays.equals( pos, selection )) {
                    showSelectedEnd( g, pos );
                }
                //afin de ne pas afficher les directions � partir d'un mauvais end
                //on r�cup�re la position du "d�but" du path
                Direction[] directions = controller.getDirections(pos);
                int[] positionOfFirstEnd = controller.getPosFirstEnd(pos);
                if (directions!= null && positionOfFirstEnd != null) {paintDirections( g, positionOfFirstEnd, directions);}
            }
            
        }
    }

	private void showSelectedEnd(Graphics g, int[] pos) {
		Color c = g.getColor();
		g.setColor( Color.BLACK );
		Graphics2D g2 = ( Graphics2D ) g;
		Stroke s = g2.getStroke();
		g2.setStroke( new BasicStroke( SELECTED_WIDTH ));
		g.drawOval( pos[1] * cellSize + halfRadius,
		        pos[0] * cellSize + halfRadius,
		        endDiameter, endDiameter );
		g2.setStroke( s );
		g.setColor( c );
	}

    private void paintDirections( Graphics g, int[] startPos, Direction[] directions ) {
        // Ici :
        // x <=> startPos[1] et y <=> startPos[0]
        int x0 = startPos[1] * cellSize + cellSize / 2 - pathWidth / 2;
        int y0 = startPos[0] * cellSize + cellSize / 2 - pathWidth / 2;
        for( Direction dir : directions ) {
            int w = pathWidth;
            int h = pathWidth;
            int x1 = x0;
            int y1 = y0;
            switch( dir ) {
            case UP:
                y0 -= cellSize;
                y1 -= cellSize;
                h += cellSize;
                break;
            case DOWN:
                h += cellSize;
                y1 += cellSize;
                break;
            case LEFT:
                x0 -= cellSize;
                x1 -= cellSize;
                w += cellSize;
                break;
            case RIGHT:
                w += cellSize;
                x1 += cellSize;
                break;
            }
            g.fillRoundRect( x0, y0, w, h, arcRoundRect, arcRoundRect );
            x0 = x1;
            y0 = y1;
        }
    }

    private void setColorForTag( Graphics g, int tag ) {
        g.setColor( switch( tag ) {
            case 0  -> Color.RED;
            case 1  -> Color.ORANGE;
            case 2  -> Color.BLUE;
            case 3  -> Color.GREEN;
            case 4  -> Color.YELLOW;
            case 5  -> Color.MAGENTA;
            case 6  -> Color.CYAN;
            case 7  -> Color.PINK;
            case 8  -> Color.LIGHT_GRAY;
            case 9  -> Color.GRAY;
            case 10 -> Color.DARK_GRAY;
            case 11 -> Color.BLACK;
            default -> throw new IllegalArgumentException( "L'IHM ne peut pas afficher plus de 12 �tiquettes diff�rentes" );
        } );
    }

    @Override
    public void mouseClicked( MouseEvent e ) {

        System.out.println("Click enregistr� � x=" + e.getPoint().x + " , y="+e.getPoint().y );
        if( controller.clickCell( e.getPoint().y / cellSize, e.getPoint().x / cellSize )) {
            selection = new int[] { e.getPoint().y / cellSize, e.getPoint().x / cellSize };

        }
        repaint();
    }

    @Override
    public void mousePressed( MouseEvent e ) {
        // nothing
    }

    @Override
    public void mouseReleased( MouseEvent e ) {
        // nothing
    }

    @Override
    public void mouseEntered( MouseEvent e ) {
        // nothing
    }

    @Override
    public void mouseExited( MouseEvent e ) {
        // nothing
    }
}
