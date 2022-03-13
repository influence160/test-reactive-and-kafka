package test;

import java.util.List;

public class TestSplit {

	public static void main(String[] args) {
		System.out.println(List.of("".split(":")));
		System.out.println(List.of("".split(":", -1)));
		
		System.out.println(List.of("aaa".split(":")));
		System.out.println(List.of("aaa".split(":", -1)));

		System.out.println(List.of("aaa:".split(":")));
		System.out.println(List.of("aaa:".split(":", -1)));

		System.out.println(List.of("aaa:bbb".split(":")));
		System.out.println(List.of("aaa:bbb".split(":", -1)));

		System.out.println(List.of(":aaa:bbb:".split(":")));
		System.out.println(List.of(":aaa:bbb:".split(":", -1)));

		System.out.println(List.of("aaa:bbb:ccc".split(":")));
		System.out.println(List.of("aaa:bbb:ccc".split(":", -1)));
	}

}
