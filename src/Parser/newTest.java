package Parser;

import java.util.regex.*;

public class newTest {
	public static void main(String args[]) {
		System.out.println("---dashajaa".replaceFirst("^-{3,100}(dashed|dotted|solid|rounded)", "<hr class='$1'>"));
	}
}
