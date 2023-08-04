import java.util.*;

public class Main {

    private static int cowsCount;
    private static int bullsCount;
    private static String randomNumber;
    private static int length;
    private static int symbolQuantity;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Input the length of the secret code:");
            length = scanner.nextInt();
            if (length < 1) {
                System.out.printf("Error: can't generate a secret number with a length of %d.", length);
                return;
            }
        } catch (InputMismatchException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", scanner.nextLine());
            return;
        }

        try {
            System.out.println("Input the number of possible symbols in the code:");
            symbolQuantity = scanner.nextInt();

            if (symbolQuantity > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                return;
            } else if (length > symbolQuantity) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, symbolQuantity);
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error:  \"%s\" isn't a valid number." + scanner.nextLine());
            return;
        }


        randomNumber = generateRandomNumber(length, symbolQuantity);
        System.out.println("The random secret number is " + randomNumber);
        verifyCowsAndBulls(randomNumber);
        System.out.println("Congratulations! You guessed the secret code.");
    }

    public static String generateRandomNumber(int length, int symbolQuantity) {

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        boolean isAlwaysDigit = false;
        boolean firstDigit = true;

        if(symbolQuantity <= 10) {
            isAlwaysDigit = true;
        } else {
            symbolQuantity -= 10;
        }

        HashSet<String> uniqueDigits = new HashSet<>();

        while (uniqueDigits.size() < length) {
            boolean isDigit = true;

            if(!isAlwaysDigit) {
                isDigit = random.nextBoolean();
            }

            if (isDigit) {
                String digit = String.valueOf(random.nextInt(10));
                if (!uniqueDigits.contains(digit)) {
                    if (firstDigit && digit.equals("0")) {
                        continue;
                    } else {
                        sb.append(digit);
                        uniqueDigits.add(digit);
                        firstDigit = false;
                    }
                }
            }
            else  {
                char randomChar = (char) ('a' + random.nextInt(symbolQuantity));
                if (!uniqueDigits.contains(randomChar)) {
                    uniqueDigits.add(String.valueOf(randomChar));
                    sb.append(randomChar);
                }
            }
        }
        printPreparation(isAlwaysDigit, length, symbolQuantity);
        return sb.toString();
    }

    public static void printPreparation(boolean isAlwaysDigit, int length, int symbolQuantity) {
        System.out.print("The secret is prepared: ");
        if (isAlwaysDigit) {
            printStars(length);
            System.out.println(" (0-9)");
        } else {
            printStars(length);
            System.out.printf(" (0-9, a-%c).%n", (char) ('a' + symbolQuantity - 1 ));
        }
        System.out.println("Okay, let's start a game!");
    }

    public static void printStars(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }
    }

    public static void verifyCowsAndBulls(String secretCode) {
        Scanner scanner = new Scanner(System.in);
        String[] splitSecretCode = secretCode.split("");
        String userGuess = "";
        int turnCount = 0;
        while (!secretCode.equals(userGuess)) {
            cowsCount = 0;
            bullsCount = 0;
            turnCount++;
            System.out.println("Turn " + turnCount + ":");
            userGuess = scanner.nextLine();

            if (userGuess.length() > 0 && userGuess.length() <= secretCode.length()) {
                String[] splitUserGuess = userGuess.split("");
                for (int i = 0; i < splitUserGuess.length; i++) {
                    if (splitUserGuess[i].equals(splitSecretCode[i])) {
                        bullsCount += 1;
                    } else if (splitUserGuess[i].equals(splitSecretCode[i]) && i != i) {
                        cowsCount += 1;
                    }
                }
            }
            printOutput();
        }
    }

    public static void printOutput() {
        if (bullsCount > 0 && cowsCount > 0) {
            System.out.println("Grade: " + bullsCount + " bull(s) and " + cowsCount + " cow(s)");
        } else if (bullsCount > 0 && cowsCount == 0) {
            System.out.println("Grade: " + bullsCount + " bull(s)");
        } else if (bullsCount == 0 && cowsCount > 0) {
            System.out.println("Grade: " + cowsCount + " cows(s)");
        } else {
            System.out.println("Grade: None. The secret code is " + randomNumber);
        }
    }
}


