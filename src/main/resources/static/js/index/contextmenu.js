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


// 菜单栏绑定相关函数
function editName() {
    console.log("执行修改名称逻辑");
}

function switchState() {
    console.log("执行切换状态逻辑");
}

function editRule() {
    console.log("执行规则修改逻辑");
}

function removeFurniture() {
    console.log("执行删除设备逻辑");
}

function moveFurniture() {
    console.log("执行移动设备逻辑");
}


// 点击菜单选项
contentEl.addEventListener('click', (e) => {
    // 排除点击边框的情况
    if (e.target.getAttribute("class") != "contextmenu-list" &&
        e.target.getAttribute("class") != "contextmenu-content") {
        // 获取 id，针对点击 svg 还是 path 标签进行差异化处理保证能求得 id
        if (clicked_btn.parentElement.tagName == "PATH") {
            var id = clicked_btn.parentElement.parentElement.querySelector(".furniture-id").textContent
        } else {
            // console.log(clicked_btn.parentElement.querySelector(".furniture-id"))
            var id = clicked_btn.parentElement.querySelector(".furniture-id").textContent
            // TODO: 修复bug
        }

        console.log(id)

        // 执行菜单项对应命令
        // console.log(e.target.id)
        eval(e.target.id + "()");

        // 隐藏菜单栏
        hideContextMenu()
    }
}, false)