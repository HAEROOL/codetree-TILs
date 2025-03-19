const fs = require("fs");
const input = fs.readFileSync(0).toString().trim().split('\n');

const [n, m] = input[0].split(' ').map(Number);
const grid = input.slice(1, n + 1).map(row => row.split(' ').map(Number));

const dx = [1, 0]
const dy = [0, 1]

const v = new Array(n).fill(null).map(() => new Array(m).fill(false));
let isExit = false;
function dfs(x, y){
    if(isExit) return;
    if(x === n - 1 && y === m - 1){
        isExit = true
        return;
    }
    for(let i = 0 ; i < 2 ; i++){
        const nx = x + dx[i];
        const ny = y + dy[i];
        if(nx >= n || nx < 0 || ny >= m || ny < 0) continue;
        if(!v[nx][ny] && grid[nx][ny] === 1){
            v[nx][ny] = true;
            dfs(nx, ny);
            // v[nx][ny] = false;
        }
    }
}

v[0][0] = true;
dfs(0, 0);
console.log(isExit?1 : 0)