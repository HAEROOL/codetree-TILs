import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int K, M;
	static int[][] board;
	static Deque<Integer> stones = new ArrayDeque<Integer>();

	static int[][] copyBoard() {
		int[][] res = new int[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				res[i][j] = board[i][j];
			}
		}
		return res;
	}

	static int[][] rotate(int sx, int sy) {
		int x = sx - 1;
		int y = sy - 1;
		int[][] tmp = copyBoard();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tmp[i + x][j + y] = board[(3 - 1 - j) + x][i + y];
			}
		}
		return tmp;
	}

	static int[] dx = { 1, 0, -1, 0 };
	static int[] dy = { 0, 1, 0, -1 };

	static int bfs(int sx, int sy, boolean[][] visited, int[][] map, PriorityQueue<int[]> pq) {
		Deque<int[]> q = new ArrayDeque<int[]>();
		PriorityQueue<int[]> p = new PriorityQueue<int[]>((a, b) -> {
			if(a[1] == b[1]) {
				return b[0] - a[0];
			}
			return a[1] - b[1];
		});
		q.offer(new int[] { sx, sy });
		visited[sx][sy] = true;
		p.offer(new int[] {sx, sy});
		int total = 1;
		while (!q.isEmpty()) {
			int[] coord = q.poll();
			int x = coord[0];
			int y = coord[1];
			for (int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				if (0 <= nx && nx < 5 && 0 <= ny && ny < 5) {
					if (!visited[nx][ny] && map[nx][ny] == map[x][y] && map[nx][ny] != 0) {
						q.offer(new int[] { nx, ny });
						visited[nx][ny] = true;
						total++;
						p.offer(new int[] {nx, ny});
					}
				}
			}
		}
		if (total > 2) {
			for(int[] coord : p) {
				pq.offer(coord);
			}
			return total;
		}
		return 0;

	}
	static class State{
		int total, r, c, rot;
		int[][] map;
		PriorityQueue<int[]> log;
		State(int total, int r, int c, int rot, int[][] tmp, PriorityQueue<int[]> log){
			this.total = total;
			this.r = r;
			this.c = c;
			this.rot = rot;
			this.map = tmp;
			this.log = log;
		}
	}
	static int ans = 0;
	public static void main(String[] args) throws IOException {
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		K = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new int[5][5];
		for (int i = 0; i < 5; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++) {
			stones.offer(Integer.parseInt(st.nextToken()));
		}
		for (int turn = 0; turn < K; turn++) {
			ans = 0;
			PriorityQueue<State> pq = new PriorityQueue<State>((a, b) -> {
				if(b.total == a.total) {
					if(a.rot == b.rot) {
						if(a.c == b.c) {
							return a.r - b.r;
						}
						return a.c - b.c;
					}
					return a.rot - b.rot;
				}
				return	b.total - a.total;
			});
			for (int r = 1; r < 4; r++) {
				for (int c = 1; c < 4; c++) {
					for (int rot = 1; rot < 4; rot++) {
						// 90도 회전하기
						int[][] tmpboard = rotate(r, c);
						PriorityQueue<int[]> log = new PriorityQueue<int[]>((a, b) -> {
							if(a[1] == b[1]) {
								return b[0] - a[0];
							}
							return a[1] - b[1];
						});
						// BFS
						boolean[][] visited = new boolean[5][5];
						int total = 0;
						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 5; j++) {
								if (!visited[i][j] && tmpboard[i][j] != 0) {
									total += bfs(i, j, visited, tmpboard, log);
								}
							}
						}
						if(total > 0) {
							pq.offer(new State(total, r, c, rot, tmpboard, log));
						}
					}
					
				}
			}
			// 결과 선정해서 적용하기
			if(pq.isEmpty()) {
				break;
			}
			State s = pq.peek();
			int size = s.log.size();
			int[][] resboard = s.map;
//			System.out.println("result : " + s.total + " " + s.r + " " + s.c + " " + s.rot);
			ans += s.total;
//			System.out.print("+ " + s.total);
			for(int i = 0 ; i < size ; i++) {
				int[] coord = s.log.poll();
				int x = coord[0];
				int y = coord[1];
				if(stones.size() == 0) {
					resboard[x][y] = 0;
					continue;
				}
				int n = stones.poll();
				resboard[x][y] = n;
			}

			while(true) {
//				System.out.println();
//				for (int[] row : resboard) {
//					System.out.println(Arrays.toString(row));
//				}
//				System.out.println();
				int total = 0;
				boolean[][] visited = new boolean[5][5];
				PriorityQueue<int[]> p = new PriorityQueue<int[]>((a, b) -> {
					if(a[1] == b[1]) {
						return b[0] - a[0];
					}
					return a[1] - b[1];
				});
				for(int i = 0 ; i < 5 ; i++) {
					for(int j = 0 ; j < 5 ; j++) {
						if(!visited[i][j] && resboard[i][j] != 0) {
							int res = bfs(i, j, visited, resboard, p);
							total += res;
						}
					}
				}
				size = p.size();
				for(int i = 0 ; i < size ; i++) {
					int[] coord = p.poll();
					int x = coord[0];
					int y = coord[1];
					if(stones.size() == 0) {
						resboard[x][y] = 0;
						continue;
					}
					int n = stones.poll();
					resboard[x][y] = n;
				}
				board = resboard;
				if(total == 0) break;
				ans += total;
			}
//			System.out.println();
			bw.write(ans + " ");
		}
		bw.close();
	}
}