package edu.umd.cs.findbugs.formatStringChecker;

public class IllegalFormatConversionException extends FormatterException {

	private static final long serialVersionUID = 1L;

	final private String formatSpecifier;
	final private char conversion;
	final private String signature;
	int argIndex = -1;

	/**
	 * Constructs an instance of this class with the mismatched conversion and
	 * the corresponding argument class.
	 * 
	 * @param formatSpecifier
	 *            Inapplicable format specifier
	 * 
	 * @param signature
	 *            Signature of the mismatched argument
	 */
	public IllegalFormatConversionException(String formatSpecifier, char conversion, String signature) {
		if (signature == null)
			throw new NullPointerException();
		
		this.conversion = conversion;
		this.formatSpecifier = formatSpecifier;
		this.signature = signature;
	}

	public void setArgIndex(int argIndex) {
		if (argIndex == -1) throw new IllegalStateException("arg index already set");
		this.argIndex = argIndex;
	}
	
	public int getArgIndex() {
		return argIndex;
	}
	public String getFormatSpecifier() {
		return formatSpecifier;
	}
	public char getConversion() {
		return conversion;
	}
	/**
	 * Returns the class of the mismatched argument.
	 * 
	 * @return The class of the mismatched argument
	 */
	public String getArgumentSignature() {
		return signature;
	}

	// javadoc inherited from Throwable.java
	public String getMessage() {
		return String.format("%s can't format %s", formatSpecifier, signature);
	}

}
