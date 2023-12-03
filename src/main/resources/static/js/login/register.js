import {showWrongMessage, showRightMessage, requestByRoute, validatePassword} from "../module.js"

const register_btn = document.querySelector('#register_btn');
const register_uname = document.querySelector("#register_uname");
const register_passwd = document.querySelector("#register_passwd");
const register_con_passwd = document.querySelector("#register_con_passwd");


register_btn.addEventListener("click", function (event) {
    event.preventDefault(); // 阻止默认表单的提交
    const uname = register_uname.value;
    const passwd = register_passwd.value;
    const con_passwd = register_con_passwd.value;

    // 校验弱口令
    if (validatePassword(passwd)) {
        // 校验重复确认密码
        if (passwd === con_passwd) {
            // 用于发包的数据
            const data = {
                username: uname, password: md5(passwd)  // 传输过程加密,md5 函数在html中引入
            };
            console.log(JSON.stringify(data));

            //TOdo: 表单验证

            if (uname !== "" && passwd !== "") {
                // 向服务端验证注册
                requestByRoute("/api/checkRegister", data, 'login');
            } else {
                showWrongMessage("什么都不输入可没办法注册哦")
            }

        } else {
            showWrongMessage("两次密码不一致哦~")
        }
    } else {
        showWrongMessage("密码太弱啦~")
    }
});