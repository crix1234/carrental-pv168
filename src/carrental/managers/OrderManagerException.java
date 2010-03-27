package carrental.managers;

/**
 *
 * @author Matej Cizik
 */
public class OrderManagerException extends Exception {
	/**
     * Creates a new instance of <code>OrderManagerException</code> without detail message.
     */
    public OrderManagerException() {
    }

    /**
     * Constructs an instance of <code>OrderManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public OrderManagerException(String msg) {
        super(msg);
    }

	/**
	 * Constructs an instance of
	 * @param ex<code>Exception</code> with the specified detail message.
	 */
	OrderManagerException(Exception ex) {
		super(ex);
	}
}
