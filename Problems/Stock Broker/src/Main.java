public class Main {

    public static void main(String[] args) {

        var broker = new Broker();
        var stock = new Stock();
        var buyCommand = new BuyCommand(stock);
        var sellCommand = new SellCommand(stock);

        broker.setCommand(buyCommand);
        broker.executeCommand();

        broker.setCommand(sellCommand);
        broker.executeCommand();
    }
}


class Stock {

    public void buy() {
        System.out.println("Stock was bought");
    }

    public void sell() {
        System.out.println("Stock was sold");
    }
}

interface Command {
    void execute();
}

class BuyCommand implements Command {
    private Stock stock;

    public BuyCommand(Stock stock) {
        this.stock = stock;
    }

    public void execute() {
        this.stock.buy();
    }
}

class SellCommand implements Command {
    private Stock stock;

    public SellCommand(Stock stock) {
        this.stock = stock;
    }

    public void execute() {
        this.stock.sell();
    }
}

class Broker {
    private Command command;
    public void setCommand(Command command) {
        this.command = command;
    }
    public void executeCommand() {
        this.command.execute();
    }
}