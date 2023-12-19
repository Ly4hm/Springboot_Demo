const filterBtns = document.querySelectorAll(".FilterCheckBox");

// 初始全部勾选
filterBtns.forEach(function (Element) {
    Element.checked = true;
})

// 为其添加监听器
filterBtns.forEach(function (Element) {
    Element.addEventListener("change", (e) => {
        if (!Element.checked) {
            document.querySelectorAll(".furniture-item").forEach(function (Furniture) {
                var flag = Furniture.querySelector(".furniture-variety").textContent == Element.value;
                if (flag) {
                    Furniture.classList.add("hidden");
                }
            })
        } else {
            console.log("remove")
            document.querySelectorAll(".furniture-item").forEach(function (Furniture) {
                var flag = Furniture.querySelector(".furniture-variety").textContent == Element.value;
                if (flag) {
                    Furniture.classList.remove("hidden");
                }
            })
        }
    })
})