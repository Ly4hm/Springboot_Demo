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
    message_title.textContent = "Right！"
    icon.setAttribute("class","fa-regular fa-circle-check");
    mes.textContent = text;
    section.classList.add("active");
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

// 数据传输加密
export async function calculateMD5(inputText) {
    // 使用 Web Crypto API 计算 MD5 值
    const encoder = new TextEncoder();
    const data = encoder.encode(inputText);
    const hashBuffer = await crypto.subtle.digest('MD5', data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    return hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
}

export function md5(text) {
    let passwd;
    calculateMD5(text).then(hash => {
        passwd = hash;
    })
    return passwd;
}