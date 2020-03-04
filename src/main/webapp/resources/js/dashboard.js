var widgetsUpdateInterval;

function dashboardPageStart() {
    clearInterval(widgetsUpdateInterval);
    requestWidgets();
    refreshWidgetHandlers();
}

function dashboardPageStop() {
    clearInterval(widgetsUpdateInterval);
}

var selectedWidgetId;

var WIDGET_UPDATE_TIMEOUT = 10000;

function refreshWidgetHandlers() {
    $(".widget-config-button").off("click");
    $(".apply_modal_widget").off('click');
    $("#add_new_widget").off('click');
    $(".toggle_button").off('click',);
    $(".choose-type-menu").off("change");

    $(".apply_modal_widget").on('click', applyWidgetChanges);
    $(".widget-config-button").on("click", updateWidgetAction);
    $("#add_new_widget").on('click', createWidgetAction);
    $(".toggle_button").on('click', toggleButtonAction);

    $(".widget").resizable({containment:'.widgets-wrap', handles:'e,s'});
    $(".widget").resizable( "option", "minHeight", 200 );
    $(".widget").resizable( "option", "minWidth", 200 );

    $(".widget").resizable({stop:sendNewWidgetSize});
    $(".choose-type-menu").on("change", displayWidgetBoundsFields);

    $( ".slider" ).slider();
}

var toggleButtonLock = false;
function toggleButtonAction() {
    if (toggleButtonLock)
        return;
    else
        toggleButtonLock = true;

    var widget = $(this).closest(".widget");
    var id = widget.attr("id");

    var widgetObj;
    for (var widgetVal in widgets) {
        if (widgets[widgetVal]["id"] == id) {
            widgetObj = widgets[widgetVal];
        }
    }

    var keyWard = widgetObj["keyWard"];
    var deviceName = widgetObj["deviceName"];
    var value;
    if ($(this).attr("src") === "./resources/images/toggleButtonOff.png") {
        value = 1;
    }
    else {
        value = 0;
    }


    sendCloudOrderButton(keyWard, value, deviceName, $(this));

}

function sendCloudOrderButton(keyWard, value, deviceName, button) {
    var cloudOrder = {
        keyWard:keyWard,
        value: value,
        deviceName: deviceName
    }

    $.ajax({
        type: "POST",
        url: "console/dashboard/record-cloud-order",
        contentType: 'application/json',
        data: JSON.stringify(cloudOrder),
        success: function(data) {
            if (button.attr("src") === "./resources/images/toggleButtonOff.png") {
                button.attr("src","./resources/images/toggleButtonOn.png");
            }
            else {
                button.attr("src","./resources/images/toggleButtonOff.png");
            }
        },
        complete: function () {
            toggleButtonLock = false;
        }
    });
}

var widgetsSize = [];
var widgetSizeTimeout;
var sizeUpdating = false;
function widgetSizeChanged() {
    sizeUpdating = true;
    clearTimeout(widgetSizeTimeout);
    widgetSizeTimeout = setTimeout(new function() {sizeUpdating = false}, 500);
}

function sendNewWidgetSize(event,ui) {
    let id = $(this).attr("id");
    let width = ui.size.width;
    let height = ui.size.height;

    var foundNum;
    let alreadyExist = false;
    for (var widgetSizeEl in widgetsSize) {
        if (widgetsSize[widgetSizeEl]["id"] == id) {
            alreadyExist = true;
            foundNum = widgetSizeEl;
        }
    }
    if (alreadyExist) {
        widgetsSize[foundNum]["width"] = width;
        widgetsSize[foundNum]["height"] = height;
    } else {
        var widgetSize = {
            id:+id,
            width:width,
            height:height
        }
        widgetsSize.push(widgetSize);
    }
    requestChangeWidgetSize();
}

function requestChangeWidgetSize() {
    $.ajax({
        type: "POST",
        url: "console/dashboard/update-widgets-size",
        contentType: 'application/json',
        data: JSON.stringify(widgetsSize),
        success: function(data) {
            widgetsSize = [];
        }
    });
}

var createWidget;

function updateWidgetAction() {
    createWidget = false;
    openModalWindow($(this));
}

function createWidgetAction() {
    createWidget = true;
    openModalWindow($(this));
}

function openModalWindow(widgetThis) {
    displayWidgetBoundsFields();
    if (createWidget)
        openModalWindowCreate(widgetThis);
    else
        openModalWindowUpdate(widgetThis);
}

