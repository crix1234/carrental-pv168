package carrental.managers;

/**
 *
 * @author Pavel Mican
 */
public final class ReduceLongString {

	/**
	 * reduces lenght of the given <code>String</code> to the maximum
	 * of <code>maxLength</code> length.
	 * @param value string to be checked for being too long
	 * @param maxLength lenght of a given string
	 * @return String reduced string to the given <maxLength> length
	 * @return null if maxLength < 0
	 */
	public static String reduceLongString(String value, int maxLength) {
		if (maxLength < 0) {
			return null;
		}
		String strNew = value;
		if (value != null) {
			if (value.length() > maxLength) {
				strNew = value.substring(0, maxLength);
			}
		}
		return strNew;
	}
}
