package carrental.managers;

/**
 *
 * @author Matej Cizik
 */
public class OrderingManagerException extends Exception {

	/**
	 * Creates a new instance of <code>OrderingManagerException</code> without detail message.
	 */
	public OrderingManagerException() {
	}

	/**
	 * Constructs an instance of <code>OrderingManagerException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public OrderingManagerException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an instance of
	 * @param ex<code>Exception</code> with the specified detail message.
	 */
	OrderingManagerException(Exception ex) {
		super(ex);
	}
}
