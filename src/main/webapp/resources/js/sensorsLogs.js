$(document).ready(function() {
    requestSensorsLogs();
});

function requestSensorsLogs() {
    $.ajax({
        type: "GET",
        url: "/intler_iot_war_exploded/console/get-sensors-logs",
        contentType: 'application/json',
        success: function(data) {
            console.log(data);
        }
    });
}