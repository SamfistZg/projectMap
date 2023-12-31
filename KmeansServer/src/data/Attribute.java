package data;
import java.io.Serializable;

abstract class Attribute implements Serializable {
    
    private String name;
    private int index;

    Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex () {
        return index;
    }

    public String toString() {
        return name;
    }
}
