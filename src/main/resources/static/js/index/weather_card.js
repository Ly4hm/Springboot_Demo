const city = document.querySelector("#weather-province-city");
const temp = document.querySelector("#weatehr-temp");
const date = document.querySelector("#weather-date");

// 从接口获取数据
fetch("/api/weather_info", {
    method: 'GET'
})
    .then(response => {
        response.json().then(data => {
            data = data["lives"][0];
            city.textContent = data["province"] + "  " + data["city"];
            temp.textContent = data["temperature"] + "°";
        })
    })
    .catch(error => {
        // 处理请求错误
        console.log('请求错误:', error);
    });


// 获取本地时间
var currentDate = new Date();
// 定义月份和日期的名称数组
var monthNames = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
];
// 获取当前日期的月份和日期
var month = monthNames[currentDate.getMonth()];
var curr_date = currentDate.getDate();
// 格式化输出当前时间
var formattedDate = month + " " + curr_date;
date.textContent = formattedDate;