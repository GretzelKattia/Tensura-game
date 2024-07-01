import java.io.Serializable;
import java.util.Scanner;

// Classe que representa o jogador
class Player implements Serializable  {
    private String name;
    private int health;
    private int attackDamage;

    private int enemiesDefeated;
    private int treasuresCollected;
    private int roomsExplored;


    public Player(int health, int attackDamage) {
		
		Scanner scanner = new Scanner(System.in);
		
        System.out.println("Olá, sei que pode estar confuso agora, então deixe eu explicar. Você foi assasinado na sua vida passada, e reencarnou aqui em um mundo completamente novo... E pior, você é somente um slime");
        System.out.println("Mas não se preocupe, eu a maravilhosa Ramiris, terceira lorde demônio irei te acompanhar nessa sua nova jornada");
        System.out.println("Vou deixar você assimilar a situação. Mas e ai, qual é o seu nome?");
		this.name = scanner.nextLine();
        this.health = health; // Saúde inicial do jogador
		this.attackDamage = attackDamage; // Dano de ataque do 
        this.enemiesDefeated = 0; // Inimigos derrotados inicialmente
        this.treasuresCollected = 0; // Tesouros coletados inicialmente
        this.roomsExplored = 0; // Salas exploradas inicialmente
		
    }

    public int getHealth() {
        return this.health;
    }

    public void reduceHealth(int damage) {
        this.health -= damage;
    }

    public void increaseHealth(int value) {
        this.health += value;
    }


    public boolean isDefeated() {
        return this.health <= 0;
    }


    public void increaseAttackDamage(int value) {
        this.attackDamage += value;
    }
	
    public void decreaseAttackDamage(int value) {
        this.attackDamage -= value;
    }	

    // Método para incrementar a quantidade de inimigos derrotados
    public void incrementEnemiesDefeated() {
        enemiesDefeated++;
    }

    // Método para incrementar a quantidade de tesouros coletados
    public void incrementTreasuresCollected() {
        treasuresCollected++;
    }

    // Método para incrementar a quantidade de salas exploradas
    public void incrementRoomsExplored() {
        roomsExplored++;
    }

    public int attack(Enemy enemy) {
        int damageDealt = calculateDamage();

        enemy.reduceHealth(damageDealt);

        return damageDealt;
    }

    private int calculateDamage() {
        // Lógica para calcular o dano do jogador

        // Exemplo simples: Dano fixo de 10
        // return 10;
        return this.attackDamage;

    }

    public void collectTreasure(Treasure selectedTreasure) {
        // Lógica para coletar o tesouro
    }

    public String getName() {
        return this.name;
    }

    // Método para exibir as estatísticas do jogador
    public void displayStats() {
        System.out.println("Inimigos derrotados: " + enemiesDefeated);
        System.out.println("Tesouros coletados: " + treasuresCollected);
        System.out.println("Salas exploradas: " + roomsExplored);
    }
}
