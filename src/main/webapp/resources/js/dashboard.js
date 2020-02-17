var widgetsUpdateInterval;

function dashboardPageStart() {
    clearInterval(widgetsUpdateInterval);
    requestWidgets();
    widgetsUpdateInterval = setInterval(requestWidgets, 10000);
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
    var icon = getIconOption(widgetObj["icon"]);

    $(".input_widget_name").val(name);
    $(".input_widget_measure").val(measure);
    $(".choose-color-menu").val(optionColor);
    $(".choose-icon-menu").val(icon);
    document.location.href = "#widget_modal_window";
}

function applyWidgetChanges(e) {
    e.preventDefault();

    var id = selectedWidgetId;
    var name =  $(".input_widget_name").val();
    var measure =  $(".input_widget_measure").val();
    var optionColor = $(".choose-color-menu").val();
    var optionIcon = $(".choose-icon-menu").val();
    var colorNum = getColorNum(optionColor);
    var iconNum = getIconNum(optionIcon);
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
        keyWard: keyWard,
        icon:iconNum
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
                let icon = widget["icon"];
                let measure = widget["measure"];

                let sensor = data[widget_num]["sensor"];
                let keyward = sensor["name"];
                let value = sensor["value"];
                let arriveTime = sensor["arriveTime"];
                addWidget(name, color,icon, sensor, id, keyward, value, measure, arriveTime);
            }
        }
    });
}

function addWidget(name, color, icon, sensor, id, keyWard, value, measure, arriveTime) {
    var borderColor = getBorderColorString(color, 0.3);
    var valueColor = getValueColorString(color);
    var configColor = getBorderColorString(color, 0.4);
    var configActiveColor = getBorderColorString(color, 1);
    var iconStr = getIconStr(icon);

    var widgetHtml = " <div id='" + id + "' class=\"widget\" style=\"border: 1px solid " + borderColor + "; border-radius: 2px\">\n" +
        "                    <table class=\"widget-table\">\n" +
        "                        <tr><td><img class='widget-icon' src=\"" + iconStr + "\"></td>\n" +
        "                            <td class=\"widget-name\"><span>" + name + "</span></td>\n" +
        "                        <td></td></tr>\n" +
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

    memWidget(name, color, icon, sensor, id, keyWard, value, measure, arriveTime);
    refreshWidgetHandlers();
}

function memWidget(name, color, icon, sensor, id, keyWard, value, measure, arriveTime) {
    var widget = {
        id : id,
        name: name,
        color: color,
        icon:icon,
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

function getIconOption(iconNum) {
    let iconStr = "";
    switch (iconNum) {
        case 0: iconStr = "Нет"; break;
        case 1: iconStr = "Внимание"; break;
        case 2: iconStr = "Угол"; break;
        case 3: iconStr = "Огонь"; break;
        case 4: iconStr = "Цветок"; break;
        case 5: iconStr = "Длина"; break;
        case 6: iconStr = "Молния"; break;
        case 7: iconStr = "Дождь"; break;
        case 8: iconStr = "Скорость"; break;
        case 9: iconStr = "Солнце"; break;
        case 10: iconStr ="Температура"; break;
        case 11: iconStr ="Вода"; break;
        default: iconStr = "Нет"; break;
    }

    return iconStr;
}

function getIconStr(icon) {
    let iconPath = "./resources/images/widget_icons/";
    let iconStr = "";
    switch (icon) {
        case 0: iconStr = "none.png";break;
        case 1: iconStr = "alert.png";break;
        case 2: iconStr = "angle.png";break;
        case 3: iconStr = "fire.png";break;
        case 4: iconStr = "flower.png";break;
        case 5: iconStr = "length.png";break;
        case 6: iconStr = "lightning.png";break;
        case 7: iconStr = "rain.png";break;
        case 8: iconStr = "speedmeter.png";break;
        case 9: iconStr = "sun.png";break;
        case 10: iconStr = "temp.png";break;
        case 11: iconStr = "waterCoef.png";break;
        default: iconStr = "none.png";break;
    }

    return iconPath + iconStr;
}

function getIconNum(iconStr) {
    let iconNum = 0;
    console.log(iconStr);
    switch (iconStr) {
        case "Нет": iconNum = 0;break;
        case "Внимание": iconNum = 1;break;
        case "Угол": iconNum = 2;break;
        case "Огонь": iconNum = 3;break;
        case "Цветок": iconNum = 4;break;
        case "Длина": iconNum = 5;break;
        case "Молния": iconNum = 6;break;
        case "Дождь": iconNum = 7;break;
        case "Скорость": iconNum = 8;break;
        case "Солнце": iconNum = 9;break;
        case "Температура": iconNum = 10;break;
        case "Вода": iconNum = 11;break;
        default: iconNum = 0;break;
    }
    console.log(iconNum);
    return iconNum;
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
        case 0: colorString = "Черный"; break; //black
        case 1: colorString = "Красный"; break;//red
        case 2: colorString = "Зеленый"; break;//green
        case 3: colorString = "Синий"; break;//blue
        case 4: colorString = "Желтый"; break;//yellow
        case 5: colorString = "Бирюзовый"; break;//cian
        case 6: colorString = "Фиолетовый"; break;//magenta
    }

    return colorString;

}

function getColorNum(colorStr) {
    let colorNum = 0;
    switch (colorStr) {
        case "Черный": colorNum = 0; break; //black
        case "Красный": colorNum = 1; break;//red
        case "Зеленый": colorNum = 2; break;//green
        case "Синий": colorNum = 3; break;//blue
        case "Желтый": colorNum = 4; break;//yellow
        case "Бирюзовый": colorNum = 5; break;//cian
        case "Фиолетовый": colorNum = 6; break;//magenta
    }

    return colorNum;
}