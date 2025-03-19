const fs = require("fs");
const input = fs.readFileSync(0).toString().trim().split('\n');

const [n, m] = input[0].split(' ').map(Number);
const grid = input.slice(1, 1 + Number(n)).map(line => line.split(' ').map(Number));
let K = -1;
grid.forEach(row => row.forEach(block => K = Math.max(K, block)))

const dx = [1, 0, -1, 0]
const dy = [0, 1, 0, -1]

let ans = 0;
let ansCnt = 0;
function bfs(k){
    const v = new Array(n).fill(null).map(() => new Array(m).fill(false));
    let size = 0
    let cnt = 0;
    for(let i = 0 ; i < n ; i++){
        for(let j = 0 ; j < m ; j++){
            if(!v[i][j] && grid[i][j] > k){
                cnt++;
                size = 1;
                const q = [];
                q.push([i, j]);
                v[i][j] = true;

                while(q.length !== 0){
                    const [x, y] = q.shift();

                    for(let d = 0 ; d < 4 ; d++){
                        const nx = x + dx[d]
                        const ny = y + dy[d]
                        if(nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
                        if(!v[nx][ny] && grid[nx][ny] > k){
                            q.push([nx, ny])
                            v[nx][ny] = true;
                            size++;
                        }
                    }
                }
            }
        }
    }
    return {size, cnt}
}

for(let k = 1 ; k < K + 1 ; k++){
    const {size, cnt} = bfs(k);
    if(cnt > ansCnt){
        ansCnt = cnt
        ans = k 
    }
}
console.log(ans, ansCnt)