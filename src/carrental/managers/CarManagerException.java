package carrental.managers;

/**
 *
 * @author Matej Cizik
 */
public class CarManagerException extends Exception {

    /**
     * Creates a new instance of <code>CarManagerException</code> without detail message.
     */
    public CarManagerException() {
    }

    /**
     * Constructs an instance of <code>CarManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public CarManagerException(String msg) {
        super(msg);
    }

	/**
	 * Constructs an instance of
	 * @param ex<code>Exception</code> with the specified detail message.
	 */
	CarManagerException(Exception ex) {
		super(ex);
	}
}
