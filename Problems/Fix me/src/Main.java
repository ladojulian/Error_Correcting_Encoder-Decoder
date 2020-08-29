import java.util.*;

/**
 * Observable
 */
interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}

/**
 * Concrete Observable
 */
class RockstarGames implements Observable {

    public String releaseGame;
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (var observer : this.observers) {
            System.out.println("Inform message to : " + observer);
            observer.update(this.releaseGame);
        }
    }

    public void release(String game) {
        this.releaseGame = game;
        this.notifyObservers();
    }

}

/**
 * Observer
 */
interface Observer {
    void update(String domain);
}

/**
 * Concrete Observer
 */
class Gamer implements Observer {

    private String name;
    private String reaction;
    private Observable observable;

    private Set<String> games = new HashSet<>();

    public Gamer(String name, String reaction, Observable observable) {
        this.reaction = reaction;
        this.observable = observable;
        this.name = name;
    }

    @Override
    public void update(String domain) {
        this.buyGame(domain);
    }

    public void buyGame(String game) {
        games.add(game);
        System.out.println(this.name + " says: " + this.reaction);
    }

    @Override
    public String toString() {
        return this.name;
    }
}

/**
 * Main Class
 */
public class Main {
    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);

        String game = null;

        var rockstarGames = new RockstarGames();

        Gamer garry = new Gamer("Garry Rose", "I want to pre-order", rockstarGames);
        Gamer peter = new Gamer("Peter Johnston", "Pinch me...", rockstarGames);
        Gamer helen = new Gamer("Helen Jack", "Jesus, it's new game from Rockstar!", rockstarGames);

        rockstarGames.addObserver(garry);
        rockstarGames.addObserver(peter);
        rockstarGames.addObserver(helen);

        game = scanner.nextLine();
        System.out.println("It's happened! RockstarGames releases new game - " + game + "!");

        rockstarGames.release(game);

        scanner.close();
    }
}