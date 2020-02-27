function logicPageInit() {
    refreshLogicPageEventHandlers();
}

function refreshLogicPageEventHandlers() {
    $(".logic-block-submit").off("click");
    $(".add_new_command").off('click');

    $(".add_new_command").on('click', addNewLocicCommand);
    $(".logic-block-submit").on("click", confirmUpdateLogicCommand);
}

function confirmUpdateLogicCommand() {
    var parent = $(this).closest(".control_command");

    var commandName = parent.find(".logic-block-for").find("input").val();
    var commandAction = parent.find(".logic-block-action").find("select").val();
    var commandValue = parent.find(".logic-block-value").find("input").val();
    var ifSensorName = parent.find(".logic-block-if-object").find("select").val();
    var condition = parent.find(".logic-block-condition").find("select").val();
    var conditionValue = parent.find(".logic-block-if-object-val").find("input").val();

    var validCommandName = commandName != null && commandName !== "";
    var validCommandValue = commandValue != null && commandValue !== "";
    var validConditionValue = conditionValue != null && conditionValue !== "";

    var controlCommand = {
        targetName: commandName,
        action: getActionNumber(commandAction),
        value: +commandValue,
        conditions: [
            {
                sensorName: ifSensorName,
                conditionType:getConditionNumber(condition),
                value:+conditionValue
            }
        ]
    };

    if (validCommandName && validCommandValue && validConditionValue)
        requestCreateControlCommand(controlCommand);
}

function requestCreateControlCommand(controlCommand) {
    console.log(controlCommand);
    console.log(JSON.stringify(controlCommand));
    $.ajax({
        type: "POST",
        url: "console/logic/create-control-command",
        contentType: 'application/json',
        data: JSON.stringify(controlCommand),
        success: function(data) {
        }
    });
}

function getActionNumber(actionStr) {
    var actionNum;
    switch(actionStr) {
        case "Включить": actionNum = 0;break;
        case "Выключить": actionNum = 1;break;
        case "Значение": actionNum = 2;break;
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

function addNewLocicCommand() {
    var logicCommand = "<div class=\"control_command\">";

    logicCommand += getLogicCommandHtml("for");
    logicCommand += getLogicCommandHtml("action");
    logicCommand += getLogicCommandHtml("value");
    logicCommand += getLogicCommandHtml("if");
    logicCommand += getLogicCommandHtml("if-block");
    logicCommand += getLogicCommandHtml("condition");
    logicCommand += getLogicCommandHtml("if-object-val");
    logicCommand += getLogicCommandHtml("submit-block");
    logicCommand += "</div>";

    $(".add_new_command").before(logicCommand);

    refreshLogicPageEventHandlers();
}

function getLogicCommandHtml(type) {
    var logicBlock = " <div class=\"control_command_block " + getLogicBlockClass(type) + "\">\n" +
        "                <table class=\"control_command_table\">\n" +
        "                    <tr><td class=\"control_command_margin\"></td></tr>\n" +
        "                    <tr><td align=\"center\" class=\"control_command_body\">\n" +
                          getLogicCommandBodyHtml(type) +
        "                        </td></tr>\n" +
        "                    <tr><td class=\"control_command_margin\"></td></tr>\n" +
        "                </table>\n" +
        "            </div>"

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
        case "if":
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
            blockClass = "logic-block-submit";
            break;
    }

    return blockClass;
}

function getLogicCommandBodyHtml(type) {
    var blockBody;
    switch(type) {
        case "for":
            blockBody = "<div>Для\n" +
                "             <input type=\"search\" list=\"character\">\n" +
                // "             <datalist id=\"character\">\n" +
                // "                 <option value=\"Чебурашка\"></option>\n" +
                // "                 <option value=\"Крокодил Гена\"></option>\n" +
                // "                 <option value=\"Шапокляк\"></option>\n" +
                // "             </datalist>\n" +
                "        </div>";
            break;
        case "action":
            blockBody = "<select class=\"select-order-action\">\n" +
                "            <option>Включить</option>\n" +
                "            <option>Выключить</option>\n" +
                "            <option>Значение</option>\n" +
                "        </select>"
            break;
        case "value":
            blockBody = "<input class=\"control_command_value\" type=\"number\" name=\"type\"/>";
            break;
        case "if":
            blockBody = "<span>Если</span>";
            break;
        case "if-block" :
            blockBody = " <select class=\"select-order-action\">\n" +
                "             <option>Включить</option>\n" +
                "             <option>Выключить</option>\n" +
                "             <option>Значение</option>\n" +
                "         </select>";
            break;
        case "condition":
            blockBody = " <select class=\"select-order-action\">\n" +
                "             <option>&#62;</option>\n" +
                "             <option>&#60;</option>\n" +
                "             <option>&#62;=</option>\n" +
                "             <option>&#60;=</option>\n" +
                "             <option>=</option>\n" +
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