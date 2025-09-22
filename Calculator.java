import java.util.Scanner;
public class Calculator{
    public static double add(double a,double b){
        return a+b;
    }
    public static double sub(double a,double b){
        return a-b;
    }
    public static double mul(double a,double b){
        return a*b;
    }
    public static double div(double a,double b){
        if (b==0) {
            System.out.println("Can't divided");
        }
        return a/b;
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        boolean keepRunning=true;
        System.out.println("===Java Calculator===");

        while (keepRunning) {
            System.out.println("\nSelect Operation:");
            System.out.println("1.)Addition:");
            System.out.println("2.)Subtraction:");
            System.out.println("3.)Multiplication:");
            System.out.println("4.)Division:");
            System.out.println("5.)Exit:");
            System.out.println("Enter your choice (1-5):");

            if (!sc.hasNextLine()) {
                System.out.println("It is not correct number,Pls Enter a number 1-5:");
                sc.nextLine();
                continue;
            }
            int choice=sc.nextInt();

            if (choice==5) {
                System.out.println("Existing the calculation,Thank You,Good Bye!");
                break;
            }
            System.out.println("Enter the 1st number:");

            while (!sc.hasNextDouble()) {
                System.out.println("Wrong input,Pls enter correct number:");
                sc.next();
            }
            double num1=sc.nextDouble();

            System.out.println("Enter the 2nd number:");

            while (!sc.hasNextDouble()) {
                System.out.println("Wrong input,Pls enter correct number:");
                sc.next();
            }
            double num2=sc.nextDouble();

            sc.nextLine();

            try{
                double result;
                switch (choice) {
                    case 1:
                        result=add(num1, num2);
                        System.out.println("Result:"+result);
                        break;
                    case 2:
                        result=sub(num1, num2);
                        System.out.println("Result:"+result);
                        break;
                    case 3:
                        result=mul(num1, num2);
                        System.out.println("Result:"+result);
                        break;
                    case 4:
                        result=div(num1, num2);
                        System.out.println("Result:"+result);
                        break;                
                    default:
                        System.out.println("Invalid choice,pick number 1-5:");
                        
                }
            } catch (ArithmeticException e) {
                System.out.println("Error "+e.getMessage());
            }
            System.out.println("If you want another Calculation? (y,n):");
            String again=sc.nextLine();
            if (!again.equalsIgnoreCase("y") && !again.equalsIgnoreCase("yes")) {
                keepRunning=false;
                System.out.println("Bye");
            }
        }
        sc.close();
    }
}