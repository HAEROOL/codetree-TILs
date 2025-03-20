const fs = require("fs");
const input = fs.readFileSync(0).toString().trim().split('\n');

const [n, k] = input[0].split(" ").map(Number);
const grid = input.slice(1, 1 + n).map(line => line.split(" ").map(Number));
const dx = [1, 0, -1, 0]
const dy = [0, 1, 0, -1]
const v = new Array(n).fill(null).map(() => new Array(n).fill(-1))

const starts = []
const bfs = () => {
    const q = [...starts];
    starts.forEach(p => v[p[0]][p[1]] = 0)
    // console.log(q)
    while(q.length !== 0){
        const [x, y] = q.shift()
        for(let i = 0 ; i < 4 ; i++){
            const nx = x + dx[i]
            const ny = y + dy[i]
            if(0 <= nx && nx < n && 0 <= ny && ny < n && v[nx][ny] === -1 && grid[nx][ny] === 1){
                // console.log(nx, ny, x, y)
                v[nx][ny] = v[x][y] + 1
                q.push([nx, ny])
            }
        }
    }

}
for(let i = 0 ; i < n ; i++){
    for(let j = 0 ; j < n ; j++){
        if(grid[i][j] === 2){
            starts.push([i, j])
        }
    }
}

bfs();

for(let i = 0 ; i < n ; i++){
    for(let j = 0 ; j < n ; j++){
        if(v[i][j] === -1 && grid[i][j] === 1) v[i][j] = -2
    }
    console.log(v[i].join(" "))
}