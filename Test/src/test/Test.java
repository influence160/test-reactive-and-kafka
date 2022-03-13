package test;

import java.nio.file.Path;

public class Test {

	public static void main(String[] args) {
		System.out.println(Path.of("C:\\Users\\OTILIO~1\\AppData\\Local\\Temp\\", "ttt").toString());
		String s = "Required request part 'metadata' is not present";
		System.out.println(s.substring("Required request part ".length() + 1, s.length() - " is not present".length() - 1));
		System.out.println(s.split("'")[1]);
	}
	
	

}
