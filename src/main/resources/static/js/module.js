// 弹窗函数
export function showWrongMessage(text) {
    const section = document.querySelector("section");
    const mes = document.querySelector("#message");
    const icon = document.querySelector("#icon");
    const message_title = document.querySelector("#message_title");
    message_title.textContent = "Wrong！"
    icon.setAttribute("class","far fa-times-circle");
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
    icon.setAttribute("class","fa-regular fa-circle-check");
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
