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
            for (var widget_num in data) {
                let name = data[widget_num]["name"];
                let color = data[widget_num]["color"];
                let sensor = data[widget_num]["sensor"];
                let id = data[widget_num]["id"];
                let keyward = sensor["name"];
                let value = sensor["value"];
                let measure = sensor["measure"];
                let arriveTime = sensor["arriveTime"];
                addWidget(name, color, sensor, id, keyward, value, measure, arriveTime);
            }
        }
    });
}

function addWidget(name, color, sensor, id, keyward, value, measure, arriveTime) {
    var borderColor = getBorderColorString(color);
    var valueColor = getValueColorString(color);

    var widgetHtml = " <div id='" + id + "' class=\"widget\" style=\"border: 1px solid " + borderColor + "; border-radius: 2px\">\n" +
        "                    <table class=\"widget-table\">\n" +
        "                        <tr>\n" +
        "                            <td class=\"widget-name\" colspan=\"3\"><span>" + name + "</span></td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td class=\"widget-content\" colspan=\"3\">\n" +
        "                                <h1 style=\"color: " + valueColor + "\"> " + value + "</h1>\n" +
        "                                <p>volts</p>\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td class=\"widget-icon-wrap\">\n" +
        "                            </td>\n" +
        "                            <td class=\"widget-keyword\">" + keyward + "</td>\n" +
        "                            <td class=\"widget-config-wrap\">\n" +
        "                                <img src=\"../resources/images/config.png\">\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                    </table>\n" +
        "                </div>";
    $(".widgets-wrap").append(widgetHtml);
}

function getBorderColorString(colorNum) {
    let colorString = "rgba(100, 100, 100, 0.3)";
    switch (colorNum) {
        case 0: colorString = "rgba(100, 100, 100, 0.3)"; break; //black
        case 1: colorString = "rgba(255, 0, 0, 0.3)"; break;//red
        case 2: colorString = "rgba(0, 255, 0, 0.3)"; break;//green
        case 3: colorString = "rgba(0, 0, 255, 0.3)"; break;//blue
        case 4: colorString = "rgba(255, 255, 0, 0.3)"; break;//yellow
        case 5: colorString = "rgba(0, 255, 255, 0.3)"; break;//aqumarine
        case 6: colorString = "rgba(255, 0, 255, 0.3)"; break;//magenta
    }

    return colorString;
}

function getValueColorString(colorNum) {
    let colorString = "rgba(100, 100, 100, 0.3)";
    switch (colorNum) {
        case 0: colorString = "#000"; break; //black
        case 1: colorString = "#f00"; break;//red
        case 2: colorString = "#0f0"; break;//green
        case 3: colorString = "#00f"; break;//blue
        case 4: colorString = "#ff0"; break;//yellow
        case 5: colorString = "#0ff"; break;//aqumarine
        case 6: colorString = "#f0f"; break;//magenta
    }

    return colorString;
}