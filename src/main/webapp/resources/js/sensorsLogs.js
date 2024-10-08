const LAST_PAGE_NUM = -1;

function sensorsLogPageStart() {
    requestSensorsLogs();
}

function refreshEmpEventHandlers() {
    $('.first-pag-button').off('click');
    $('.dec-pag-button').off('click');
    $('.inc-pag-button').off('click');
    $('.last-pag-button').off('click');

    $('.first-pag-button').on('click', toFirstPage);
    $('.dec-pag-button').on('click', decSensorPage);
    $('.inc-pag-button').on('click', incSensorPage);
    $('.last-pag-button').on('click', toLastPage);

}

var sensorsInfo = [];

function requestSensorsLogs() {
    $.ajax({
        type: "GET",
        url: "console/get-sensors-logs",
        contentType: 'application/json',
        success: function(data) {
            for (var key in data) {
                var sensor = data[key];
                var sensLogs = sensor["sensorsLogs"];
                addSensorTable(sensor["sensorName"], sensLogs, sensor["currentPage"], sensor["pagesCount"]);
                var newSensorInfo = {
                    name: sensor["sensorName"],
                    currentPage: 0,
                    pagesCount: sensor["pagesCount"]
                }
                sensorsInfo.push(newSensorInfo);
            }
            refreshEmpEventHandlers();
        }
    });
}

function addSensorTable(sensorName, sensorLogs, curPage, maxPage) {
    var tableHtml = "<table class=\"table_blur\" id=\"" + sensorName + "\">\n" +
        "                    <thead>\n" +
        "                        <caption class=\"table-sensor-name\">" + "Датчик " +"<a href=\"#\">"+ sensorName +"</a></caption>\n" +
        "                    <tr>\n" +
        "                        <th>Дата</th>\n" +
        "                        <th>Значение</th>\n" +
        "                    </tr>\n" +
        "                    </thead>\n" +
        "                    <tbody>\n" +
        "                    <tr class=\"pagination\" align='center'>\n" +
        "                        <td colspan=\"2\">\n" +
        "                            <input type=\"button\" class=\"pag-button first-pag-button\" value=\"&lt&lt\">\n" +
        "                            <input type=\"button\" class=\"pag-button dec-pag-button\" value=\"&lt\">\n" +
        "                            <span class=\"pag-input-page-num\"></span>\n" +
        "                            <input type=\"button\" class=\"pag-button inc-pag-button\" value=\"&gt\">\n" +
        "                            <input type=\"button\" class=\"pag-button last-pag-button\" value=\"&gt&gt\">\n" +
        "                        </td>\n" +
        "                    </tr>\n" +
        "                    </tbody>\n" +
        "                </table>";
    $(".tables-wrap").append(tableHtml);

    fillSensorTable(sensorName, sensorLogs, curPage, maxPage);
    $(".pag-input-page-num").html((curPage + 1) + " из " + maxPage);
}

function parseTimestamp(timestamp) {
    var a = new Date(timestamp);
    var year = a.getFullYear();
    var month = "0" + (a.getMonth() + 1);
    var date = a.getDate();
    var hour = "0" + a.getHours();
    var min = "0" +a.getMinutes();
    var sec ="0" + a.getSeconds();
    var time = date + '.' + month.substr(-2) + '.' + year + ' ' + hour.substr(-2) + ':' + min.substr(-2) + ':' + sec.substr(-2) ;
    return time;
}

function toFirstPage() {
    var parentTable = $(this).closest("table");
    var sensorName = parentTable.attr("id");
    var pageNum = 0;

    requestSensorPage(sensorName, pageNum);
}

function toLastPage() {
    var parentTable = $(this).closest("table");
    var sensorName = parentTable.attr("id");
    var pageNum = LAST_PAGE_NUM;

    requestSensorPage(sensorName, pageNum);
}

function incSensorPage() {
    var parentTable = $(this).closest("table");
    var sensorName = parentTable.attr("id");
    var pageNum = 0;

    for (var sensInfo in sensorsInfo) {
        if (sensorsInfo[sensInfo]["name"] === sensorName) {
            var curPage = sensorsInfo[sensInfo]["currentPage"];
            var maxPages = sensorsInfo[sensInfo]["pagesCount"];
            if (curPage + 1 < maxPages) {
                requestSensorPage(sensorName, curPage + 1);
            }
        }
    }
}

function decSensorPage() {
    var parentTable = $(this).closest("table");
    var sensorName = parentTable.attr("id");
    var pageNum = 0;

    for (var sensInfo in sensorsInfo) {
        if (sensorsInfo[sensInfo]["name"] === sensorName) {
            var curPage = sensorsInfo[sensInfo]["currentPage"];
            var maxPages = sensorsInfo[sensInfo]["pagesCount"];
            if (curPage > 0) {
                requestSensorPage(sensorName, curPage - 1);
            }
        }
    }
}

function requestSensorPage(sensorName, pageNum) {
    $.ajax({
        type: "GET",
        url: "console/get-sensor-logs-page?name="+sensorName + "&pageNum=" + pageNum,
        contentType: 'application/json',
        success: function(data) {
             console.log(data);
            var sensLogs = data["sensorsLogs"];
            fillSensorTable(data["sensorName"], sensLogs, data["currentPage"], data["pagesCount"]);

            for (var sensInfo in sensorsInfo) {
                if (sensorsInfo[sensInfo]["name"] === data["sensorName"]) {
                    sensorsInfo[sensInfo]["currentPage"] = data["currentPage"];
                    sensorsInfo[sensInfo]["pagesCount"] = data["pagesCount"];
                }
            }
        }
    });
}

function fillSensorTable(sensorName, sensorLogs, curPage, maxPage) {
    var addedTable = $("#" + sensorName);
    addedTable.find(".sensorVal").remove();
    for (var sensorLog in sensorLogs) {
        var sensLogNum = sensorLogs[sensorLog];
        for (var key in sensLogNum) {
            addedTable.find(".pagination").before("<tr class='sensorVal'><td>" + parseTimestamp(key)+ "</td> <td>" +
                sensLogNum[key]+ "</td></tr>");
        }
    }
    addedTable.find(".pag-input-page-num").html((curPage + 1) + " из " + maxPage);
}