function openModalWindowUpdate(widgetThis) {
    var widget =widgetThis.closest(".widget");
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
    var keyWard = widgetObj["keyWard"];
    var type = widgetObj["type"];
    var deviceName = widgetObj["deviceName"];
    var minValue = widgetObj["minValue"];
    var maxValue = widgetObj["maxValue"];


    $(".input_widget_name").val(name);
    $(".input_widget_measure").val(measure);
    $(".choose-color-menu").val(optionColor);
    $(".choose-icon-menu").val(icon);
    $(".input_widget_keyward").val(keyWard);
    $(".input_widget_deviceName").val(deviceName);
    $(".choose-type-menu option[value = " + type + "]").attr("selected",true);
    $(".choose-type-menu option[value != " + type + "]").attr("selected",false);
    $(".input-widget-minValue").val(minValue);
    $(".input-widget-maxValue").val(maxValue);

    document.location.href = "#widget_modal_window";
}

function displayWidgetBoundsFields() {
    var type = $(".choose-type-menu option:selected").attr("value");

    if (type == 2) {
        $("#input-widget-minValue-wrap").show();
        $("#input-widget-maxValue-wrap").show();
    } else {
        $("#input-widget-minValue-wrap").hide();
        $("#input-widget-maxValue-wrap").hide();
    }}

function openModalWindowCreate() {

    var optionColor = getOptionColor(0);
    var icon = getIconOption(0);

    $(".input_widget_name").val("");
    $(".input_widget_measure").val("");
    $(".choose-color-menu").val(optionColor);
    $(".choose-icon-menu").val(icon);
    $(".input_widget_keyward").val("");
    $(".input_widget_deviceName").val("");
    $(".input-widget-minValue").val(0);
    $(".input-widget-maxValue").val(100);

    document.location.href = "#widget_modal_window";
}

function applyWidgetChanges(e) {
    e.preventDefault();

    var id = selectedWidgetId;
    var name =  $(".input_widget_name").val();
    var measure =  $(".input_widget_measure").val();
    var optionColor = $(".choose-color-menu").val();
    var optionIcon = $(".choose-icon-menu").val();
    var optionType = $(".choose-type-menu option:selected").attr("value");
    var colorNum = getColorNum(optionColor);
    var iconNum = getIconNum(optionIcon);
    var keyWard = $(".input_widget_keyward").val();
    var deviceName = $(".input_widget_deviceName").val();
    var minValue = $(".input-widget-minValue").val();
    var maxValue = $(".input-widget-maxValue").val();

    if (minValue == "") {
        minValue = 0;
    }
    if (maxValue == "") {
        maxValue = 100;
    }

    var widgetData = {
        id: id,
        name: name,
        color: colorNum,
        measure: measure,
        keyWard: keyWard,
        icon:iconNum,
        type:optionType,
        deviceName: deviceName,
        minValue: minValue,
        maxValue:maxValue
    };

    if (createWidget)
        createWidgetData(widgetData)
    else
        updateWidgetData(widgetData);
}

function createWidgetData(widgetData) {
    delete widgetData["id"];
    console.log(widgetData);

    $.ajax({
        type: "POST",
        url: "console/dashboard/create-widget",
        contentType: 'application/json',
        data: JSON.stringify(widgetData),
        success: function(data) {
            document.location.href = "#";
            requestWidgets();
        }
    });
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
        url: "console/dashboard/get-widgets",
        contentType: 'application/json',
        success: function(data) {
            console.log(data);
            $(".widget").remove();
            for (var widget_num in data) {
                let widget = data[widget_num]["widget"];

                let sensor = data[widget_num]["sensor"];
                addWidget(widget, sensor);
            }
            clearInterval(widgetsUpdateInterval);
            widgetsUpdateInterval = setInterval(requestUpdateWidgets, WIDGET_UPDATE_TIMEOUT);
        }
    });
}

function requestUpdateWidgets() {
    $.ajax({
        type: "GET",
        url: "console/dashboard/get-widgets",
        contentType: 'application/json',
        success: function(data) {
            console.log(data);
            for (var widget_num in data) {
                let widget = data[widget_num]["widget"];

                let sensor = data[widget_num]["sensor"];
                updateWidget(widget, sensor);
            }
        }
    });
}

