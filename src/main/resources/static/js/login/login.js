import {showWrongMessage, xorDecrypt, xorEncrypt, requestByRoute} from "../module.js";

// 获取login表单元素
const login_form = document.querySelector('#login_btn');
const usernameInput = document.querySelector('#login_uname');
const passwordInput = document.querySelector('#login_passwd');

// 通过 LocalStorage 来实现记住密码
function saveCredentials() {
    // 获取checkbox的状态
    var checkBox = document.getElementById("check");
    var isChecked = checkBox.checked;

    // 获取用户名和密码
    var username = document.getElementById("login_uname").value;
    var password = document.getElementById("login_passwd").value;

    // 根据checkbox的状态决定是否存入localstore
    if (isChecked) {
        localStorage.setItem("username", username);
        localStorage.setItem("password", xorEncrypt(password, 'fakepasswd'));
    } else {
        // 如果checkbox未被选中，则从localstore中移除保存的用户名和密码
        localStorage.removeItem("username");
        localStorage.removeItem("password");
    }
}

// 监听 login 提交事件
login_form.addEventListener('click', function (event) {
    event.preventDefault(); // 阻止表单默认提交行为

    // 获取输入的用户名和密码
    const username = usernameInput.value;
    const password = passwordInput.value;

    // 创建一个对象来存储要发送的数据
    const data = {
        username: username, password: md5(password)
    };

    // 决定是否保存密码
    saveCredentials();

    // 表单验证
    if (username !== "" || password !== "") {
        // 发送POST请求到login路由
        requestByRoute("/api/checkLogin", data, ()=>{},"index", 1000)
    } else {
        showWrongMessage("怎么什么都不填呢？")
    }
});

// remember password 功能实现
// 从 LocalStorage 中提取出存储的账户
const userName = localStorage.getItem('username') ? localStorage.getItem('username') : '';
const enPassword = localStorage.getItem('password') ? localStorage.getItem('password') : '';
if (enPassword) {
    const uname = document.getElementById("login_uname");
    const passwd = document.getElementById("login_passwd");
    // 设置值
    var checkBox = document.getElementById("check");
    checkBox.checked = true;

    uname.value = userName;
    passwd.value = xorDecrypt(enPassword, "fakepasswd")
}