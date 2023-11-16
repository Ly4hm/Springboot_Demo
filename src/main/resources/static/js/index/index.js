// sidebar 动效
document.addEventListener('DOMContentLoaded', function () {
    const menuToggle = document.querySelector('.menu-toggle');
    const sidebar = document.querySelector('.sidebar');
    const sidebarItems = document.querySelectorAll('.sidebar-menu li');

    menuToggle.addEventListener('click', function () {
        sidebar.classList.toggle('collapsed');
    });

    // Handle sidebar item clicks
    sidebarItems.forEach(item => {
        item.addEventListener('click', function () {
            // Remove 'active' class from all items
            sidebarItems.forEach(i => i.classList.remove('active'));
            // Add 'active' class to the clicked item
            item.classList.add('active');
        });
    });
});

