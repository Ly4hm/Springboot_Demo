import {showWrongMessage} from "../module.js";

const forget_btn = document.querySelector("#foget_btn");

// 添加暂时的处理办法
forget_btn.addEventListener("click", () => {
    showWrongMessage("前面的路稍后再来探索吧~");
});