function updateWidget(widget, sensor)  {
    var borderColor = getBorderColorString(widget["color"], 0.3);
    var valueColor = getValueColorString(widget["color"]);
    var configColor = getBorderColorString(widget["color"], 0.4);
    var configActiveColor = getBorderColorString(widget["color"], 1);
    var iconStr = getIconStr(widget["icon"]);

    var widgetObj = $("#" + widget["id"]);
    widgetObj.css("border: 1px solid " + borderColor + "; border-radius: 2px;");
    if (!sizeUpdating) {
        widgetObj.css("width: " + widget["width"] + "px; height: " +widget["height"] + "px;");
    }
    widgetObj.find(".widget-icon").attr("src", iconStr);
    widgetObj.find(".widget-name span").html(widget["name"]);
    // widgetObj.find(".widget-content").html(getWidgetBodyHtml(widget, sensor));
    updateWidgetBody(widget, sensor);
    widgetObj.find(".widget-keyword").html(widget["keyWard"]);
    widgetObj.find(".widget-config-button").css("background: " + configColor + ";");
    widgetObj.find(".widget-config-button").attr("onmouseover", "this.style.backgroundColor='" +  configActiveColor+ "'");
    widgetObj.find(".widget-config-button").attr("onmouseout", "this.style.backgroundColor='" +  configColor+ "'");

    if (widget["type"] == 2)
        sliderInit(widget, sensor);

    memWidget(widget);
    refreshWidgetHandlers();
}

function addWidget(widget, sensor) {
    var borderColor = getBorderColorString(widget["color"], 0.3);
    var valueColor = getValueColorString(widget["color"]);
    var configColor = getBorderColorString(widget["color"], 0.4);
    var configActiveColor = getBorderColorString(widget["color"], 1);
    var iconStr = getIconStr(widget["icon"]);

    var widgetHtml = " <div id='" + widget["id"] + "' class=\"widget\" style=\"border: 1px solid " + borderColor + "; border-radius: 2px; width: " + widget["width"] + "px; height: " +widget["height"] + "px;\">\n" +
        "                    <table class=\"widget-table\">\n" +
        "                        <tr><td><img class='widget-icon' src=\"" + iconStr + "\"></td>\n" +
        "                            <td class=\"widget-name\"><span>" + widget["name"] + "</span></td>\n" +
        "                        <td></td></tr>\n" +
        "                        <tr>\n" +
        "                            <td class=\"widget-content\" colspan=\"3\">\n" +
        "                               "  + getWidgetBodyHtml(widget, sensor) +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td class=\"widget-icon-wrap\">\n" +
        "                            </td>\n" +
        "                            <td class=\"widget-keyword\">" + widget["keyWard"] + "</td>\n" +
        "                            <td class=\"widget-config-wrap\">\n" +
        "                                <img class='widget-config-button' style='background: " + configColor + "' src=\"./resources/images/config-inv.png\" onmouseover=\"this.style.backgroundColor='" +  configActiveColor+ "'\"" +
        "onmouseout=\"this.style.backgroundColor='" +  configColor+ "'\">\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                    </table>\n" +
        "                </div>";

    $("#add_new_widget").before(widgetHtml);

    if (widget["type"] == 2)
        sliderInit(widget, sensor);

    memWidget(widget);
    refreshWidgetHandlers();
}

function updateWidgetBody(widget, sensor) {
    var valueColor = getValueColorString(widget["color"]);
    var configColor = getBorderColorString(widget["color"], 0.4);
    var configActiveColor = getBorderColorString(widget["color"], 0.8);
    var imagePath;
    if (sensor != null && sensor["value"] != 0)
        imagePath = "./resources/images/toggleButtonOn.png";
    else
        imagePath = "./resources/images/toggleButtonOff.png";

    var widgetObj = $(".widget[id = " + widget["id"] +"]");
    switch (widget["type"]) {
    case 0 :
        let val;
        val = sensor ==null? "?": sensor["value"];

        widgetObj.find(".widget-content h1").css("color: " + valueColor);
        widgetObj.find(".widget-content h1").html(val);
        widgetObj.find(".widget-content").find(".widget-measure").html(widget["measure"]);
        break;
    case 1 :
        let buttonImg = widgetObj.find(".toggle_button_img");
        let oldImg = buttonImg.attr("src");
        if (oldImg != imagePath)
            widgetObj.find(".toggle_button").attr("src",imagePath);
        buttonImg.css("background:" + configActiveColor);
        buttonImg.attr("onmouseover", "this.style.backgroundColor = '" +  configColor+ "'");
        buttonImg.attr("onmousedown", "this.style.backgroundColor = '" +  configActiveColor+ "'");
        buttonImg.attr("onmouseup", "this.style.backgroundColor = '" +  configColor+ "'");
        break;
    case 2 :
        var minVal = +widget["minValue"];
        var maxVal = +widget["maxValue"];
        var value;

        if (sensor == null)
            value = (minVal + maxVal) /2;
        else {
            value = sensor["value"];
            if (value < minVal)
                value = minVal;
            if (value > maxVal)
                value = maxVal;
        }
        var slider = widgetObj.find( ".slider" );
        slider.slider({min:minVal, max:maxVal, value:value, step:0.1, animate: "fast"});
        break;
    }

}

