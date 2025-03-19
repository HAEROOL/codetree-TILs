const fs = require("fs");
const input = fs.readFileSync(0).toString().trim().split('\n');
const n = Number(input[0]);
const segments = input.slice(1, 1 + n).map(line => line.split(' ').map(Number));

let ans = Number.MIN_VALUE
function dfs(depth, cnt, sel, v){
    if(depth == n){
        ans = Math.max(ans, cnt);
        return;
    }
    for(let i = 0 ; i < n ; i++){
        if(!v[i]){
            let isPossible = true;
            const st = segments[i][0];
            const end = segments[i][1];
            for(let j = st ; j < end ; j++){
                if(sel[j]){
                    isPossible = false;
                    break;
                }
            }
            if(isPossible){
                const tmp = [...sel];
                for(let j = st ; j < end ; j++){
                    tmp[j] = true;
                }
                v[i] = true;
                dfs(depth + 1, cnt + 1, tmp, v);
                v[i] = false;
            }

        }
        dfs(depth + 1, cnt, sel, v);
    }
}

for(let i = 0 ; i < n ; i++){
    const v = new Array(n).fill(false)
    const sel = new Array(1000).fill(false)
    const st = segments[i][0];
    const end = segments[i][1];

    for(let j = st ; j < end + 1 ; j++){
        sel[j] = true;
    }
    v[i] = true;
    dfs(i, 1, sel, v)
}

console.log(ans)