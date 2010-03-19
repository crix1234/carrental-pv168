package carrental.managers;
/**
 *
 * @author Pavel Mican
 */
public class CustomerManagerException extends Exception {

    /**
     * Creates a new instance of <code>CustomerManagerException</code> without detail message.
     */
    public CustomerManagerException() {
    }


    /**
     * Constructs an instance of <code>CustomerManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public CustomerManagerException(String msg) {
        super(msg);
    }

	/**
	 * Constructs an instance of
	 * @param ex<code>Exception</code> with the specified detail message.
	 */
	CustomerManagerException(Exception ex) {
		super(ex);
	}
}

