package Parser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexReplacement {
	Pattern regex;
	String replacement;
	Matcher matcher;
	String regexString;

	public RegexReplacement(String regex, String replacement) {
		this.regex = Pattern.compile(regex);
		this.replacement = replacement;
		this.regexString = regex;
	}

	public String toString() {
		return "{regex: " + regex.toString() + " , replacement: " + replacement + " }";
	}

	public static String RegexreplaceAll(String st, String regex, String replaceWith) {
		//String exceptions[] = { "header", "ulList", "olList", "blockquote", "para" };
		switch (replaceWith) {
			case "header":
				if (Pattern.compile("^#{1,5} (.*)").matcher(st).matches())
					return header(st);
			case "ulList":
				return st;
			case "olList":
				return st;
			case "blockquote":
				return st;
			case "para":
				return st;
			default:
				return st.replaceAll(regex, replaceWith);
		}
	}

	public static String header(String st) {
		st = st.trim();
		int degree = 0;
		for (int i = 0; i < 5; i++) {
			if (st.charAt(i) != '#')
				break;
			degree++;
		}

		return "<h" + degree + ">" + st.trim() + "</h" + degree + ">\n";
	}
	public static String para(String st) {
		if (Pattern.compile("^<\\/?(ul|ol|li|h|p|bl)").matcher(st.trim()).matches()) {
			return "\n" + st+ "\n";
		}
		return "\n<p>" + st.trim()+ "</p>\n";
	}
/*
	public static String ulList(String st) {
		return ``;
	}

	public static String olList(text, item) {
		return `\n<ol>\n\t<li>${item.trim()}</li>\n</ol>`;
	}

	public static String blockquote(text, tmp, item) {
		return `\n<blockquote>${item.trim()}</blockquote>`;
	}
*/
}