package hmi;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.Direction;
import control.IController;

/**
 * Fen�tre de l'IHM pour le jeu Numberlink
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
     * Initialise la fen�tre du jeu.
     * La fen�tre d�l�gue � un panneau l'affichage de l'�tat du jeu.
     * Elle est responsable des dimensions globales de la fen�tre au d�part et
     * de la transmission au contr�leur des appuis sur les fl�ches du clavier.
     * 
     * @param controller le contr�leur qu'il faut informer des appuis par
     *                   l'utilisateur des fl�ches du clavier
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
        System.out.println("Enregistrement direction " + direction);
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
