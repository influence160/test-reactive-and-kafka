package othmen.test.reactor;

import reactor.core.publisher.Mono;

public class TestZip {

	public static void main(String[] args) {
		testZipMonoWIthEmpty();
	}

	private static void testZipMonoWIthEmpty() {
		class Personne {
			String nom;
			String prenom;
			public Personne(String nom, String prenom) {
				super();
				this.nom = nom;
				this.prenom = prenom;
			}
			public String toString() {
				return "Personne{" + nom + " " + prenom + "}"; 
			}
		}
		
		Mono.just("Othmen").zipWith(Mono.just("Tiliouine"), Personne::new).subscribe(System.out::println);
		
		Mono.just("Othmen").zipWith(Mono.empty(), Personne::new).subscribe(System.out::println);
		
		Mono.<String>empty().zipWith(Mono.just("Tiliouine"), Personne::new).subscribe(System.out::println);
		
		Mono.<String>empty().zipWith(Mono.<String>empty(), Personne::new).subscribe(System.out::println);
	}

}
