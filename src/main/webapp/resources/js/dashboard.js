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
                let id = data[widget_num]["id"];
                let name = data[widget_num]["name"];
                let color = data[widget_num]["color"];
                let measure = data[widget_num]["measure"];

                let sensor = data[widget_num]["sensor"];
                let keyward = sensor["name"];
                let value = sensor["value"];
                let arriveTime = sensor["arriveTime"];
                addWidget(name, color, sensor, id, keyward, value, measure, arriveTime);
            }
        }
    });
}

function addWidget(name, color, sensor, id, keyward, value, measure, arriveTime) {
    var borderColor = getBorderColorString(color, 0.3);
    var valueColor = getValueColorString(color);
    var widgetMeasure = getMeasureForWidget(measure);
    var configColor = getBorderColorString(color, 0.4);
    var configActiveColor = getBorderColorString(color, 1);

    var widgetHtml = " <div id='" + id + "' class=\"widget\" style=\"border: 1px solid " + borderColor + "; border-radius: 2px\">\n" +
        "                    <table class=\"widget-table\">\n" +
        "                        <tr>\n" +
        "                            <td class=\"widget-name\" colspan=\"3\"><span>" + name + "</span></td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td class=\"widget-content\" colspan=\"3\">\n" +
        "                                <h1 style=\"color: " + valueColor + "\"> " + value + "</h1>\n" +
        "                                <p>" + widgetMeasure + "</p>\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td class=\"widget-icon-wrap\">\n" +
        "                            </td>\n" +
        "                            <td class=\"widget-keyword\">" + keyward + "</td>\n" +
        "                            <td class=\"widget-config-wrap\">\n" +
        "                                <img style='background: " + configColor + "' src=\"../resources/images/config-inv.png\" onmouseover=\"this.style.backgroundColor='" +  configActiveColor+ "'\"" +
        "onmouseout=\"this.style.backgroundColor='" +  configColor+ "'\">\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                    </table>\n" +
        "                </div>";
    $(".widgets-wrap").append(widgetHtml);
}

function getBorderColorString(colorNum, opacity) {
    let colorString = "rgba(100, 100, 100, 0.3)";
    switch (colorNum) {
        case 0: colorString = "rgba(0, 0, 0, " + opacity  + ")"; break; //black
        case 1: colorString = "rgba(255, 0, 0," + opacity  + ")"; break;//red
        case 2: colorString = "rgba(0, 130, 0," + opacity  + ")"; break;//green
        case 3: colorString = "rgba(0, 0, 255, " + opacity  + ")"; break;//blue
        case 4: colorString = "rgba(255, 255, 0, " + opacity  + ")"; break;//yellow
        case 5: colorString = "rgba(0, 255, 255, " + opacity  + ")"; break;//aqumarine
        case 6: colorString = "rgba(255, 0, 255, " + opacity  + ")"; break;//magenta
    }

    return colorString;
}

function getValueColorString(colorNum) {
    let colorString = "rgba(100, 100, 100, 0.3)";
    switch (colorNum) {
        case 0: colorString = "#000"; break; //black
        case 1: colorString = "#f00"; break;//red
        case 2: colorString = "#090"; break;//green
        case 3: colorString = "#00f"; break;//blue
        case 4: colorString = "#ff0"; break;//yellow
        case 5: colorString = "#0ff"; break;//aqumarine
        case 6: colorString = "#f0f"; break;//magenta
    }

    return colorString;
}

function getMeasureForWidget(measureNum) {
    var colorString = "";
    switch (measureNum) {
        case 0: colorString = ""; break;
        case 1: colorString = "%"; break;
        case 2: colorString = "вольт (В)"; break;
        case 3: colorString = "амрер(А)"; break;
        case 4: colorString = "метр"; break;
        case 5: colorString = "паскаль(Па)"; break;
        case 6: colorString = "ньютон(Н)"; break;
    }

    return colorString;
}