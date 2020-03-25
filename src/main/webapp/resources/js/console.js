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
    $(".logout-button").on("click", requestLogout);
    $("#dd").on('click', openUserMenu);
}

function openUserMenu(event){
    $(this).toggleClass('active');
    event.stopPropagation();
}

function requestLogout() {
    $.ajax({
        type: "GET",
        url: "logout",
        contentType: 'application/json',
        success: function(data) {
            document.location.href = "login";
        }
    });
}

function pressMenuTab() {
    $(".active").removeClass("active");
    $(this).addClass("active");

    var tabId = $(this).attr("id");
    switch (tabId) {
        case "dashboard-tab": currentPage = 2; break;
        case "db-tab": currentPage = 1; break;
        case "logic-tab": currentPage = 3; break;
    }
    switchContentPage();
}

function switchContentPage() {
    switch(currentPage) {
        case 1 : requestLogsPage(); break;
        case 2 : requestDashboardPage(); break;
        case 3 : requestLogicPage();break;
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

function requestLogicPage() {
    $.ajax({
        type: "GET",
        url: "console/logic",
        contentType: 'application/json',
        success: function(data) {
            dashboardPageStop();
            fillContentPage(data);
            logicPageInit();
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
