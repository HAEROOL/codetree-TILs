const fs = require("fs");
const input = fs.readFileSync(0).toString().trim().split('\n');

// 변수 선언 및 입력
const [n, m] = input[0].split(' ').map(Number);
const grid = input.slice(1, 1 + n).map(line => line.split(' ').map(Number));

const visited = Array(n).fill(false).map(() => Array(m).fill(false));

let zoneNum = 0;

// visited 배열을 초기화합니다.
function initializeVisited() {
    for (let i = 0; i < n; i++) {
        for (let j = 0; j < m; j++) {
            visited[i][j] = false;
        }
    }
};

// 주어진 위치가 격자를 벗어나는지 여부를 반환합니다.
function inRange(x, y) {
    return 0 <= x && x < n && 0 <= y && y < m;
};

// 주어진 위치로 이동할 수 있는지 여부를 확인합니다.
function canGo(x, y, k) {
    if (!inRange(x, y)) {
        return false;
    }

    if (visited[x][y] || grid[x][y] <= k) {
        return false;
    }

    return true;
};

function dfs(x, y, k) {
    // 0: 오른쪽, 1: 아래쪽, 2: 왼쪽, 3: 위쪽
    const dx = [0, 1, 0, -1], dy = [1, 0, -1, 0];
    
    // 네 방향에 각각에 대하여 DFS 탐색을 합니다.
    for (let dir = 0; dir < 4; dir++) {
        const newX = x + dx[dir], newY = y + dy[dir];

        if (canGo(newX, newY, k)) {
            visited[newX][newY] = true;
            dfs(newX, newY, k);
        }
    }
};

function getZoneNum(k) {
    // 새로운 탐색을 시작한다는 의미로 zoneNum를 0으로 갱신하고 
    // visited 배열을 초기화합니다.
    zoneNum = 0;
    initializeVisited();

    // 격자의 각 위치에 대하여 탐색을 시작할 수 있는 경우
    // 해당 위치로부터 시작한 DFS 탐색을 수행합니다.
    for (let i = 0; i < n; i++) {
        for (let j = 0; j < m; j++) {
            if (canGo(i, j, k)) {
                // 해당 위치를 탐색할 수 있는 경우 visited 배열을 갱신하고
                // 안전 영역을 하나 추가합니다.
                visited[i][j] = true;
                zoneNum += 1;

                dfs(i, j, k);
            }
        }
    }
};

// 가능한 안전 영역의 최솟값이 0이므로 다음과 같이 초기화 할 수 있습니다.
let maxZoneNum = -1;
let answerK = 0;
const maxHeight = 100;

// 각 가능한 비의 높이에 대하여 안전 영역의 수를 탐색합니다.
for (let k = 1; k <= maxHeight; k++) {
    getZoneNum(k);

    // 기존의 최대 영역의 수보다 클 경우 이를 갱신하고 인덱스를 저장합니다.
    if (zoneNum > maxZoneNum) {
        maxZoneNum = zoneNum;
        answerK = k;
    }
}

console.log(answerK, maxZoneNum);
