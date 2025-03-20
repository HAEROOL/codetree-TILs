const fs = require("fs");
const input = fs.readFileSync(0).toString().trim().split('\n');

const n = Number(input[0]);
const grid = input.slice(1, n + 1).map(row => row.split(" ").map(Number));

let ans = 0;
let mxSize = 0;
const dx = [1, 0, -1, 0]
const dy = [0, 1, 0, -1]
const v = new Array(n).fill(null).map(() => new Array(n).fill(false));
const dfs = (x, y, base) => {
    cnt++
    // console.log(x, y)
    for(let i = 0 ; i < 4 ; i++){
        const nx = x + dx[i]
        const ny = y + dy[i]
        if(0 <= nx && nx < n && 0 <= ny && ny < n && grid[nx][ny] === base && !v[nx][ny]){
            v[nx][ny] = true;
            dfs(nx, ny, base)
        }
    }
}
let cnt = 0;
for(let i = 0 ; i < n ; i++){
    for(let j = 0 ; j < n ; j++){
        if(!v[i][j]){
            cnt = 0;
            v[i][j] = true
            dfs(i, j, grid[i][j])
            // console.log(cnt)
            mxSize = Math.max(cnt, mxSize)
            if(cnt >= 4){
                ans++   
            }
        }
    }
}

console.log(ans, mxSize)