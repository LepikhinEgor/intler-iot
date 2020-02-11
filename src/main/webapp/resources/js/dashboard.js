function dashboardPageStart() {
    requestWidgets();
    setInterval(requestWidgets, 10000);
}

var selectedWidgetId;

function refreshWidgetHandlers() {
    $(".widget-config-button").off("click");
    $(".apply_modal_widget").off('click');

    $(".apply_modal_widget").on('click', applyWidgetChanges);
    $(".widget-config-button").on("click", openModalWindow);
}

function openModalWindow() {
    var widget =$(this).closest(".widget");
    var id = widget.attr("id")
    var name = widget.find(".widget-name").find("span").html();
    var keyward = widget.find(".widget-keyword").html();
    var measure = widget.find(".widget-measure").html();

    selectedWidgetId = id;

    var widgetObj;
    for (var widgetVal in widgets) {
        if (widgets[widgetVal]["id"] == id) {
            widgetObj = widgets[widgetVal];
        }
    }

    var optionColor = getOptionColor(widgetObj["color"]);

    $(".input_widget_name").val(name);
    $(".input_widget_measure").val(measure);
    $(".choose-color-menu").val(optionColor);
    document.location.href = "#widget_modal_window";
}

function applyWidgetChanges(e) {
    e.preventDefault();

    var id = selectedWidgetId;
    var name =  $(".input_widget_name").val();
    var measure =  $(".input_widget_measure").val();
    var optionColor = $(".choose-color-menu").val();
    var colorNum = getColorNum(optionColor);
    var keyWard;

    for (var widgetVal in widgets) {
        if (widgets[widgetVal]["id"] == id) {
            keyWard = widgets[widgetVal]["keyWard"];
        }
    }

    var widgetData = {
        id: id,
        name: name,
        color: colorNum,
        measure: measure,
        keyWard: keyWard
    };

    updateWidgetData(widgetData);
}

function updateWidgetData(widgetData) {
    var request = "console/dashboard/update-widget"

    $.ajax({
        type: "POST",
        url: "console/dashboard/update-widget",
        contentType: 'application/json',
        data: JSON.stringify(widgetData),
        success: function(data) {
            document.location.href = "#";
            requestWidgets();
        }
    });
}

var widgets = [];

function requestWidgets() {
    $.ajax({
        type: "GET",
        url: "/intler_iot_war_exploded/console/dashboard/get-widgets",
        contentType: 'application/json',
        success: function(data) {
            $(".widgets-wrap").html("");
            for (var widget_num in data) {
                let widget = data[widget_num]["widget"];
                let id = widget["id"];
                let name = widget["name"];
                let color = widget["color"];
                let measure = widget["measure"];

                let sensor = data[widget_num]["sensor"];
                let keyward = sensor["name"];
                let value = sensor["value"];
                let arriveTime = sensor["arriveTime"];
                addWidget(name, color, sensor, id, keyward, value, measure, arriveTime);
            }
        }
    });
}

function addWidget(name, color, sensor, id, keyWard, value, measure, arriveTime) {
    var borderColor = getBorderColorString(color, 0.3);
    var valueColor = getValueColorString(color);
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
        "                                <p class='widget-measure'>" + measure + "</p>\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td class=\"widget-icon-wrap\">\n" +
        "                            </td>\n" +
        "                            <td class=\"widget-keyword\">" + keyWard + "</td>\n" +
        "                            <td class=\"widget-config-wrap\">\n" +
        "                                <img class='widget-config-button' style='background: " + configColor + "' src=\"./resources/images/config-inv.png\" onmouseover=\"this.style.backgroundColor='" +  configActiveColor+ "'\"" +
        "onmouseout=\"this.style.backgroundColor='" +  configColor+ "'\">\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                    </table>\n" +
        "                </div>";
    $(".widgets-wrap").append(widgetHtml);

    memWidget(name, color, sensor, id, keyWard, value, measure, arriveTime);
    refreshWidgetHandlers();
}

function memWidget(name, color, sensor, id, keyWard, value, measure, arriveTime) {
    var widget = {
        id : id,
        name: name,
        color: color,
        sensor: sensor,
        keyWard: keyWard,
        value : value,
        measure : measure,
        arriveTime:arriveTime
    }

    for (var oldWidget in widgets) {
        if (widgets[oldWidget]["id"] == id) {
            widgets.splice(oldWidget, 1);
        }
    }

    widgets.push(widget);
}

function getBorderColorString(colorNum, opacity) {
    let colorString = "rgba(100, 100, 100, 0.3)";
    switch (colorNum) {
        case 0: colorString = "rgba(0, 0, 0, " + opacity  + ")"; break; //black
        case 1: colorString = "rgba(255, 0, 0," + opacity  + ")"; break;//red
        case 2: colorString = "rgba(0, 130, 0," + opacity  + ")"; break;//green
        case 3: colorString = "rgba(0, 0, 255, " + opacity  + ")"; break;//blue
        case 4: colorString = "rgba(220, 220, 0, " + opacity  + ")"; break;//yellow
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
        case 4: colorString = "#ee0"; break;//yellow
        case 5: colorString = "#0ff"; break;//aqumarine
        case 6: colorString = "#f0f"; break;//magenta
    }

    return colorString;
}

function getOptionColor(colorNum) {
    let colorString = "rgba(100, 100, 100, 0.3)";
    switch (colorNum) {
        case 0: colorString = "Black"; break; //black
        case 1: colorString = "Red"; break;//red
        case 2: colorString = "Green"; break;//green
        case 3: colorString = "Blue"; break;//blue
        case 4: colorString = "Yellow"; break;//yellow
        case 5: colorString = "Cian"; break;//cian
        case 6: colorString = "Magenta"; break;//magenta
    }

    return colorString;

}

function getColorNum(colorStr) {
    let colorNum = 0;
    switch (colorStr) {
        case "Black": colorNum = 0; break; //black
        case "Red": colorNum = 1; break;//red
        case "Green": colorNum = 2; break;//green
        case "Blue": colorNum = 3; break;//blue
        case "Yellow": colorNum = 4; break;//yellow
        case "Cian": colorNum = 5; break;//cian
        case "Magenta": colorNum = 6; break;//magenta
    }

    return colorNum;
}