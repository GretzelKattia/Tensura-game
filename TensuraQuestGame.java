import java.io.*;
import java.util.*;

class TensuraQuestGame implements Serializable {
    private Player player;
    private List<Room> rooms;
    private Room currentRoom;

    public TensuraQuestGame() {
        player = new Player(100, 10);
        rooms = createRooms();
    }

    public void startGame() {
        System.out.println("Bem-vindo ao jogo TenSura!");

        // Define a sala inicial
        currentRoom = rooms.get(0);

        boolean gameRunning = true;
        boolean mostraMensagemInvalida = true; // Variável para controlar a exibição da mensagem de opção inválida

        while (gameRunning) {
            System.out.println("\nVocê se encontra no: " + currentRoom.getName());

            // Verifica se há inimigos na sala
            if (!currentRoom.getEnemies().isEmpty()) {
                System.out.println("Possíveis inimigos encontrados!");

                // Realiza ações de batalha
                attackEnemy();
            }

            // Verifica se há tesouros na sala
            if (!currentRoom.getTreasures().isEmpty()) {
                System.out.println("Eba! Habilidades encontradas!");

                // Realiza ações de coleta de tesouro
                collectTreasure();
            }

            // Verifica se há armadilha na sala
            if (currentRoom.hasTrap()) {
                System.out.println("CUIDADO! ARMADILHA!");
                int trapDamage = currentRoom.getTrap().activateTrap();
                player.reduceHealth(trapDamage);
                System.out.println("Você sofreu " + trapDamage + " de dano da armadilha!");
            }

            // Verifica se o jogador ainda está vivo
            if (player.isDefeated()) {
                System.out.println("Vish, você morreu... Sorte na próxima.");
                gameRunning = false;
                break;
            }

            // Verifica se o jogador alcançou a última sala
            if (currentRoom == rooms.get(rooms.size() - 1)) {
                System.out.println("Você conseguiu derrotar o Boss final! Parabéns!");
                gameRunning = false;
                continue;
            }

            // Permite ao jogador escolher o próximo movimento
            System.out.println("Escolha seu próximo movimento:");
            System.out.println("1. Mover para uma sala adjacente");
            System.out.println("2. Ver status do jogador");
            System.out.println("3. Salvar o jogo");
            System.out.println("4. Ver estatísticas do jogo");
            System.out.println("5. Carregar o jogo");
            System.out.println("6. Desistir do jogo");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do scanner após nextInt()

            switch (choice) {
                case 1:
                    System.out.println("Escolha uma direção para se mover (norte, sul, leste, oeste):");
                    String direction = scanner.nextLine().toLowerCase();
                    movePlayer(direction);
                    break;
                case 2:
                    showPlayerStatus();
                    break;
                case 3:
                    try {
                        saveGame("savegame.dat");
                        System.out.println("Jogo salvo com sucesso!");
                    } catch (IOException e) {
                        System.out.println("Ocorreu um erro ao salvar o jogo.");
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    player.displayStats();
                    break;
                case 5:
                    try {
                        TensuraQuestGame loadedGame = loadGame("savegame.dat");
                        System.out.println("Jogo carregado com sucesso!");
                        loadedGame.startGame();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Ocorreu um erro ao carregar o jogo.");
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    System.out.println("Você desistiu do jogo. Fim de jogo.");
                    return; // Sai imediatamente do loop ao desistir do jogo
                default:
                    if (mostraMensagemInvalida) {
                        System.out.println("Opção inválida. Tente novamente.");
                        mostraMensagemInvalida = false; // Evita exibir a mensagem novamente na próxima iteração
                    }
                    break;
            }
        }
    }

    public void saveGame(String arquivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(this);
        }
    }

    public static TensuraQuestGame loadGame(String arquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (TensuraQuestGame) ois.readObject();
        }
    }

    private void movePlayer(String direction) {
        Room nextRoom = currentRoom.getExits().get(direction);

        if (nextRoom == null) {
            System.out.println("Oxi, não tem nada aqui.");
        } else {
            int trapDamage = currentRoom.activateTrap(); // Ativa a armadilha da sala atual

            currentRoom = nextRoom;
            System.out.println("Você se encontra na: " + currentRoom.getName());

            // Incrementa o número de salas exploradas
            player.incrementRoomsExplored();

            if (trapDamage > 0) {
                System.out.println("AAAAAAAAAAAAAAA, ARMADILHA!!");
                System.out.println("Tadinho.. você sofreu " + trapDamage + " de dano!");
                player.reduceHealth(trapDamage);
            }

            if (currentRoom.getEnemies().isEmpty() && currentRoom.getTreasures().isEmpty()) {
                System.out.println("Não tem nada aqui, vamos ter que procurar em outro lugar.");
            } else {
                System.out.println("Há inimigos ou habilidades nesta sala. Vamos explorar!");
            }
        }
    }

    private void showPlayerStatus() {
        System.out.println("Status do Slime:");
        System.out.println("Nome: " + player.getName());
        System.out.println("Saúde: " + player.getHealth());
        System.out.println("----------");
    }

