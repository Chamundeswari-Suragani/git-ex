import java.util.Scanner;
import java.util.Random;
public class NumberGuessingGame {
    Random random = new Random();
    int randomNumber = random.nextInt(100) + 1;
    Scanner scanner = new Scanner(System.in);
    {
    while(true) 
    {
        System.out.print("Enter your guess: ");
        int userGuess = scanner.nextInt();
        if (userGuess == randomNumber) {
        System.out.println("Congratulations! You guessed the correct number.");
        break;
         } 
        else if (userGuess < randomNumber) {
            System.out.println("Your guess is too low. Try again.");
         } 
        else {
            System.out.println("Your guess is too high. Try again.");
         }
    }

 scanner.close();

}
}
