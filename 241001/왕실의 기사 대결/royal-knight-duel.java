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

	static int count(int r, int c, int w, int h) {
		int cnt = 0;
		for (int i = r; i < r + h; i++) {
			for (int j = c; j < c + w; j++) {
				if (map[i][j] == 1) {
					cnt++;
				}
			}
		}
		return cnt;
	}

	static boolean isPushed(Knight k1, Knight k2) {
		int r1 = k1.r;
		int r2 = k2.r;
		int c1 = k1.c;
		int c2 = k2.c;
		int w1 = k1.w;
		int w2 = k2.w;
		int h1 = k1.h;
		int h2 = k2.h;
		if (r1 + h1 - 1 >= r2 && c1 + w1 - 1 >= c2 && r2 + h2 - 1 >= r1 && c2 + w2 - 1 >= c1) {
			return true;
		}
		return true;
	}

	static class Knight {
		int id, r, c, w, h, hp, total;

		public Knight(int id, int r, int c, int h, int w, int hp, int total) {
			this.id = id;
			this.r = r;
			this.c = c;
			this.w = w;
			this.h = h;
			this.hp = hp;
			this.total = total;
		}
	}

	static void move(int id, int d) {
		Set<Integer> set = new HashSet<>();
		Deque<Integer> q = new ArrayDeque<>();
		int[] dmgs = new int[N];
		q.add(id);
		set.add(id);
		while (!q.isEmpty()) {
			Knight nk = knights[q.poll()];
			if (dead[id])
				continue;
			int nx = nk.r + dx[d];
			int ny = nk.c + dy[d];
//			System.out.println(nk.id);
			for(int i = nx ; i < nx + nk.h ; i ++) {
				for(int j = ny ; j < ny + nk.w ; j++) {
					if(map[i][j] == 2) {
//						System.out.println((nk.id + 1) + ": " + i + ", " + j);
						return;
					}else if(map[i][j] == 1) {
						dmgs[nk.id]++;
					}
				}
			}
			for (int i = 0; i < N; i++) {
				if (set.contains(i))
					continue;
				Knight k = knights[i];
				int r1 = nx;
				int r2 = k.r;
				int c1 = ny;
				int c2 = k.c;
				int w1 = nk.w;
				int w2 = k.w;
				int h1 = nk.h;
				int h2 = k.h;
				if (r1 + h1 - 1 >= r2 && c1 + w1 - 1 >= c2 && r2 + h2 - 1 >= r1 && c2 + w2 - 1 >= c1) {
//					System.out.println((nk.id + 1) + ", " + (k.id + 1) + "겹ㅣㅊ");
					q.add(i);
					set.add(i);
				}
			}

		}
		dmgs[id] = 0;
//		System.out.println(Arrays.toString(dmgs));
		for (int s : set) {
			Knight k = knights[s];
			knights[s] = new Knight(k.id, k.r + dx[d], k.c + dy[d], k.h, k.w, k.hp - dmgs[k.id], k.total + dmgs[k.id]);
			k.r += dx[d];
			k.c += dy[d];
			k.hp -= dmgs[k.id];
			k.total += dmgs[k.id];
			if (k.hp <= 0)
				dead[k.id] = true;
		}
	}

	static int L, N, Q;
	static int[][] map;
	static Knight[] knights;
	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, 1, 0, -1 };
	static boolean[] dead;

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		map = new int[L + 2][L + 2];
		Arrays.fill(map[0], 2);
		Arrays.fill(map[L + 1], 2);
		for (int i = 1; i < L + 1; i++) {
			st = new StringTokenizer(br.readLine());
			map[i][0] = 2;
			map[i][L + 1] = 2;
			for (int j = 1; j < L + 1; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
//		System.out.println(L + " " + N + " " + Q);
//		for (int[] row : map) {
//			System.out.println(Arrays.toString(row));
//		}
		knights = new Knight[N];
		dead = new boolean[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			knights[i] = new Knight(i, r, c, h, w, k, 0);
		}
//		System.out.println("start");
//		for (Knight k : knights) {
//			System.out.println((k.id + 1) + ": " + k.r + "," + k.c + " : hp: " + k.hp + ", totaldmg:  " + k.total);
//		}
		int[][] cmds = new int[Q][2];
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			cmds[i] = new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) };
		}
		for (int[] cmd : cmds) {
			int kid = cmd[0] - 1;
			int d = cmd[1];
//			System.out.println("##############333"+(kid + 1) + "move " + d);
			move(kid, d);
//			for (Knight k : knights) {
//				System.out.println((k.id + 1) + ": " + k.r + "," + k.c + " : hp: " + k.hp + ", totaldmg:  " + k.total);
//			}
//			System.out.println();
		}
		int ans = 0;
		for (Knight k : knights) {
			if (!dead[k.id]) {
				ans += k.total;
			}
		}
		bw.write(ans + "\n");
		bw.close();
	}
}