    // Método para criar e desenhar a TenSura
    private List<Room> createRooms() {
        List<Room> rooms = new ArrayList<>();

        // Criação das salas
        Room room1 = new Room("Grande Rio Ameld");
        Room room2 = new Room("Floresta de Jura");
        Room room3 = new Room("Reino dos Anões");
        Room room4 = new Room("Grande Montanhas Canaat");
        Room room5 = new Room("Mundo Central");
        Room room6 = new Room("Cidade do Dragão Esquecido");
        Room room7 = new Room("Terra del Diablo");

        // Definição das conexões entre as salas
        room1.setExit("leste", room2);
        room2.setExit("oeste", room1);
        room2.setExit("sul", room3);
        room3.setExit("norte", room2);
        room3.setExit("leste", room4);
        room4.setExit("leste", room5);
        room4.setExit("norte", room6);
        room4.setExit("oeste", room3);
        room5.setExit("oeste", room4);
        room6.setExit("norte", room7);

        // Adição de inimigos às salas
        Enemy enemy1 = new Enemy("Vila de Goblins", 20, 5);
        Enemy enemy2 = new Enemy("Matilha de lobos Gigantes", 60, 12);
        Enemy enemy3 = new Enemy("Esquadão de Onis", 150, 15);
        Enemy enemy4 = new Enemy("Lord Orc", 300, 25);
        Enemy enemy5 = new Enemy("Hinata Sakaguchi - espadachim humana", 50, 30);
        Enemy enemy6 = new Enemy("Diablo", 600, 60);

        room2.addEnemy(enemy1);
        room3.addEnemy(enemy2);
        room4.addEnemy(enemy4);
        room5.addEnemy(enemy3);
        room6.addEnemy(enemy5);
        room7.addEnemy(enemy6);

        // Adição de tesouros às salas
        Treasure treasure1 = new Treasure("Absorver", 150);
        Treasure treasure2 = new Treasure("Regeneração", 30);
        Treasure treasure3 = new Treasure("Dissolver", 300);
        Treasure treasure4 = new Treasure("Mímica", 550);

        room1.addTreasure(treasure1);
        room3.addTreasure(treasure2);
        room5.addTreasure(treasure3);
        room7.addTreasure(treasure4);

        // Adição de armadilha em uma sala
        Trap trap1 = new Trap("Armadilha de espinhos", 10);
        Trap trap2 = new Trap("Armadilha de Fumaça Venenosa", 20);
        room2.setTrap(trap1);
        room4.setTrap(trap2);

        // Adicione as salas à lista de salas
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
        rooms.add(room6);
        rooms.add(room7);

        return rooms;
    }

    private void attackEnemy() {
        List<Enemy> enemies = currentRoom.getEnemies();
        int enemyCount = enemies.size();

        System.out.println("Selecione o inimigo para atacar:");

        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = enemies.get(i);
            System.out.println((i + 1) + ". " + enemy.getName() + " (HP: " + enemy.getHealth() + ")");
        }

        Scanner scanner = new Scanner(System.in);
        int enemyChoice = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do scanner

        if (enemyChoice >= 1 && enemyChoice <= enemyCount) {
            Enemy selectedEnemy = enemies.get(enemyChoice - 1);

            int playerDamage = player.attack(selectedEnemy);
            int enemyDamage = selectedEnemy.attack();

            System.out.println("Ebaa! Você atacou o inimigo " + selectedEnemy.getName() + " e causou " + playerDamage + " de dano. Continue assim!");
            System.out.println("Ai! O inimigo " + selectedEnemy.getName() + " contra-atacou, tomamos " + enemyDamage + " de dano.");

            selectedEnemy.reduceHealth(playerDamage);
            player.reduceHealth(enemyDamage);

            if (selectedEnemy.isDefeated()) {
                System.out.println("Você derrotou o inimigo " + selectedEnemy.getName() + "!");

                // Incrementa o número de inimigos derrotados
                player.incrementEnemiesDefeated();

                enemies.remove(selectedEnemy);
            }

            if (player.isDefeated()) {
                System.out.println("Coitado, você foi de arrasta pra cima! Boa sorte na próxima.");
            }
        } else {
            System.out.println("Ué, não entendi.");
        }
    }

    private void collectTreasure() {
        List<Treasure> treasures = currentRoom.getTreasures();
        int treasureCount = treasures.size();

        System.out.println("Selecione o tesouro para coletar:");

        for (int i = 0; i < treasureCount; i++) {
            Treasure treasure = treasures.get(i);
            System.out.println((i + 1) + ". " + treasure.getName() + " (" + treasure.getValue() + " pontos)");
        }

        Scanner scanner = new Scanner(System.in);
        int treasureChoice = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do scanner

        if (treasureChoice >= 1 && treasureChoice <= treasureCount) {
            Treasure selectedTreasure = treasures.remove(treasureChoice - 1);

            player.collectTreasure(selectedTreasure);
            System.out.println("Você coletou o tesouro " + selectedTreasure.getName() + "!");

            // Incrementa o número de tesouros coletados
            player.incrementTreasuresCollected();
        } else {
            System.out.println("Escolha inválida. Tente novamente.");
        }
    }

    // Iniciar o jogo
    public static void main(String[] args) {
        TensuraQuestGame game = new TensuraQuestGame();
        game.startGame();
    }
}
