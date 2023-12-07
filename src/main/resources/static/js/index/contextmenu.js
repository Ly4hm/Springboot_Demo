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


const onContextMenu = e => {
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

// 点击菜单，隐藏
contentEl.addEventListener('click', (e) => {
    console.log('点击：', e.target.textContent)
    // 执行菜单项对应命令
    hideContextMenu()
}, false)