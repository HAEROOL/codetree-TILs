import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	static int[][] copyMap() {
		int[][] res = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				res[i][j] = map[i][j];
			}
		}
		return res;
	}

	static void rotate(int sx, int sy, int w) {
//		System.out.println("rotate" + sx + ", " +sy + "부터" + w+"칸");
		int[][] res = copyMap();
//		System.out.println("전");
//		for(int[] row : map) {
//			System.out.println(Arrays.toString(row));
//		}
//		System.out.println();
		for (int i = sx; i < sx + w; i++) {
			for (int j = sy; j < sy + w; j++) {
				int ox = i - sx, oy = j - sy;
				int rx = oy, ry = w - ox - 1;
				res[sx + rx][sy +ry] = map[i][j];
			}
		}
		map = res;
//		System.out.println("후");
//		for(int[] row : map) {
//			System.out.println(Arrays.toString(row));
//		}
//		System.out.println();
		for(Player p : players) {
			if(p == null) continue;
			if(sx <= p.x && p.x < sx + w && sy <= p.y && p.y < sy + w) {
//				System.out.println(p.id+"번: " + p.x + ", " + p.y);
				int x = p.x;
				int y = p.y;
				int ox = x - sx, oy = y - sy;
				int rx = oy, ry = w - ox - 1;
				p.x = sx + rx;
				p.y = sy + ry;
				
//				System.out.println(p.id+"번: " + p.x + ", " + p.y);
			}
		}
		if(sx <= ex && ex < sx + w && sy <= ey && ey < sy + w) {
//			System.out.println("출구: " +ex + ", " + ey);
			int x = ex;
			int y = ey;
			int ox = x - sx, oy = y - sy;
			int rx = oy, ry = w - ox - 1;
			ex = sx + rx;
			ey = sy + ry;
//			System.out.println("출구: " +ex + ", " + ey + "로 이동");
		}
	}

	static int[] findBox() {
		for (int w = 2; w < N; w++) {
			for (int sx = 0; sx < N - w; sx++) {
				for (int sy = 0; sy < N - w; sy++) {
					boolean p = false;
					boolean exit = false;
					for (int i = sx; i < sx + w; i++) {
						for (int j = sy; j < sy + w; j++) {
							if (map[i][j] == -11) {
								exit = true;
							}
							for(Player pl : players) {
								if(pl == null) continue;
								if(pl.x == i && pl.y == j) {
									p = true;
									break;
								}
							}
						}
						if (p && exit)
							return new int[] { sx, sy, w };
					}
				}
			}
		}
		return new int[] { -1, -1, -1 };
	}
	static void breakWall(int sx, int sy, int w) {
		for(int i = sx ; i < sx + w ; i++) {
			for(int j = sy ; j < sy + w ; j++) {
				if(map[i][j] > 0) {
					map[i][j]--;
				}
			}
		}
	}
	static class Player{
		int x, y, id;
	}
	static int N, M, K;
	static int[][] map;
	static Player[] players;
	static int[] distance;
	static boolean[] escape;
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, 1, -1};
	static int ex, ey;
	public static void main(String[] args) throws IOException{
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		players = new Player[M + 1];
		distance = new int[M + 1];
		escape = new boolean[M + 1];
		
		for(int i = 0 ; i < N ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0 ; j < N ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i = 1 ; i < M + 1 ; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			players[i] = new Player();
			players[i].x = x;
			players[i].y = y;
			players[i].id = i;
		}
		st = new StringTokenizer(br.readLine());
		ex = Integer.parseInt(st.nextToken()) - 1;
		ey = Integer.parseInt(st.nextToken()) - 1;
		map[ex][ey] = -11;
		
		for(int time = 0 ; time < K ; time++) {

			// 모든 참가자가 이동
			for(Player p : players) {
				if(p == null) continue;
				int x = p.x;
				int y = p.y;
				// 기존보다 가까워지는 방향으로 이동하기
				int dist = Math.abs(x - ex) + Math.abs(y - ey);
				int dir = -1;
				for(int i = 0 ; i < 4 ; i++) {
					int nx = x + dx[i];
					int ny = y + dy[i];
					if(0 <= nx && nx < N && 0 <= ny && ny < N && map[nx][ny] <= 0){
						int d = Math.abs(nx - ex) + Math.abs(ny - ey);
						if(d < dist) {
							dist = d;
							dir = i;
						}
					}
				}
				if(dir == -1) continue;
				// 찾은 위치로 업데이트
				p.x = x + dx[dir];
				p.y = y + dy[dir];
				distance[p.id] += 1;
				if(p.x == ex && p.y == ey) {
					escape[p.id] = true;
					players[p.id] = null;
				}
			}
//			for(int[] row : map) {
//				System.out.println(Arrays.toString(row));
//			}
//			System.out.println();
//			int[][] newMap = new int[N][N];
//			for(Player pl : players) {
//				if(pl == null) continue;
//				newMap[pl.x][pl.y] = pl.id;
//			}
//			for(int[] row : newMap) {
//				System.out.println(Arrays.toString(row));
//			}
//			System.out.println(Arrays.toString(distance));
//			System.out.println(Arrays.toString(escape));
//			System.out.println();
			
			int[] res = findBox();
			int sx = res[0];
			int sy = res[1];
			int w = res[2];
			int cnt = 0;
			for(int i = 1 ; i < M + 1 ; i++) {
				if(escape[i]) cnt++;
			}
			if(cnt == M) break;
			rotate(sx, sy, w);
			breakWall(sx, sy, w);
		}
		int total = 0;
//		System.out.println(Arrays.toString(distance));
		for(int i = 1; i < M + 1 ;i++) {
			total += distance[i];
		}
		bw.write(total + "\n");
		bw.write((ex + 1) + " " + (ey + 1));
		bw.close();
	}
}