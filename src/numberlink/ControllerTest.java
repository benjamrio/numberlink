package numberlink;

import javax.swing.SwingUtilities;

import control.Controller;
import numberlink.hmi.NumberlinkWindow;

/**
 * Programme de test de l'IHM en utilisant le controller bouchon
 * 
 * @author Dominique Marcadet
 * @version 1.2
 *
 */
public class ControllerTest implements Runnable {

	public static void main( String[] args ) {
        SwingUtilities.invokeLater( new ControllerTest() );
	}

    @Override
    public void run() {
    	int [][][] endsPos = {{{1,1}, {3,3}}, {{1,2}, {4, 2}}};
        new NumberlinkWindow( new Controller(5, 5, endsPos));
    }
}
