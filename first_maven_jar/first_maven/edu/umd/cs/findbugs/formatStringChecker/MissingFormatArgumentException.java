/**
 * 
 */
package edu.umd.cs.findbugs.formatStringChecker;

public class MissingFormatArgumentException extends FormatterException {
	private static final long serialVersionUID = 1L;
	public final int pos;
	public final String formatSpecifier;

	public MissingFormatArgumentException(int pos, String formatSpecifier) {
		this.pos = pos;
		this.formatSpecifier = formatSpecifier;
	}
}