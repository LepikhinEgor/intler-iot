function logicPageInit() {
    refreshLogicPageEventHandlers();
}

function refreshLogicPageEventHandlers() {
    $(".add_new_command").off('click');

    $(".add_new_command").on('click', addNewLocicCommand);
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

    console.log(+getLogicCommandBodyHtml(type));
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
                "             <datalist id=\"character\">\n" +
                "                 <option value=\"Чебурашка\"></option>\n" +
                "                 <option value=\"Крокодил Гена\"></option>\n" +
                "                 <option value=\"Шапокляк\"></option>\n" +
                "             </datalist>\n" +
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
console.log(blockBody);

    return blockBody;
}