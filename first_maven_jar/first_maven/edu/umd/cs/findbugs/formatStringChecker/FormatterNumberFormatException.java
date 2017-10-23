package edu.umd.cs.findbugs.formatStringChecker;

public class FormatterNumberFormatException extends FormatterException {
	private static final long serialVersionUID = 1L;
	final String txt, kind;
	
	/**
	 * @return the txt
	 */
	public String getTxt() {
		return txt;
	}

	/**
	 * @return the msg
	 */
	public String getKind() {
		return kind;
	}

	public FormatterNumberFormatException(String txt, String kind) {
		this.txt = txt;
		this.kind = kind;
		
	}
}