function getWidgetBodyHtml(widget, sensor) {
    var valueColor = getValueColorString(widget["color"]);
    var configColor = getBorderColorString(widget["color"], 0.4);
    var configActiveColor = getBorderColorString(widget["color"], 0.8);
    var imagePath;
    if (sensor != null && sensor["value"] != 0)
        imagePath = "./resources/images/toggleButtonOn.png";
    else
        imagePath = "./resources/images/toggleButtonOff.png";
    var widgetBodyHtml;
    switch (widget["type"]) {
        case 0 :
            let val;
            if (sensor == null)
                val = "?";
            else
                val = sensor["value"];
            widgetBodyHtml = "<h1 style=\"color: " + valueColor + "\"> " + val + "</h1>\n" +
                "                                <p class='widget-measure'>" + widget["measure"] + "</p>";
            break;
        case 1 :
            widgetBodyHtml = "<img style='background:" + configActiveColor + "' onmouseover=\"this.style.backgroundColor = '" +  configColor+ "'\" " +
                " onmouseout=\"this.style.backgroundColor = '" +  configActiveColor+ "'\"" +
                " onmousedown=\"this.style.backgroundColor = '" +  configActiveColor+ "'\"" +
                " onmouseup=\"this.style.backgroundColor = '" +  configColor+ "'\"" +
                " class='toggle_button' src='" + imagePath +"'>";
            break;
        case 2 :
            var sliderVal;
            if (sensor == null)
                sliderVal = widget["minValue"]/2 + widget["maxValue"]/2;
            else
                sliderVal = sensor["value"];
            widgetBodyHtml = "<p class='slider_value_text' style=\"color: " + valueColor + "\">" + sliderVal + "</p>" +
                "<div class='slider'></div>" +
                "<p class='widget-measure'>" + widget["measure"] + "</p>";
            break;

    }

    return widgetBodyHtml;
}

function sliderInit(widget, sensor) {
    var minVal = +widget["minValue"];
    var maxVal = +widget["maxValue"];
    var value;

    if (sensor == null)
        value = (minVal + maxVal) /2;
    else {
        value = sensor["value"];
        if (value < minVal)
            value = minVal;
        if (value > maxVal)
            value = maxVal;
    }
    var slider = $('.widget[id = ' + widget["id"] + ']').find( ".slider" );
    slider.slider({min:minVal, max:maxVal, value:value, step:0.1, animate: "fast"});
    slider.slider({slide:sliderMovedAction});
    slider.slider({stop:sliderValChangeAction});
}

function sliderMovedAction(event, ui) {
    var value = ui.value;
    var widget = $(this).closest(".widget");
    widget.find(".slider_value_text").html(value);
}

function sliderValChangeAction(event, ui) {
    var value = ui.value;
    var widget = $(this).closest(".widget");
    var id = widget.attr("id");

    var widgetObj;
    for (var widgetVal in widgets) {
        if (widgets[widgetVal]["id"] == id) {
            widgetObj = widgets[widgetVal];
        }
    }
    var keyWord = widgetObj["keyWard"];
    var device = widgetObj["deviceName"];

    sendCloudOrderSlider(keyWord,value, device);
}

function sendCloudOrderSlider(keyWard, value, deviceName) {
    console.log("change");
    var cloudOrder = {
        keyWard:keyWard,
        value: value,
        deviceName: deviceName
    }

    console.log(cloudOrder);

    $.ajax({
        type: "POST",
        url: "console/dashboard/record-cloud-order",
        contentType: 'application/json',
        data: JSON.stringify(cloudOrder),
        success: function(data) {
            console.log("success");
        }
    });
}

function memWidget(widget) {

    for (var oldWidget in widgets) {
        if (widgets[oldWidget]["id"] == widget["id"]) {
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