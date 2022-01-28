package numberlink;

import javax.swing.SwingUtilities;

import numberlink.control.ControllerStub;
import numberlink.hmi.NumberlinkWindow;

/**
 * Programme de test de l'IHM du jeu Numberlink
 * en utilisant le contrôleur bouchon
 * 
 * @author Dominique Marcadet
 * @version 1.2
 *
 */
public class TestWithControllerStub implements Runnable {

	public static void main( String[] args ) {
        SwingUtilities.invokeLater( new TestWithControllerStub() );
	}

    @Override
    public void run() {
        new NumberlinkWindow( new ControllerStub() );
    }
}
