package Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class rules {
	public static void main(String[] args) {
		/* Reading file */
		int i = 0;
		int linecount = 0;
		Scanner s = new Scanner(System.in);
		String sourceFile;
		do {
			System.out.println("Enter source file .md");
			sourceFile = s.nextLine();
		} while (!sourceFile.endsWith(".md"));
		File file = new File("/home/hazem/eclipse-workspace/MarkdownParser/src/Parser/" + sourceFile);
		FileReader fr;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return;
		}
		String line;
		BufferedReader br = new BufferedReader(fr);
		ArrayList<RegexReplacement> rules = new ArrayList<>();
		ArrayList<String> lines = new ArrayList<>();
		rules.add(new RegexReplacement("^#{1,5}( .*)", "header"));
		rules.add(new RegexReplacement("!\\[([^\\[]+)\\]\\(([^\\)]+)\\)", "<img src='$2' alt='$1'>"));
		rules.add(new RegexReplacement("\\[([^\\[]+)\\]\\(([^\\)]+)\\)", "<a href='$2'>$1</a>"));
		rules.add(new RegexReplacement("(\\*\\*|__)(.*?)\\1", "<strong>$2</strong>"));
		rules.add(new RegexReplacement("(\\*|_)(.*?)\\1", "<em>$2</em>"));
		rules.add(new RegexReplacement("\\~\\~(.*?)\\~\\~", "<del>$1</del>"));
		rules.add(new RegexReplacement("\\:\\\"(.*?)\\\"\\:", "<q>$1</q>"));
		rules.add(new RegexReplacement("`(.*?)`", "<code>$1</code>"));
		rules.add(new RegexReplacement("\\n\\*(.*)", "\n<ul>\n\t<li>$1</li>\n</ul>"));
		rules.add(new RegexReplacement("\\n[0-9]+\\.(.*)", "olList"));
		rules.add(new RegexReplacement("\\n(&gt;|\\>)(.*)", "blockquote"));
		rules.add(new RegexReplacement("\\n-{5,}", "\\n<hr/>"));
		rules.add(new RegexReplacement("([^\\n]+)", "para"));
		rules.add(new RegexReplacement("^-{3,100}", "<hr'>"));
		rules.add(new RegexReplacement("\\/ul>\\s?<ul>", ""));
		rules.add(new RegexReplacement("<\\/ol>\\s?<ol>", ""));
		rules.add(new RegexReplacement("<\\/blockquote><blockquote>", "\n"));
		String temp;
		String[] operations = new String[100];
		boolean putHeader;
		try {
			while ((line = br.readLine()) != null) {
				putHeader = false;
				for (RegexReplacement rule : rules) {
					temp = line;
					line = RegexReplacement.RegexreplaceAll(line, rule.regexString, rule.replacement);
						//System.out.printf("line is: %s %s", line, rule.replacement);
					if (!temp.equals(line)) {
						if (rule.replacement == "header") putHeader = true;
						operations[i] = rule.replacement;
						i++;
					}
				}
				if (putHeader)
					lines.add(line);
				else
					lines.add(line+"\n");
				linecount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.printf("done %d operations done are:", operations.length);
		for (String oper : operations) {
			if (oper != null)
				System.out.println(oper);
		}
		System.out.println("--------------------------");
		String fileName;
		System.out.println("Attempting creating and writing to your file");
		do {
			System.out.println("Enter file name must be .html");
			fileName = s.nextLine();
		} while (!fileName.endsWith(".html"));
		try {
			BufferedWriter bw = new BufferedWriter(
					new FileWriter("/home/hazem/eclipse-workspace/MarkdownParser/src/Parser/" + fileName));
			for (i = 0; i < linecount; i++) {
				//System.out.printf("written %d out of %d:\n", i, linecount);
				//System.out.println("out: " + lines.get(i));
				bw.write(lines.get(i));
			}
			bw.close();
		} catch (Exception ex) {
			System.out.println("error writing");
		}
	}
}