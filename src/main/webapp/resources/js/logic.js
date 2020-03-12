function logicPageInit() {
    refreshLogicPageEventHandlers();
    requestLogicCommands();
}

function requestLogicCommands() {
    $.ajax({
        type: "GET",
        url: "console/logic/get-control-commands",
        contentType: 'application/json',
        success: function(data) {
            console.log(data);
            for(var command in data) {
                addNewLocicCommand(data[command]["id"]);
                fillLogicCommand(data[command]);
            }
        }
    });
}

function fillLogicCommand(commandData) {
    var commandObj = $("#command_" + commandData["id"]);

    commandObj.find(".logic-block-for").find("input").val(commandData["targetName"]);
    commandObj.find(".logic-block-action").find("select").val(commandData["action"]);
    commandObj.find(".logic-block-value").find("input").val(commandData["value"]);
    commandObj.find(".logic-block-if-object").find("input").val(commandData["conditions"]["0"]["sensorName"]);
    commandObj.find(".logic-block-condition").find("select").val(commandData["conditions"]["0"]["conditionType"]);
    commandObj.find(".logic-block-if-object-val").find("input").val(commandData["conditions"]["0"]["value"]);
    commandObj.find(".logic-block-action").find("select").change();
    commandObj.find(".logic-block-submit").hide();
}

function refreshLogicPageEventHandlers() {
    $(".logic-block-submit").off("click");
    $(".add_new_command").off('click');
    $(".logic-block-action").off('change', blockActionChanged);
    $(".control_command").find("select").off("change");
    $(".control_command").find("input").off("change");

    $(".add_new_command").on('click', addNewLocicCommand);
    $(".logic-block-submit").on("click", confirmUpdateLogicCommand);
    $(".logic-block-action").on('change', blockActionChanged);

    $(".control_command").find("select").on("change", commandUpdated);
    $(".control_command").find("input").on("change", commandUpdated);
}

function commandUpdated() {
    var command = $(this).closest(".control_command");
    command.find(".logic-block-submit").show();
}

function blockActionChanged() {
    var command = $(this).closest(".control_command");
    var value = $(this).find(".select-order-action").val();
    if (value == 3) {
        command.find(".logic-block-value").show()
    } else {
        command.find(".logic-block-value").hide()
    }
    console.log(value);
}

function confirmUpdateLogicCommand() {
    var parent = $(this).closest(".control_command");
    var id = parent.attr("id");

    var commandName = parent.find(".logic-block-for").find("input").val();
    var commandAction = parent.find(".logic-block-action").find("select").val();
    var commandValue = parent.find(".logic-block-value").find("input").val();
    var ifSensorName = parent.find(".logic-block-if-object").find("input").val();
    var condition = parent.find(".logic-block-condition").find("select").val();
    var conditionValue = parent.find(".logic-block-if-object-val").find("input").val();

    var validCommandName = commandName != null && commandName !== "";
    var validCommandValue = commandValue != null && commandValue !== "";
    var validConditionValue = conditionValue != null && conditionValue !== "";

    var controlCommand = {
        targetName: commandName,
        action: commandAction,
        value: +commandValue,
        conditions: [
            {
                sensorName: ifSensorName,
                conditionType:condition,
                value:+conditionValue
            }
        ]
    };

    if (id != null && id != "") {
        console.log(id);
        controlCommand["id"] = id.substring(8);
    }

    console.log(controlCommand);

    if (validCommandName && validCommandValue && validConditionValue)
        requestCreateControlCommand(controlCommand,$(this));
}

function requestUserSensorsName() {
    $.ajax({
        type: "GET",
        url: "console/logic/get-sensors-name",
        contentType: 'application/json',
        success: function(data) {
            console.log(data);
        }
    });
}

function requestCreateControlCommand(controlCommand, confirmButton) {
    $.ajax({
        type: "POST",
        url: "console/logic/create-control-command",
        contentType: 'application/json',
        data: JSON.stringify(controlCommand),
        success: function(data) {
            confirmButton.hide();
        }
    });
}

function getActionNumber(actionStr) {
    var actionNum;
    switch(actionStr) {
        case "Включить": actionNum = 1;break;
        case "Выключить": actionNum = 2;break;
        case "Значение": actionNum = 3;break;
    }

    return actionNum;
}

