const stateCount = document.querySelectorAll(".data");
const stateFill = document.querySelectorAll(".range div");

fetch("/api/getState", {
    method: 'GET'
})
    .then(response => {
        response.json().then(data => {
            var sum = data[0] + data[1] + data[2];
            for (var i = 0; i < 3; i++) {
                stateCount[i].querySelector("p").textContent = data[i] + "台";
                stateFill[i].style.width = (data[i] / sum) * 100 + "%";
            }
        })
    })
    .catch(error => {
        // 处理请求错误
        console.log('请求错误:', error);
        gptMsg.textContent = "出现了些小问题";
    });