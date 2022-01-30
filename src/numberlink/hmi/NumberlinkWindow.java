package numberlink.hmi;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.Direction;
import control.IController;

/**
 * Fenêtre de l'IHM pour le jeu Numberlink
 * 
 * @author Dominique Marcadet
 * @version 1.2
 *
 */
@SuppressWarnings("serial")
public class NumberlinkWindow extends JFrame implements KeyListener {

	private static final int WINDOW_SIZE = 500;
    private static final int WINDOW_TITLE_HEIGHT = 20;
    private IController controller;

    /**
     * Initialise la fenêtre du jeu.
     * La fenêtre délègue à un panneau l'affichage de l'état du jeu.
     * Elle est responsable des dimensions globales de la fenêtre au départ et
     * de la transmission au contrôleur des appuis sur les flèches du clavier.
     * 
     * @param controller le contrôleur qu'il faut informer des appuis par
     *                   l'utilisateur des flèches du clavier
     */
    public NumberlinkWindow( IController controller ) {
        this.controller = controller;

        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setPreferredSize( new Dimension( WINDOW_SIZE, WINDOW_SIZE + WINDOW_TITLE_HEIGHT ));
        this.setTitle( "Numberlink" );

        this.add( new NumberlinkPanel( controller ));
        this.addKeyListener( this );

        this.pack();
        this.setVisible( true );
    }

    @Override
    public void keyTyped( KeyEvent e ) {
        // nothing
    }

    @Override
    public void keyPressed( KeyEvent e ) {
        Direction direction = switch( e.getKeyCode() ) {
            case KeyEvent.VK_UP    -> Direction.UP;
            case KeyEvent.VK_DOWN  -> Direction.DOWN;
            case KeyEvent.VK_LEFT  -> Direction.LEFT;
            case KeyEvent.VK_RIGHT -> Direction.RIGHT;
            default                -> null;
        };
        if( direction == null ) return;
        if( controller.action( direction )) {
            repaint();
            JOptionPane.showMessageDialog( this, "Vous avez gagn� !" );
            System.exit( 0 );
        }
        repaint();
    }

    @Override
    public void keyReleased( KeyEvent e ) {
        // nothing
    }

}
