import {showWrongMessage, showRightMessage, requestByRoute} from "../module.js";

// 初始隐藏
const page_content = document.querySelectorAll('.page-content');
page_content.forEach(item => {
    item.classList.add("hidden");
})
// 初始显示 dashboard 页面
document.querySelector("#dashboard-content").classList.remove("hidden");
// 隐藏弹窗
const section = document.querySelector("section");
section.classList.remove("active");


// 关闭弹窗相关逻辑
const close_btn = document.querySelector('.modal-box').querySelector(".close-btn");
close_btn.addEventListener("click", function () {
    section.classList.remove("active")
})


// sidebar 动效
document.addEventListener('DOMContentLoaded', function () {
    const menuToggle = document.querySelector('.menu-toggle');
    const sidebar = document.querySelector('.sidebar');
    const sidebarItems = document.querySelectorAll('.sidebar-menu li');

    menuToggle.addEventListener('click', function () {
        sidebar.classList.toggle('collapsed');
    });

    // 处理 sidebar 点击事件
    sidebarItems.forEach(item => {
        item.addEventListener('click', function () {
            // 移除所有的 active，隐藏所有内容
            sidebarItems.forEach(i => {
                i.classList.remove('active');

                // 反求出相关的 content
                let a_lable = i.querySelector("a");
                let element_id = a_lable.id + "-content";
                let content = document.getElementById(element_id);
                try {
                    content.classList.add("hidden");
                } catch (e) {
                    console.log(e);
                }

            });
            // 为被点击的元素添加 active
            item.classList.add('active');
            // 反求出对应的内容并显示
            try {
                let a_lable = item.querySelector("a");
                let element_id = a_lable.id + "-content";
                let content = document.getElementById(element_id);
                content.classList.remove("hidden");
            } catch (e) {
                let a = e;
            }
        });
    });
});


// 按钮弹窗并发送请求逻辑
// 获取所有包含用户信息的容器
var userItems = document.querySelectorAll('.user-item');
// 遍历每个容器
userItems.forEach(function(userItem) {
    // 获取当前容器下的 set-btn 按钮
    var setBtn = userItem.querySelector('.set-btn');
    var unsetBtn = userItem.querySelector('.unset-btn');
    var rmBtn = userItem.querySelector('.rm-btn');

    // 添加 set-btn 点击事件监听器
    setBtn.addEventListener('click', function() {
        // 查询当前容器下的第一个 list-item 的内容
        var firstListItemContent = userItem.querySelectorAll('.list-item')[0].textContent;

        // 向服务器提交请求
        const data = {"username" : firstListItemContent}
        requestByRoute("/api/setAdmin", data, () => {
            userItem.querySelectorAll(".list-item")[2].textContent = "Yes";
        });
    });

    // 添加 unset-btn 点击事件监听器
    unsetBtn.addEventListener('click', function() {
        // 查询当前容器下的第一个 list-item 的内容
        var firstListItemContent = userItem.querySelector('.list-item').textContent;

        // 向服务器提交请求
        const data = {"username" : firstListItemContent}
        requestByRoute("/api/unsetAdmin", data, () => {
            userItem.querySelectorAll(".list-item")[2].textContent = "No";
        });

    });


    // 添加 rm-btn 点击事件监听器
    rmBtn.addEventListener('click', function() {
        // 查询当前容器下的第一个 list-item 的内容
        var firstListItemContent = userItem.querySelector('.list-item').textContent;
        // 向服务器提交请求
        const data = {"username" : firstListItemContent}
        fetch("/api/rmUser", {
            method: 'POST', headers: {
                'Content-Type': 'application/json'
            }, body: JSON.stringify(data)
        })
            .then(response => {
                response.json().then(data => {
                    if (data["code"]) {
                        showRightMessage(data["message"]);
                        userItem.style.display = "none";
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
    });

});