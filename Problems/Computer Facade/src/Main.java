class ComputerFacadeTestDrive {
    public static void main(String[] args) {
        var processor = new Processor();
        var monitor = new Monitor();
        var keyboard = new Keyboard();

        ComputerFacade computerFacade = new ComputerFacade(processor, monitor, keyboard);

        computerFacade.turnOnComputer();
        computerFacade.turnOffComputer();
    }
}

class ComputerFacade {
    Processor processor;
    Monitor monitor;
    Keyboard keyboard;

    public ComputerFacade(Processor processor, Monitor monitor, Keyboard keyboard) {
        this.processor = processor;
        this.monitor = monitor;
        this.keyboard = keyboard;
    }

    public void turnOnComputer() {
        this.processor.on();
        this.monitor.on();
        this.keyboard.on();
    }

    public void turnOffComputer() {
        this.keyboard.off();
        this.monitor.off();
        this.processor.off();
    }
}

class Processor {
    String description = "Processor";

    public void on() {
        System.out.println(this.description + " on");
    }

    public void off() {
        System.out.println(this.description + " off");
    }
}

class Monitor {
    String description = "Monitor";


    public void on() {
        System.out.println(this.description + " on");
    }

    public void off() {
        System.out.println(this.description + " off");
    }
}

class Keyboard {
    String description = "Keyboard";

    public void on() {
        System.out.println(this.description + " on");
        this.turnOnBacklight();
    }

    public void off() {
        System.out.println(this.description + " off");
        this.turnOffBacklight();
    }

    private void turnOnBacklight() {
        System.out.println("Backlight is turned on");
    }

    private void turnOffBacklight() {
        System.out.println("Backlight is turned off");
    }
}