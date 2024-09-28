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
	static int[] rdx = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] rdy = {0, -1, -1, -1, 0, 1, 1, 1};
	static int[] sdx = {-1, 0, 1, 0};
	static int[] sdy = {0, 1, 0, -1};
	static class Santa {
		// 1이면 정상, 0이면 기절, -1이면 탈락
		int x, y, num, state, score, cnt;
		public Santa(int x, int y, int id) {
			this.x = x;
			this.y = y;
			this.num = id;
			this.state = 1;
			this.score = 0;
			this.cnt = 0;
		}
		int[] find() {
			int d = (int)Math.pow(rudolph.x - this.x, 2) + (int)Math.pow(rudolph.y - this.y, 2);
			int tx = -2;
			int ty = -2;
			int dir = -1;
			for(int i = 0 ; i < 4 ; i++) {
				int nx = this.x + sdx[i];
				int ny = this.y + sdy[i];
				if(0 <= nx && nx < N && 0 <= ny && ny < N && map[nx][ny] == 0 ) {
					int dist = (int)Math.pow(rudolph.x - nx, 2) + (int)Math.pow(rudolph.y - ny, 2);
					if(dist < d ) {
						dir = i;
						d = dist;
						tx = sdx[i];
						ty = sdy[i];
					}
				}
			}
			return new int[] {tx, ty, dir};
		}
		void move() {
			int[] target = find();
			if(target[0] == -2 && target[1] == -2) {
				return;
			}
			// 루돌프랑 충돌하면 
			if(target[0] + this.x == rudolph.x && target[1] + this.y == rudolph.y) {
				map[this.x][this.y] = 0;
				this.x += target[0];
				this.y += target[1];
				crash(target[2]);
			}else {
				// 충돌안하면 그냥 이동
				map[this.x][this.y] = 0;
				this.x += target[0];
				this.y += target[1];
				map[this.x][this.y] = this.num;
				
			}	
		}
		void crash(int dir) {
			Deque<Integer> movelist = new ArrayDeque<Integer>();
			// 루돌프랑 충돌
			this.score += D;
			this.state = 0;
			this.cnt = 2;
//			System.out.println(this.num + " crashed-산타가박음");
			int nx = this.x + sdx[dir] * D * -1;
			int ny = this.y + sdy[dir] * D * -1;
//			System.out.println(nx + " " + ny);
			if(0 <= nx && nx < N && 0 <= ny && ny < N) {
				if(map[nx][ny] != 0) {
					movelist.offer(map[nx][ny]);
					map[nx][ny] = this.num;
				}else {
					map[nx][ny] = this.num;
				}
				this.x = nx;
				this.y = ny;
			}else {
				this.state = -1;
				map[this.x][this.y] = 0;
			}
			while(!movelist.isEmpty()) {
				int n = movelist.poll();
				Santa s = santas[n];
				s.x += sdx[dir]*-1;
				s.y += sdy[dir]*-1;
				if(0 <= s.x && s.x < N && 0 <= s.y && s.y < N) {
					if(map[s.x][s.y] == 0) {
						map[s.x][s.y] = s.num;
					}else {
						movelist.add(map[s.x][s.y]);
						map[s.x][s.y] = s.num;
					}
				}else {
					map[s.x - sdx[dir]][s.y - sdy[dir]] = 0;
					s.state = -1;
				}
			}
			
		}
	}

	static class Rudolph {
		int x, y, c;

		public Rudolph(int x, int y, int c) {
			// TODO Auto-generated constructor stub
			this.x = x;
			this.y = y;
			this.c = c;
		}

		int[] find() {
			PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> {
				if (a[0] == b[0]) {
					if (a[1] == b[1]) {
						return b[2] - a[2];
					}
					return b[1] - a[1];
				}
				return a[0] - b[0];
			});
			for (Santa s : santas) {
				if(s == null) continue;
				if(s.state != -1) {
					int dist = (int)Math.pow(this.x - s.x, 2) +  (int)Math.pow(this.y - s.y, 2);
					pq.add(new int[] { dist, s.x, s.y });					
				}
			}
			return pq.poll();
		}
		
		int[] move() {
			int[] target = find();
			int tx = 0;
			int ty = 0;
			if(target[1] > this.x) {
				tx = 1;
			}else if(target[1] < this.x) {
				tx = -1;
			}
			if(target[2] > this.y) {
				ty = 1;
			}else if(target[2] < this.y) {
				ty = -1;
			}
			this.x += tx;
			this.y += ty;
			return new int[] {target[1], target[2], tx, ty};
		}
		void crash(int tx, int ty, int dx, int dy) {
			int santaid = map[tx][ty];
			Santa tSanta = santas[santaid];
			map[tSanta.x][tSanta.y] = 0;
			Deque<Integer> moveList = new ArrayDeque<Integer>();
//			System.out.println(santaid + " crashed -루돌프가 박");
//			System.out.print("산타 moved" +"from" + tSanta.x + " " + tSanta.y);
			// 타겠된 첫 산타 움직이기(루돌프 방향으로 C칸 이동)
			tSanta.x += dx * C;
			tSanta.y += dy * C;
			tSanta.score += C;
//			System.out.println("to" + tSanta.x + " " + tSanta.y);
			if(0 <= tSanta.x && tSanta.x < N && 0 <= tSanta.y && tSanta.y < N) {
				tSanta.state = 0;
				tSanta.cnt = 2;
				if(map[tSanta.x][tSanta.y] != 0) {
					moveList.add(map[tSanta.x][tSanta.y]);
					map[tSanta.x][tSanta.y] = tSanta.num;
				}else {
					map[tSanta.x][tSanta.y] = tSanta.num;
				}
			}else {
				// 범위 넘어가면 탈락
				tSanta.state = -1;
				map[tx][ty] = 0;
			}
			// 밀려난 산타부터 연쇄작용
			while(!moveList.isEmpty()) {
				int n = moveList.poll();
				Santa s = santas[n];
				s.x += dx;
				s.y += dy;
				if(0 <= s.x && s.x < N && 0 <= s.y && s.y < N) {
					if(map[s.x][s.y] == 0) {
						map[s.x][s.y] = s.num;
					}else {
						moveList.add(map[s.x][s.y]);
						map[s.x][s.y] = s.num;
					}
				}else {
					map[s.x - dx][s.y - dy] = 0;
					s.state = -1;
				}
			}
		}
	}
	static int N, M, P, C, D;
	static int[][] map;
	static Rudolph rudolph;
	static Santa[] santas;
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		st = new StringTokenizer(br.readLine());
		int rx = Integer.parseInt(st.nextToken()) - 1;
		int ry = Integer.parseInt(st.nextToken()) - 1;
		rudolph = new Rudolph(rx, ry, C);
		santas = new Santa[P + 1];
		for(int i = 1 ; i < P + 1 ; i++) {
			st = new StringTokenizer(br.readLine());
			int p = Integer.parseInt(st.nextToken());
			int sx = Integer.parseInt(st.nextToken()) - 1;
			int sy = Integer.parseInt(st.nextToken()) - 1;
			Santa s = new Santa(sx, sy, p);
			// 이동시 동기화 시켜줘야 댐
			map[sx][sy] = p;
			santas[p] = s;		
		}
		for(int i = 1 ; i <= M ; i++) {
//			System.out.println(i);
			for(Santa s : santas) {
				if(s == null) continue;
				if(s.cnt != 0 && s.state != -1) {
					s.cnt -= 1;
					if(s.cnt == 0) {
						s.state = 1;
					}
				}
			}
			// 루돌프 움직
			// 타겟 위치 x,y 움직인 방향 x, y
			int[] target = rudolph.move();
			// 충돌 발생했는지 확인하고, 충돌했으면 충돌 작업
			if(map[rudolph.x][rudolph.y] != 0) {
				rudolph.crash(rudolph.x, rudolph.y, target[2], target[3]);
			}
//			System.out.println("rx: " + rudolph.x + "ry: " + rudolph.y);
			// 산타 움직임(순서대로)
			for(Santa s : santas) {
				if(s == null) continue;
				if(s.state == 1) {
					s.move();
//					for(int[] row : map) {
//						System.out.println(Arrays.toString(row));
//					}
//					System.out.println();
				}
			}
			// 살아있는 산타들 확인, 다 죽었으면 종
			boolean isContinue = false;
			for(Santa s : santas) {
				if(s == null) continue;
				if(s.state != -1) {
					isContinue = true;
					break;
				}
			}
			for(Santa s : santas) {
				if(s == null) continue;
				if(s.state != -1) {
					s.score += 1;
				}
			}
			if(!isContinue) {
				break;
			}
//			for(Santa s : santas) {
//				if(s != null) {
//					System.out.print(s.score + " ");
//				}
//			}
//			System.out.println();
//			System.out.println("rx: " + rudolph.x + " ry: " + rudolph.y);
		}
		for(Santa s : santas) {
			if(s != null) {
				bw.write(s.score + " ");
			}
		}
		bw.close();
	}
}