package carrental.managers;

/**
 *
 * @author Pavel Mican
 */
public class AddressManagerException extends Exception {

    /**
     * Creates a new instance of <code>AddressManagerException</code> without detail message.
     */
    public AddressManagerException() {
    }


    /**
     * Constructs an instance of <code>AddressManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public AddressManagerException(String msg) {
        super(msg);
    }

	/**
	 * Constructs an instance of
	 * @param ex<code>Exception</code> with the specified detail message.
	 */
	AddressManagerException(Exception ex) {
		super(ex);
	}
}
