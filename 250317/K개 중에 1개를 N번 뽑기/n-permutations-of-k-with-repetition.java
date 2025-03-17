import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static void combination(int x, int[] sel){
        if(x == sel.length){
            for(int e : sel) {
            	System.out.print(e + " ");
            }
            System.out.println();
            return;
        }
        for(int i = 1 ; i <= k ; i++) {
        	sel[x] = i;
        	combination(x + 1, sel);
        }
           
    }
    static int k;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        k = sc.nextInt();
        int n = sc.nextInt();
        // Please write your code here.
        combination(0, new int[n]);
    }
}