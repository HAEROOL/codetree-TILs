import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static void combination(int x, int[] sel){
//    	System.out.println(x);
        if(x == sel.length){
//        	System.out.println(Arrays.toString(sel));
            cnt++;
            return;
        }
        for(int i = 1 ; i <= 4 ; i++) {
        	if(x + i > k) break;
        	for(int j = x ; j < x + i ; j++) {
        		sel[j] = i;
        	}
        	combination(x + i, sel);
        }
           
    }
    static int k;
    static int cnt = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        k = sc.nextInt();
        // Please write your code here.
        combination(0, new int[k]);
        System.out.println(cnt);
    }
}