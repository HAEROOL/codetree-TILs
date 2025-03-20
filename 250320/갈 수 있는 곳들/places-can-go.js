const fs = require("fs");
const input = fs.readFileSync(0).toString().trim().split('\n');

const [n, k] = input[0].split(' ').map(Number);
const grid = input.slice(1, n + 1).map(line => line.split(' ').map(Number));
const startPoints = input.slice(n + 1).map(line => line.split(' ').map(Number));
const dx = [1, 0, -1, 0]
const dy = [0, 1, 0, -1]

// Please Write your code here.
const v = new Array(n).fill(null).map(() => new Array(n).fill(false))
const bfs = (i, j) => {
    let cnt = 0;
    const q = [];
    v[i][j] = true;
    q.push([i, j])

    while(q.length !== 0){
        const [x, y] = q.pop(0)
        cnt++;
        for(let i = 0 ; i < 4 ; i++){
            const nx = x + dx[i]
            const ny = y + dy[i]
            if(0 <= nx && nx < n && 0 <= ny && ny < n && grid[nx][ny] === 0 && !v[nx][ny]){
                v[nx][ny] = true;
                q.push([nx, ny])
            } 
        }
    }
    return cnt;
}
let ans = 0
startPoints.forEach(coord => {
    const [x, y] = coord;
    if(!v[x - 1][y - 1]){
        const res = bfs(x - 1, y - 1)
        ans += res
    }
})

console.log(ans)