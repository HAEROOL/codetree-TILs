import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int[][] dy = {
			{0, 0, 0, 0, 0},
			{0, 1, 0, -1, 0},
			{1, 1, -1, -1, 0}
	};
	static int[][] dx = {
			{-2, -1, 1, 2, 0},
			{-1, 0, 1, 0, 0},
			{-1, 1, 1, -1, 0}
	};
    static void combination(int x, int[] sel){
        if(x == sel.length){
//        	System.out.println(Arrays.toString(sel));
        	int[][] tmp = new int[N][N];
        	int cnt = 0;
        	for(int i = 0 ; i < sel.length ; i++) {
        		int a = spots.get(i)[0];
        		int b = spots.get(i)[1];
        		int d = sel[i] - 1;
        		for(int j = 0 ; j < 5 ; j++) {
        			int nx = a + dx[d][j];
        			int ny = b + dy[d][j];
        			if(0 <= nx && nx < N && 0 <= ny && ny < N && tmp[nx][ny] == 0) {
        				tmp[nx][ny] = 1;
        				cnt++;
        			}
        		}
        	}
//        	for(int[] row : tmp) {
//        		System.out.println(Arrays.toString(row));
//        	}
//        	System.out.println();
        	ans = Math.max(cnt, ans);
            return;
        }
        for(int i = 1 ; i <= 3 ; i++) {
        	sel[x] = i;
        	combination(x + 1, sel);
        }
           
    }
    static int N;
    static List<int[]> spots;
    static int ans = 0;
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        spots = new ArrayList<>();
        for(int i = 0 ; i < N ; i++) {
        	st = new StringTokenizer(br.readLine());
        	for(int j = 0 ; j < N ; j++) {
        		int tile = Integer.parseInt(st.nextToken());
        		if(tile == 1)spots.add(new int[] {i, j});
        	}
        }
//        for(int[] e : spots) {
//        	System.out.println(Arrays.toString(e));
//        }
        // Please write your code here.
        combination(0, new int[spots.size()]);
        System.out.println(ans);
    }
}