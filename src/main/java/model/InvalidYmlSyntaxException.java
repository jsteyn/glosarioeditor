package model;

public class InvalidYmlSyntaxException extends Exception {
	
	public static final int UNKNOWN_ERROR = 0, MISSING_QUOTES = 1,
			MISSING_LANGUAGE = 2, MISSING_TERM = 3, MISSING_DEF = 4;
	private String block;
	private int type;
	
	public InvalidYmlSyntaxException(int line, String block, int type) {
		super();
		this.type = type;
		String[] lines = block.split("\n");
		String[] markedLines = new String[lines.length];
		int count = 0;
		for (String l: block.split("\n")) {
			markedLines[count] = (line + count) + "-> " + l;
			count++;
		}
		this.block = String.join("\n", markedLines);
	}
	
	public String getMessage() {
		switch (type) {
			case UNKNOWN_ERROR:
				return "Invalid YAML Syntax in:\n" + block;
			case MISSING_QUOTES:
				return "Missing quotes on term at line in:\n" + block;
			case MISSING_LANGUAGE:
				return "No language found in slug at line in:\n" + block;
			case MISSING_TERM:
				return "No term found at line in;\n" + block;
			case MISSING_DEF:
				return "No definition found at line in;\n" + block;
		}
		return "";
	}

}
