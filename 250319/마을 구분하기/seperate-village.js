const fs = require("fs");
const input = fs.readFileSync(0).toString().trim().split('\n');

const n = Number(input[0]);
const grid = input.slice(1, n + 1).map(line => line.split(' ').map(Number));

const v = new Array(n).fill(null).map(() => new Array(n).fill(false));
let ans = 0;
const peopleCnt = [];
const dx = [1, 0, -1, 0];
const dy = [0, 1, 0, -1];
function bfs(x, y){
    const q = [];
    let size = 1;
    q.push([x, y]);
    v[x][y] = true;

    while(q.length !== 0){
        const [x, y] = q.shift();
        for(let i = 0 ; i < 4 ; i++){
            const nx = x + dx[i];
            const ny = y + dy[i];
            if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
            if(!v[nx][ny] && grid[nx][ny] === 1){
                q.push([nx, ny]);
                v[nx][ny] = true;
                size++;
            }
        }
    }
    return size;
}
for(let i = 0 ; i < n ; i++){
    for(let j = 0 ; j < n ; j++){
        if(!v[i][j] && grid[i][j] === 1){
            const size = bfs(i, j);
            ans++;
            peopleCnt.push(size);
        }
    }
}

peopleCnt.sort((a, b) => a - b);
console.log(ans);
peopleCnt.forEach((cnt) => console.log(cnt))