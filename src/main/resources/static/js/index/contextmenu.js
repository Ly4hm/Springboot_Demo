import {showRightMessage, showWrongMessage} from "../module.js";

const areaEl = document.querySelectorAll('.handle-btn')
const mask = document.querySelector('.contextmenu-mask')
const contentEl = document.querySelector('.contextmenu-content')

/**
 *
 * @param {number} x 将要设置的菜单的左上角坐标 x
 * @param {number} y 左上角 y
 * @param {number} w 菜单的宽度
 * @param {number} h 菜单的高度
 * @returns {{x: number, y: number}} 调整后的坐标
 */
const adjustPos = (x, y, w, h) => {
    const PADDING_RIGHT = 6  // 右边留点空位，防止直接贴边了，不好看
    const PADDING_BOTTOM = 6  // 底部也留点空位
    const vw = document.documentElement.clientWidth
    const vh = document.documentElement.clientHeight
    if (x + w > vw - PADDING_RIGHT) x -= w
    if (y + h > vh - PADDING_BOTTOM) y -= h
    return {x, y}
}

const showContextMenu = (x, y) => {
    contentEl.style.left = x + 'px'
    contentEl.style.top = y + 'px'
    mask.style.display = ''
}

const hideContextMenu = () => {
    mask.style.display = 'none'
    contentEl.style.top = '99999px'
    contentEl.style.left = '99999px'
}

var clicked_btn = null;

const onContextMenu = e => {
    clicked_btn = e.target;  // 保存按下的按钮元素
    e.preventDefault()
    const rect = contentEl.getBoundingClientRect()
    // console.log(rect)
    const {x, y} = adjustPos(e.clientX, e.clientY, rect.width, rect.height)
    showContextMenu(x, y)
}

// 按钮点击处理
areaEl.forEach(handleBtn => {
    handleBtn.addEventListener('click', onContextMenu);
})

// 点击蒙版，隐藏
mask.addEventListener('mousedown', () => {
    hideContextMenu()
}, false)


/**
 * 修改弹窗的内容并显示
 * @param Selector 要弹出的窗口的 Selector
 * @param commitFunc
 */
function showEditWindow(Selector, commitFunc) {
    const section = document.querySelector("section");

    // 将窗口内容替换为编辑界面
    var modalBox = document.querySelector('.modal-box');
    // 存储原始内容
    var originalContent = modalBox.innerHTML;
    // 替换内容
    var newParagraph = document.querySelector(Selector).innerHTML;
    modalBox.innerHTML = '';
    modalBox.innerHTML = newParagraph;
    section.classList.add("active"); // 显示窗口


    // TODO: 增加取消按钮 (砍)
    // 还原内容
    document.querySelector(".modal-box button").addEventListener("click", (e) => {
        // 在这里提交请求操作
        var promise = commitFunc();

        // 关闭弹出的窗口并重新监听弹窗提示的关闭按钮
        document.querySelector("section").classList.remove("active");
        setTimeout(() => {
            modalBox.innerHTML = originalContent;
            // 重新监听关闭按钮
            const close_btn = modalBox.querySelector(".close-btn")
            close_btn.addEventListener("click", function () {
                section.classList.remove("active");
            })

            // 根据Promise的结果来弹窗提示信息
            promise
                .then(data => {
                    if (!!data["code"]) {
                        showRightMessage("操作成功");
                    } else {
                        showWrongMessage(data["message"]);
                    }
                })
                .catch(error => {
                    console.log("发生错误:", error);
                    showWrongMessage("出现了一些小问题");
                });
        }, 250); // 在提交后0.25s 后再关闭弹窗

    })
}

function baseDiv() {
    if (clicked_btn.parentElement.tagName == "svg") {
        return clicked_btn.parentElement.parentElement
    } else {
        return clicked_btn.parentElement
    }
}


// 菜单栏绑定相关函数
function editName(id) {
    showEditWindow("#editNameWindow", () => {
        // 构造数据
        var postData = {
            Fid: id,
            newName: document.querySelector(".modal-box label input").value
        }

        // 向服务端发起更名请求
        return new Promise((resolve, reject) => {
            fetch("/api/editName", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(postData)
            })
                .then(response => {
                    response.json().then(data => {
                        // 更改前端显示
                        baseDiv().querySelector(".furniture-title").textContent = postData.newName;

                        // 使用 resolve 将布尔值传递出去
                        resolve(data);
                    })
                })
                .catch(error => {
                    // 处理请求错误
                    console.log('请求错误:', error);
                    showWrongMessage("出现了一些小问题");
                    reject(error); // 使用 reject 将错误信息传递出去
                });
        });
    });
}

function switchState(id) {
    // 构造数据
    var stateEle = baseDiv().querySelectorAll(".detail p")[0].querySelector("span");
    var postData = {
        Fid: id,
        currState: parseInt(stateEle.getAttribute("data-state"))
    }

    // 向服务端发起更名请求
    fetch("/api/switchState", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(postData)
    })
        .then(response => {
            response.json().then(data => {
                // 弹窗显示操作结果
                if (!!data["code"]) {
                    // 更改前端显示
                    if (postData.currState == 0) {
                        stateEle.textContent = "待机";
                        stateEle.setAttribute("style", 'color: #000000;');
                        stateEle.setAttribute("data-state", '1');
                    } else {
                        stateEle.textContent = "运行中";
                        stateEle.setAttribute("style", 'color: #43a047;');
                        stateEle.setAttribute("data-state", '0');
                    }

                    showRightMessage("操作成功");
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

function editRule(id) {
    showEditWindow("#editRuleWindow", () => {
        console.log("ed");
    })

}

function removeFurniture(id) {
    console.log("执行删除设备逻辑");
}

function moveFurniture(id) {
    console.log("执行移动设备逻辑");
}


// 点击菜单选项
contentEl.addEventListener('click', (e) => {
    // 排除点击边框的情况
    if (e.target.getAttribute("class") != "contextmenu-list" &&
        e.target.getAttribute("class") != "contextmenu-content") {
        // 获取 id，针对点击 svg 还是 path 标签进行差异化处理保证能求得 id
        if (clicked_btn.parentElement.tagName == "svg") {
            var id = clicked_btn.parentElement.parentElement.querySelector(".furniture-id").textContent
        } else {
            // console.log(clicked_btn.parentElement.querySelector(".furniture-id"))
            var id = clicked_btn.parentElement.querySelector(".furniture-id").textContent
        }

        console.log(id)

        // 执行菜单项对应命令
        // console.log(e.target.id)
        eval(e.target.id + "(" + id + ")");

        // 隐藏菜单栏
        hideContextMenu()
    }
}, false)