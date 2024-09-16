import java.util.Random;
import java.util.Scanner;

public class Connect4 {

    static final int LINNHAS = 6;
    static final int COLUNAS = 7;
    static final char VAZIO = ' ';
    static final char JOGADOR = 'J';
    static final char COMPUTADOR = 'C';

    public static void main(String[] args) {
        char[][] tabuleiro = new char[LINNHAS][COLUNAS];
        iniciaTabuleiro(tabuleiro);
        Scanner scanner = new Scanner(System.in);
        boolean turnoJogador = true;
        boolean gameRunning = true;

        while (gameRunning) {
            printTabuleiro(tabuleiro);
            if (turnoJogador) {
                System.out.print("Escolha uma coluna (1-7): ");
                int col = scanner.nextInt() - 1;
                if (!movimento(tabuleiro, col, JOGADOR)) {
                    System.out.println("Coluna cheia ou inválida. Tente novamente.");
                    continue;
                }
            } else {
                int col = getMovimentoComputador(tabuleiro);
                movimento(tabuleiro, col, COMPUTADOR );
                System.out.println("A máquina escolheu a coluna " + (col + 1));
            }

            if (vitoria(tabuleiro,turnoJogador ? JOGADOR : COMPUTADOR )) {
                printTabuleiro(tabuleiro);
                System.out.println((turnoJogador ? "Você" : "A máquina") + " ganhou!");
                gameRunning = false;
            } else if (tabuleiroCheio(tabuleiro)) {
                printTabuleiro(tabuleiro);
                System.out.println("Empate!");
                gameRunning = false;
            }

            turnoJogador = !turnoJogador;
        }

        scanner.close();
    }

    private static void iniciaTabuleiro(char[][] tabuleiro) {
        for (int r = 0; r < LINNHAS; r++) {
            for (int c = 0; c < COLUNAS; c++) {
                tabuleiro[r][c] = VAZIO;
            }
        }
    }

    private static void printTabuleiro(char[][] tabuleiro) {
        for (int r = 0; r < LINNHAS; r++) {
            for (int c = 0; c < COLUNAS; c++) {
                System.out.print("|" + tabuleiro[r][c]);
            }
            System.out.println("|");
        }
        for (int c = 0; c < COLUNAS; c++) {
            System.out.print(" " + (c + 1));
        }
        System.out.println();
    }

    private static boolean movimento(char[][]tabuleiro, int col, char player) {
        if (col < 0 || col >= COLUNAS || tabuleiro[0][col] != VAZIO) {
            return false;
        }
        for (int r = LINNHAS - 1; r >= 0; r--) {
            if (tabuleiro[r][col] == VAZIO) {
                tabuleiro[r][col] = player;
                return true;
            }
        }
        return false;
    }

    private static boolean tabuleiroCheio(char[][] tabuleiro) {
        for (int c = 0; c < COLUNAS; c++) {
            if (tabuleiro[0][c] == VAZIO) {
                return false;
            }
        }
        return true;
    }

    private static boolean vitoria(char[][] tabuleiro, char player) {
        // Check horizontal, vertical, and diagonal wins
        return (vitoriaHorizontal(tabuleiro, player) || vitoriaVertical(tabuleiro, player) ||
                verificaDiagonais(tabuleiro, player));
    }

    private static boolean vitoriaHorizontal(char[][] tabuleiro, char player) {
        for (int r = 0; r < LINNHAS; r++) {
            for (int c = 0; c <= COLUNAS - 4; c++) {
                if (tabuleiro[r][c] == player && tabuleiro[r][c + 1] == player &&
                        tabuleiro[r][c + 2] == player && tabuleiro[r][c + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean vitoriaVertical(char[][] tabuleiro, char player) {
        for (int c = 0; c < COLUNAS; c++) {
            for (int r = 0; r <= LINNHAS - 4; r++) {
                if (tabuleiro[r][c] == player && tabuleiro[r + 1][c] == player &&
                        tabuleiro[r + 2][c] == player && tabuleiro[r + 3][c] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean verificaDiagonais(char[][] tabuleiro, char player) {
        // Verifique se há diagonais em ambas as direções
        for (int r = 0; r <= LINNHAS - 4; r++) {
            for (int c = 0; c <= COLUNAS - 4; c++) {
                if (tabuleiro[r][c] == player && tabuleiro[r + 1][c + 1] == player &&
                        tabuleiro[r + 2][c + 2] == player && tabuleiro[r + 3][c + 3] == player) {
                    return true;
                }
                if (tabuleiro[r][c + 3] == player && tabuleiro[r + 1][c + 2] == player &&
                        tabuleiro[r + 2][c + 1] == player && tabuleiro[r + 3][c] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int getMovimentoComputador(char[][] tabuleiro) {
        Random rand = new Random();
        int col;
        do {
            col = rand.nextInt(COLUNAS);
        } while (tabuleiro[0][col] != VAZIO);
        return col;
    }
}
