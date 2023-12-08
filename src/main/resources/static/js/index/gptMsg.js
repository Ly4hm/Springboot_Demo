// 获取智能分析得到的内容
fetch("/api/gpt", {
    method: 'POST', headers: {
        'Content-Type': 'application/json'
    }, body: 1
})
    .then(response => {
        response.json().then(data => {
            const gptMsg = document.querySelector("#gpt_msg");
            if (data["code"]) {
                gptMsg.textContent = data["message"];
            } else {
                // 密码错误
                gptMsg.textContent = data["message"];
            }
            console.log(data["message"]);
        })
    })
    .catch(error => {
        // 处理请求错误
        console.log('请求错误:', error);
        gptMsg.textContent = "出现了些小问题";
    });