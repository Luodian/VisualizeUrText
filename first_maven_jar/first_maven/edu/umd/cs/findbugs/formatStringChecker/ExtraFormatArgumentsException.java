/**
 * 
 */
package edu.umd.cs.findbugs.formatStringChecker;

public class ExtraFormatArgumentsException extends FormatterException {

	private static final long serialVersionUID = 1L;
	public final int provided;
	public final int used;
	
public ExtraFormatArgumentsException(int provided, int used) {
		this.provided = provided;
		this.used = used;
	}
}