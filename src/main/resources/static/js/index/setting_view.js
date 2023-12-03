import {showWrongMessage, showRightMessage, requestByRoute} from "../module.js";

// 获取按钮和对应的内容元素
const selfInfoButton = document.querySelector('.self_info_setting');
const safeSettingButton = document.querySelector('#self_info_btn');
const selfInfoContent = document.querySelector('.self_info_content');
const safeSettingContent = document.querySelector('#safe_setting_btn');

// 初始状态，将内容元素隐藏
selfInfoContent.classList.add('hidden');
safeSettingContent.classList.add('hidden');

// 添加按钮点击事件监听器
selfInfoButton.addEventListener('click', function() {
    // 切换内容元素的隐藏状态
    selfInfoContent.classList.remove('hidden');
});

safeSettingButton.addEventListener('click', function() {
    // 切换内容元素的隐藏状态
    safeSettingContent.classList.remove('hidden');
});