const fs = require("fs");
const input = fs.readFileSync(0).toString().trim().split('\n');
const n = Number(input[0]);
const segments = input.slice(1, 1 + n).map(line => line.split(' ').map(Number));

let ans = 1;

function cal(sel){
    const v = new Array(1000).fill(false);
    let cnt = 0;
    for(let i = 0 ; i < sel.length ; i++){
        if(sel[i]){
            let isPossible = true;
            const st = segments[i][0];
            const end = segments[i][1];
            for(let j = st ; j < end + 1 ; j++){
                if(v[j]){
                    isPossible = false;
                    break;
                }
            }
            if(isPossible){
                for(let j = st ; j < end + 1 ; j++){
                    v[j] = true;
                }
                cnt++;
            }
        }
    }
    ans = Math.max(cnt, ans);
}

function subset(k, sel){
    if(k == sel.length){
        // console.log(sel)
        cal(sel);
        return;
    }
    sel[k] = true;
    subset(k + 1, sel);

    sel[k] = false;
    subset(k + 1, sel)

}

subset(0, new Array(n).fill(false))

console.log(ans)