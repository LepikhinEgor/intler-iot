function requestSensorsLogs() {
    $.ajax({
        type: "GET",
        url: "/intler_iot/console/get-sensors-logs",
        contentType: 'application/json',
        success: function(data) {
            console.log(data);
        }
    });
}