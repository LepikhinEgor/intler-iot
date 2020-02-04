$(document).ready(function() {
    requestWidgets();
});

function requestWidgets() {
    $.ajax({
        type: "GET",
        url: "/intler_iot_war_exploded/console/dashboard/get-widgets",
        contentType: 'application/json',
        success: function(data) {
            console.log(data);
        }
        //     for (var key in data) {
        //         var sensor = data[key];
        //         var sensLogs = sensor["sensorsLogs"];
        //         addSensorTable(sensor["sensorName"], sensLogs, sensor["currentPage"], sensor["pagesCount"]);
        //         var newSensorInfo = {
        //             name: sensor["sensorName"],
        //             currentPage: 0,
        //             pagesCount: sensor["pagesCount"]
        //         }
        //         sensorsInfo.push(newSensorInfo);
        //     }
        //
        // }
    });
}