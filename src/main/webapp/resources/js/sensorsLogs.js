$(document).ready(function() {
    requestSensorsLogs();
});

function requestSensorsLogs() {
    $.ajax({
        type: "GET",
        url: "/intler_iot_war_exploded/console/get-sensors-logs",
        contentType: 'application/json',
        success: function(data) {
            for (var key in data) {
                var sensor = data[key];
                var sensLogs = sensor["sensorsLogs"];
                addSensorTable(sensor["sensorName"], sensLogs)
            }
        }
    });
}

function addSensorTable(sensorName, sensorLogs) {
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
        "                            <input type=\"button\" class=\"pag-button\" value=\"&lt&lt\">\n" +
        "                            <input type=\"button\" class=\"pag-button\" value=\"&lt\">\n" +
        "                            <span class=\"pag-input-page-num\">1</span>\n" +
        "                            <input type=\"button\" class=\"pag-button\" value=\"&gt\">\n" +
        "                            <input type=\"button\" class=\"pag-button\" value=\"&gt&gt\">\n" +
        "                        </td>\n" +
        "                    </tr>\n" +
        "                    </tbody>\n" +
        "                </table>";
    $(".tables-wrap").append(tableHtml);

    for (var sensorLog in sensorLogs) {
        var addedTable = $("#" + sensorName);
        addedTable.find(".pagination").before("<tr><td>" + parseTimestamp(sensorLogs[sensorLog]["key"]) + "</td> <td>" +
            sensorLogs[sensorLog]["value"] + "</td></tr>");
    }
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