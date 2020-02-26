<#ftl encoding="UTF-8"/>
<div id="logic-content">
    <h1>Управление</h1>
    <div class="logic-commands-wrap">
        <div class="logic-command">
            <label for="radio_1" class="choose-order-name ui-button ui-widget ui-checkboxradio-radio-label ui-checkboxradio-label ui-controlgroup-item">
                <span>Для</span>
                <input type="search" list="character">
                <datalist id="character">
                    <option value="Чебурашка"></option>
                    <option value="Крокодил Гена"></option>
                    <option value="Шапокляк"></option>
                </datalist>
            </label>
            <select class="select-order-action">
                <option>Включить</option>
                <option>Выключить</option>
                <option>Значение</option>
            </select>

            <label class="ui-controlgroup-label select-order-value"><input class="ui-spinner-input" type="number" name="type""/></label>
            <button id="apply">Apply</button>
        </div>
    </div>
</div>