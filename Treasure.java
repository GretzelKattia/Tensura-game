// Classe que representa um tesouro

import java.io.Serializable;

class Treasure implements Serializable {
    private String name;
    private int value;
	private boolean collected;

    public Treasure(String name, int value) {
        this.name = name;
        this.value = value;
		this.collected = false;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
	
	public boolean isCollected() {
        return collected;
    }

    public void collect() {
        this.collected = true;
    }	
}