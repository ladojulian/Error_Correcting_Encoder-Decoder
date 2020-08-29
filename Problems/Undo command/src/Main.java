import java.util.Arrays;

interface Movable {
    int getX();

    int getY();

    void setX(int newX);

    void setY(int newY);
}

interface Storable {
    int getInventoryLength();

    String getInventoryItem(int index);

    void setInventoryItem(int index, String item);
}

interface Command {
    void execute();

    void undo();
}

class CommandMove implements Command {
    Movable entity;
    int xMovement;
    int yMovement;

    private int[] xMovements = new int[0];
    private int[] yMovements = new int[0];

    @Override
    public void execute() {
        this.xMovements = Arrays.copyOf(this.xMovements, this.xMovements.length + 1);
        this.xMovements[this.xMovements.length - 1] = this.entity.getX();
        this.yMovements = Arrays.copyOf(this.yMovements, this.yMovements.length + 1);
        this.yMovements[this.yMovements.length - 1] = this.entity.getY();

        this.entity.setX(this.xMovement);
        this.entity.setY(this.yMovement);
    }

    @Override
    public void undo() {
        this.entity.setX(this.xMovements[this.xMovements.length - 1]);
        this.xMovements = Arrays.copyOf(this.xMovements, this.xMovements.length - 1);

        this.entity.setY(this.yMovements[this.yMovements.length - 1]);
        this.yMovements = Arrays.copyOf(this.yMovements, this.yMovements.length - 1);
    }
}

class CommandPutItem implements Command {
    Storable entity;
    String item;

    private int[] indexes = new int[0];

    @Override
    public void execute() {
        for (var i = 0; i < this.entity.getInventoryLength(); i++) {
            if (this.entity.getInventoryItem(i) == null) {
                this.indexes = Arrays.copyOf(this.indexes, this.indexes.length + 1);
                this.indexes[this.indexes.length - 1] = i;
                this.entity.setInventoryItem(i, this.item);
                break;
            }
        }
    }

    @Override
    public void undo() {
        if (this.indexes.length > 0) {
            this.entity.setInventoryItem(this.indexes[this.indexes.length - 1], null);
            this.indexes = Arrays.copyOf(this.indexes, this.indexes.length - 1);
        }
    }
}