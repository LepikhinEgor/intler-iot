const LOGS_PAGE = 1;
const DASHBOARD_PAGE = 2;

var currentPage = 0;

$(document).ready(function() {
    currentPage = DASHBOARD_PAGE;
    addEventHandlers();
    switchContentPage();
});

function addEventHandlers() {
    $(".aside-menu").find("li").on("click", pressMenuTab);
}

function pressMenuTab() {
    $(".active").removeClass("active");
    $(this).addClass("active");

    var tabId = $(this).attr("id");
    switch (tabId) {
        case "dashboard-tab": currentPage = 2; break;
        case "db-tab": currentPage = 1; break;
    }
    switchContentPage();
}

function switchContentPage() {
    switch(currentPage) {
        case 1 : requestLogsPage(); break;
        case 2 : requestDashboardPage(); break;
    }
}

function requestLogsPage() {
    $.ajax({
        type: "GET",
        url: "console/logs",
        contentType: 'application/json',
        success: function(data) {
            fillContentPage(data);
            sensorsLogPageStart();
        }
    });
}

function fillContentPage(htmlPage) {
    $(".content-panel").html(htmlPage);
}

function requestDashboardPage() {
    $.ajax({
        type: "GET",
        url: "console/dashboard",
        contentType: 'application/json',
        success: function(data) {
            fillContentPage(data);
            dashboardPageStart();
        }
    });
}
