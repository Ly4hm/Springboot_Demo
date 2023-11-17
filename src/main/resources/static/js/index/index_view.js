import {showWrongMessage, showRightMessage} from "../module.js";

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
                let element_id = a_lable.textContent.toLowerCase() + "-content";
                console.log(element_id);
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
                let element_id = a_lable.textContent.toLowerCase() + "-content";
                console.log(element_id);
                let content = document.getElementById(element_id);
                content.classList.remove("hidden");
            } catch (e) {
                let a = e;
            }
        });
    });
});


//// 弹窗逻辑
// 获取所有包含用户信息的容器
var userItems = document.querySelectorAll('.user-item');
// 遍历每个容器
userItems.forEach(function(userItem) {
    // 获取当前容器下的 set-btn 按钮
    var setBtn = userItem.querySelector('.set-btn');
    var rmBtn = userItem.querySelector('.rm-btn');

    // 添加 set-btn 点击事件监听器
    setBtn.addEventListener('click', function() {
        // 查询当前容器下的第一个 list-item 的内容
        var firstListItemContent = userItem.querySelector('.list-item').textContent;

        // 打印获取到的内容
        // Todo: 发送请求的代码
        // console.log('点击的 set-btn 对应的第一个 list-item 内容:', firstListItemContent);

    });


    // 添加 rm-btn 点击事件监听器
    rmBtn.addEventListener('click', function() {
        // 查询当前容器下的第一个 list-item 的内容
        var firstListItemContent = userItem.querySelector('.list-item').textContent;

        // 打印获取到的内容
        // Todo: 发送请求的代码
        console.log('点击的 rm-btn 对应的第一个 list-item 内容:', firstListItemContent);
    });
});