function getConditionNumber(conditionStr) {
    var conditionNum;
    switch(conditionStr) {
        case ">": conditionNum = 0;break;
        case "<": conditionNum = 1;break;
        case ">=": conditionNum = 2;break;
        case "<=": conditionNum = 3;break;
        case "=": conditionNum = 4;break;
    }

    return conditionNum;
}

function addNewLocicCommand(id) {
    requestUserSensorsName();

    var logicCommand;
    if (typeof(id) == "number") {
        logicCommand = "<div id='command_" + id +"' class=\"control_command\">";
    } else
        logicCommand = "<div class=\"control_command\">";


    logicCommand += getLogicCommandHtml("if-block");
    logicCommand += getLogicCommandHtml("condition");
    logicCommand += getLogicCommandHtml("if-object-val")
    logicCommand += getLogicCommandHtml("do");
    logicCommand += getLogicCommandHtml("for");
    logicCommand += getLogicCommandHtml("action");
    logicCommand += getLogicCommandHtml("value");
    logicCommand += getLogicCommandHtml("submit-block");
    logicCommand += "</div>";

    $(".add_new_command").before(logicCommand);

    refreshLogicPageEventHandlers();
}

function getLogicCommandHtml(type) {
    var logicBlock = " <div class=\"control_command_block " + getLogicBlockClass(type) + "\">\n" +
        "                <table class=\"control_command_table\">\n" +
        "                    <tr><td class=\"control_command_margin\"></td></tr>\n" +
        "                    <tr><td valign='middle' align=\"center\" class=\"control_command_body\">\n" +
                          getLogicCommandBodyHtml(type) +
        "                        </td></tr>\n" +
        "                    <tr><td class=\"control_command_margin\"></td></tr>\n" +
        "                </table>\n" +
        "            </div>";

    return logicBlock;
}

function getLogicBlockClass(type) {
    var blockClass;
    switch(type) {
        case "for":
            blockClass = "logic-block-for";
            break;
        case "action":
            blockClass = "logic-block-action";
            break;
        case "value":
            blockClass = "logic-block-value";
            break;
        case "do":
            blockClass = "logic-block-if";
            break;
        case "if-block" :
            blockClass = "logic-block-if-object";
            break;
        case "condition":
            blockClass = "logic-block-condition";
            break;
        case "if-object-val":
            blockClass = "logic-block-if-object-val";
            break;
        case "submit-block":
            blockClass = "logic-block-submit highlight_button";
            break;
    }

    return blockClass;
}

function getLogicCommandBodyHtml(type) {
    var blockBody;
    switch(type) {
        case "for":
            blockBody = "<div>для\n" +
                "             <input class='input-sensor' type=\"search\" list=\"character\">\n" +
                // "             <datalist id=\"character\">\n" +
                // "                 <option value=\"Чебурашка\"></option>\n" +
                // "                 <option value=\"Крокодил Гена\"></option>\n" +
                // "                 <option value=\"Шапокляк\"></option>\n" +
                // "             </datalist>\n" +
                "        </div>";
            break;
        case "action":
            blockBody = "<select class=\"select-order-action\">\n" +
                "            <option value='1'>Включить</option>\n" +
                "            <option value='2'>Выключить</option>\n" +
                "            <option value='3'>Значение</option>\n" +
                "        </select>"
            break;
        case "value":
            blockBody = "<input class=\"control_command_value\" type=\"number\" name=\"type\"/>";
            break;
        case "do":
            blockBody = "<div style='padding-bottom: 4px'>то</div>";
            break;
        case "if-block" :
            blockBody = "<div>Если \n" +
                "             <input type=\"search\" list=\"character\">\n" +
                // "             <datalist id=\"character\">\n" +
                // "                 <option value=\"Чебурашка\"></option>\n" +
                // "                 <option value=\"Крокодил Гена\"></option>\n" +
                // "                 <option value=\"Шапокляк\"></option>\n" +
                // "             </datalist>\n" +
                "        </div>";
            break;
        case "condition":
            blockBody = " <select>\n" +
                "             <option value='1'>&#62;</option>\n" +
                "             <option value='2'>&#60;</option>\n" +
                "             <option value='3'>&#62;=</option>\n" +
                "             <option value='4'>&#60;=</option>\n" +
                "             <option value='5'>=</option>\n" +
                "         </select>";
            break;
        case "if-object-val":
            blockBody = "<input class=\"if-object-val\" type=\"number\" name=\"type\"/>";
            break;
        case "submit-block":
            blockBody = " <span>Готово</span>";
            break;
    }

    return blockBody;
}