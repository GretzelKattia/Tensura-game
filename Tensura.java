import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Classe que representa a masmorra
// public class Player implements Serializable
class Tensura implements Serializable {
    private Room currentRoom;
    private boolean gameOver;
    
    public Tensura() {
        // Inicializa a masmorra com as salas e configurações iniciais
        // Implemente a lógica de criação das salas, inimigos, armadilhas e tesouros aqui
        currentRoom = new Room("Lugar onde você inicia sua jornada: ");
        gameOver = false;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public void printCurrentRoomInfo() {
        // Exibe informações da sala atual (ex: descrição, inimigos, tesouros, etc.)
        System.out.println("Você se encontra: " + currentRoom.getName());
        // Implemente a lógica para exibir informações adicionais da sala
    }
    
    public void printActions() {
        // Exibe as ações disponíveis para o jogador
        System.out.println("Escolha uma ação:");
        System.out.println("1. Moverse para outro lugar");
        System.out.println("2. Atacar inimigo");
        System.out.println("3. Coletar habilidade");
        // Implemente outras ações disponíveis no jogo
    }
    
    public void processAction(String action) {
        // Processa a ação do jogador
        switch (action) {
            case "1":
                movePlayer(); // Implemente a lógica para mover o jogador para outra sala
                break;
            case "2":
                attackEnemy(); // Implemente a lógica para o jogador atacar um inimigo
                break;
            case "3":
                collectTreasure(); // Implemente a lógica para o jogador coletar um tesouro
                break;
            default:
                System.out.println("Ação inválida!");
                break;
        }
    }
    

    // ...

    // private void movePlayer() {
    //     System.out.println("Digite o número do lugar onde você deseja se mover:");
    //     Scanner scanner = new Scanner(System.in);
    //     int roomNumber = scanner.nextInt();
    //     scanner.nextLine(); // Limpa o buffer do scanner

    //     // Implemente a lógica para mover o jogador para a sala desejada
    //     // Certifique-se de verificar se o número da sala é válido

    //     // Exemplo simplificado: mover o jogador para a sala seguinte
    //     currentRoom = new Room("Sala " + roomNumber);
    // }

    private void movePlayer() {
    System.out.println("Digite a direção para onde deseja se mover (ex: norte, sul, leste, oeste):");
    Scanner scanner = new Scanner(System.in);
    String direction = scanner.nextLine().toLowerCase();

    Room currentRoom = getCurrentRoom();
    Map<String, Room> exits = currentRoom.getExits();

    if (exits.containsKey(direction)) {
        Room nextRoom = exits.get(direction);
        currentRoom = nextRoom;
        System.out.println("Você se moveu para " + currentRoom.getName());
    } else {
        System.out.println("Direção inválida! Tente novamente.");
    }
}


	private void attackEnemy() {
		Room currentRoom = getCurrentRoom();
		List<Enemy> enemies = currentRoom.getEnemies();

		if (enemies.isEmpty()) {
			System.out.println("Ufa! Não há inimigos nesta lugar!");
			return;
		}

		// Exibe os inimigos disponíveis para atacar
		System.out.println("Inimigos disponíveis para atacar:");
		for (int i = 0; i < enemies.size(); i++) {
			System.out.println((i + 1) + ". " + enemies.get(i).getName());
		}

		System.out.println("Escolha o número do inimigo para atacar:");
		Scanner scanner = new Scanner(System.in);
		int enemyNumber = scanner.nextInt();
		scanner.nextLine(); // Limpa o buffer do scanner

		if (enemyNumber < 1 || enemyNumber > enemies.size()) {
			System.out.println("Número de inimigo inválido!");
			return;
		}

		Enemy enemy = enemies.get(enemyNumber - 1);
		Player player = new Player(100,10); // Supondo que existe uma classe Player

		// Realiza o ataque do jogador ao inimigo
		int damageDealt = player.attack(enemy);
		enemy.reduceHealth(damageDealt);

		System.out.println("Você atacou o inimigo " + enemy.getName() + " e causou " + damageDealt + " de dano.");

		// Verifica se o inimigo foi derrotado
		if (enemy.isDefeated()) {
			currentRoom.removeEnemy(enemy);
			System.out.println("Você derrotou o inimigo " + enemy.getName() + "!");
		}
	}

    private Room getCurrentRoom() {
        return this.currentRoom;
    }


    private void collectTreasure() {
        Room currentRoom = getCurrentRoom();
        List<Treasure> treasures = currentRoom.getTreasures();

        if (treasures.isEmpty()) {
            System.out.println("Não há habilidades nesta sala!");
            return;
        }

        // Exibe os tesouros disponíveis para coletar
        System.out.println("Habilidades disponíveis para coletar:");
        for (int i = 0; i < treasures.size(); i++) {
            System.out.println((i + 1) + ". " + treasures.get(i).getName());
        }

        System.out.println("Escolha o número de habilidades que deseja coletar:");
        Scanner scanner = new Scanner(System.in);
        int treasureNumber = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do scanner

        if (treasureNumber < 1 || treasureNumber > treasures.size()) {
            System.out.println("Eita! Erro Erro Erro Erro Erro Erro Erro Erro...");
            return;
        }

        Treasure treasure = treasures.get(treasureNumber - 1);
        // Implemente a lógica para o jogador coletar o tesouro selecionado
    }
    
    // ...

    public void printGameOverMessage() {
        // Exibe a mensagem de fim de jogo
        System.out.println("Ah! Você morreu... Sorte na próxima pequeno slime");
    }
}
