const fs = require("fs");
const input = fs.readFileSync(0).toString().trim().split('\n');

const [n, m] = input[0].split(' ').map(Number);
const edges = [];
for (let i = 1; i <= m; i++) {
    edges.push(input[i].split(' ').map(Number));
}

const map = new Array(n + 1).fill(null).map(() => [])

edges.forEach((edge) => {
    const a = edge[0];
    const b = edge[1];
    // console.log(a, b)
    map[a].push(b);
    map[b].push(a);
})
const v = new Array(n + 1).fill(false);
// console.log(map)
let ans = 0;
function dfs(p, cnt){
    // console.log(p)
    ans++;
    map[p].forEach((node) => {
        if(!v[node]){
            v[node] = true;
            dfs(node, cnt + 1);
        }
    })
}

v[1] = true;
dfs(1)
console.log(ans - 1)
