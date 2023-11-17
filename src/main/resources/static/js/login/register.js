import {showWrongMessage, showRightMessage} from "./module.js"

const register_btn = document.querySelector('#register_btn');
const register_uname = document.querySelector("#register_uname");
const register_passwd = document.querySelector("#register_passwd");
const register_con_passwd = document.querySelector("#register_con_passwd");


register_btn.addEventListener("click", function (event) {
    event.preventDefault(); // 阻止默认表单的提交
    const uname = register_uname.value;
    const passwd = register_passwd.value;
    const con_passwd = register_con_passwd.value;

    if (passwd === con_passwd) {
        // 用于发包的数据
        const data = {
            username: uname, password: md5(passwd)  // 传输过程加密,md5 函数在html中引入
        };
        console.log(JSON.stringify(data));

        //TOdo: 表单验证

        if (uname !== "" && passwd !== "") {
            // 向服务端验证注册
            fetch("/api/checkRegister", {
                method: 'POST', headers: {
                    'Content-Type': 'application/json'
                }, body: JSON.stringify(data)
            })
                // 处理服务端返回值
                .then(response => {
                    response.json().then(data => {
                        if (data["code"]) {
                            showRightMessage("注册成功，即将跳转到登录界面")
                            setTimeout(() => {
                                window.location.href = "login"
                            }, 2000);
                        } else {
                            showWrongMessage(data["message"]);
                        }
                    })
                });
        } else {
            showWrongMessage("什么都不输入可没办法注册哦")
        }

    } else {
        showWrongMessage("两次密码不一致哦~")
    }
});