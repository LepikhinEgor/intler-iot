<#ftl encoding="UTF-8"/>
<div id="logic-content">
    <h1>Управление</h1>
    <div class="logic-commands-wrap">
        <div class="control_command">
            <div class="control_command_block select_command_name">
                <table class="control_command_table">
                    <tr><td class="control_command_margin"></td></tr>
                    <tr><td align="center" class="control_command_body">
                            <div>Для
                            <input type="search" list="character">
                            <datalist id="character">
                                <option value="Чебурашка"></option>
                                <option value="Крокодил Гена"></option>
                                <option value="Шапокляк"></option>
                            </datalist>
                            </div>
                        </td></tr>
                    <tr><td class="control_command_margin"></td></tr>
                </table>
            </div>
            <div class="control_command_block select_command_action">
                <table class="control_command_table">
                    <tr><td class="control_command_margin"></td></tr>
                    <tr><td align="center" class="control_command_body">
                            <select class="select-order-action">
                                <option>Включить</option>
                                <option>Выключить</option>
                                <option>Значение</option>
                            </select>
                        </td></tr>
                    <tr><td class="control_command_margin"></td></tr>
                </table>
            </div>
            <div class="control_command_block">
                <table class="control_command_table">
                    <tr><td class="control_command_margin"></td></tr>
                    <tr><td align="center" class="control_command_body">
                            <input class="control_command_value" type="number" name="type"/>
                        </td></tr>
                    <tr><td class="control_command_margin"></td></tr>
                </table>
            </div>
            <div class="control_command_block if-block">
                <table class="control_command_table">
                    <tr><td class="control_command_margin"></td></tr>
                    <tr><td align="center" class="control_command_body">
                            <span>Если</span>
                        </td></tr>
                    <tr><td class="control_command_margin"></td></tr>
                </table>
            </div>
            <div class="control_command_block if-object">
                <table class="control_command_table">
                    <tr><td class="control_command_margin"></td></tr>
                    <tr><td align="center" class="control_command_body">
                            <select class="select-order-action">
                                <option>Включить</option>
                                <option>Выключить</option>
                                <option>Значение</option>
                            </select>
                        </td></tr>
                    <tr><td class="control_command_margin"></td></tr>
                </table>
            </div>
            <div class="control_command_block condition-block">
                <table class="control_command_table">
                    <tr><td class="control_command_margin"></td></tr>
                    <tr><td align="center" class="control_command_body">
                            <select class="select-order-action">
                                <option>&#62;</option>
                                <option>&#60;</option>
                                <option>&#62;=</option>
                                <option>&#60;=</option>
                                <option>=</option>
                            </select>
                        </td></tr>
                    <tr><td class="control_command_margin"></td></tr>
                </table>
            </div>
            <div class="control_command_block ">
                <table class="control_command_table">
                    <tr><td class="control_command_margin"></td></tr>
                    <tr><td align="center" class="control_command_body">
                            <input class="control_command_value" type="number" name="type"/>
                        </td></tr>
                    <tr><td class="control_command_margin"></td></tr>
                </table>
            </div>
            <div class="control_command_block confirm-command-button">
                <table class="control_command_table">
                    <tr><td class="control_command_margin"></td></tr>
                    <tr><td align="center" class="control_command_body">
                            <span>Готово</span>
                        </td></tr>
                    <tr><td class="control_command_margin"></td></tr>
                </table>
            </div>
            <div class="add_new_command" onmouseover="$('.add_command_img').css('background',' #5aaf5a'); console.log('gtrgt')" onmouseout="$('.add_command_img').css('background','green')">
                <img class="add_command_img" src="./resources/images/add-widget.png">
            </div>
        </div>
    </div>
</div>