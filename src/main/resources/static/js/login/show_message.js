const section = document.querySelector("section"),
    overlay = document.querySelector(".overlay"),
    closeBtn = document.querySelector(".blue-btn");

// 关闭弹出的窗口的逻辑
overlay.addEventListener("click", () =>
    section.classList.remove("active")
);

closeBtn.addEventListener("click", () =>
    section.classList.remove("active")
);