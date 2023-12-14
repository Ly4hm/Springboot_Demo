import {showWrongMessage, showRightMessage, requestByRoute} from "../module.js";

// 获取按钮和对应的内容元素
const selfInfoButton = document.querySelector('#self_info_setting_btn');
const safeSettingButton = document.querySelector('#safe_setting_btn');
const selfInfoContent = document.querySelector('.self_info_setting');
const safeSettingContent = document.querySelector('.safe_setting');

// 初始状态，将内容元素隐藏
safeSettingContent.classList.add('hidden');

// 子 sidebar 的显示逻辑
selfInfoButton.addEventListener('click', function () {
    // 切换内容元素的隐藏状态
    safeSettingContent.classList.add('hidden');
    selfInfoContent.classList.remove('hidden');
});

safeSettingButton.addEventListener('click', function () {
    // 切换内容元素的隐藏状态
    selfInfoContent.classList.add('hidden');
    safeSettingContent.classList.remove('hidden');
});


// 异步加载相关信息
const selfInfo = document.querySelectorAll(".self_info_item");
fetch("/api/getSelfInfo?username=" + selfInfo[0].textContent.replace("用户名：", ""), {
    method: 'GET'
})
    .then(response => {
        response.json().then(data => {
            // 设置是否是管理员
            if (data["access"] == 0) {
                selfInfo[1].textContent = "用户权限：超级管理员";
            } else if (data["access"] == 1) {
                selfInfo[1].textContent = "用户权限：管理员";
            } else {
                selfInfo[1].textContent = "用户权限：普通用户";
            }

            // 设置邮箱
            if (data["email"] == null) {
                data["email"] = "还没有设置捏，前往账户设置去设置邮箱";
            }
            selfInfo[2].textContent = "邮箱：" + data["email"];
        })
    })
    .catch(error => {
        // 处理请求错误
        console.log('SelfInfo 请求错误:', error);
    });


// 验证邮箱格式是否正确
function validateEmail(email) {
    // 定义邮箱的正则表达式模式
    var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    // 使用正则表达式进行匹配
    return pattern.test(email);
}

// 设置邮箱
const resetEmailBtn = document.querySelector("#reset-email");
const resetEmailInput = document.querySelector(".input-style");

resetEmailBtn.addEventListener("click", () => {
    // 验证格式
    if (!validateEmail(resetEmailInput.value)) {
        showWrongMessage("邮箱格式不正确");
    } else {
        // 构造请求包
        var postData = {
            username: document.querySelectorAll(".self_info_item")[0]
                .textContent.replace("用户名：", ""),
            email: resetEmailInput.value
        }
        console.log(postData)

        fetch("/api/resetEmail", {
            method: 'POST', headers: {
                'Content-Type': 'application/json'
            }, body: JSON.stringify(postData)
        })
            .then(response => {
                response.json().then(data => {
                    if (data["code"]) {
                        showRightMessage("重置成功");
                        // 处理前端显示
                        selfInfo[2].textContent = "邮箱：" + postData.email;

                    } else {
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
})
