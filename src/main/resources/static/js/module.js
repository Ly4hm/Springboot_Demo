// 弹窗函数
export function showWrongMessage(text) {
    const section = document.querySelector("section");
    const mes = document.querySelector("#message");
    const icon = document.querySelector("#icon");
    const message_title = document.querySelector("#message_title");
    message_title.textContent = "Wrong！"
    icon.setAttribute("class", "far fa-times-circle");
    mes.textContent = text;
    section.classList.add("active");
}

export function showRightMessage(text) {
    const section = document.querySelector("section");
    const mes = document.querySelector("#message");
    const icon = document.querySelector("#icon");
    const message_title = document.querySelector("#message_title");
    // 修改图标颜色
    const iconElement = document.getElementById('icon');
    iconElement.style.color = "#2c5fee";
    // 修改弹窗内容
    section.classList.add("active");
    mes.textContent = text;
    icon.setAttribute("class", "fa-regular fa-circle-check");
    message_title.textContent = "Right！";
}

// 异或加密函数
export function xorEncrypt(text, key) {
    var result = '';
    for (var i = 0; i < text.length; i++) {
        result += String.fromCharCode(text.charCodeAt(i) ^ key.charCodeAt(i % key.length));
    }
    return result;
}

// 异或解密函数
export function xorDecrypt(encryptedText, key) {
    return xorEncrypt(encryptedText, key); // 异或解密和加密操作相同
}


/*
*向服务器发送请求的通用函数
* 接受一个路由字符串和一个数据对象
* 另外接受一个可选参数 redirect， 设置后将在操作成功时跳转至该页面
* 将数据对象自动解构为 JSON 的形式进行传输
* 操作结果只弹窗，不重定向
 */
export function requestByRoute(route, value, redirect, delay) {
    fetch(route, {
        method: 'POST', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify(value)
    })
        .then(response => {
            response.json().then(data => {
                if (data["code"]) {
                    showRightMessage(data["message"]);
                    // 如果设置了 redirect , 则直接跳转
                    if (redirect && delay) {
                        setTimeout(() => {
                            window.location.href = redirect;
                        }, delay)
                    } else if (redirect) {
                        setTimeout(() => {
                            window.location.href = redirect;
                        }, 2000);
                    }
                } else {
                    // 密码错误
                    showWrongMessage(data["message"]);
                }
            })
        })
        .catch(error => {
            // 处理请求错误
            console.log('请求错误:', error);
            showWrongMessage("出现了一些小问题");
        });
}
