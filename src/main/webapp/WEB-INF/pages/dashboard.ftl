<#ftl encoding="UTF-8"/>
<div>
    <h1>Панель приборов</h1></div>
<div class="widgets-wrap">
    <div id="add_new_widget">
        <img class="add_widget_img" src="./resources/images/add-widget.png">
    </div>
</div>

<div id="widget_modal_window">
    <div id="widget_window">
        <div id="widget_window_content">
            <form>
                <p class="modal-field-name">Имя:</p>
                <input type="text" class="input_widget_name">
                <p class="modal-field-name">Мера:</p>
                <input type="text" class="input_widget_measure">
                <p class="modal-field-name">Ключевое слово:</p>
                <input type="text" class="input_widget_keyward">
                <p class="modal-field-name">Устройство:</p>
                <input type="text" class="input_widget_deviceName">
                <p class="modal-field-name">Тип:</p>
                <select class="choose-type-menu">
                    <option value="0">Цифра</option>
                    <option value="1">Переключатель</option>
                    <option value="2">Слайдер</option>
                </select>
                <table class="widget-custom-ui">
                    <tr>
                        <td><span class="modal-field-name">Цвет:</span></td>
                        <td><span class="modal-field-name">Иконка:</span></td>
                    </tr>
                    <tr>
                        <td><select class="choose-color-menu">
                                <option>Черный</option>
                                <option>Красный</option>
                                <option>Синий</option>
                                <option>Зеленый</option>
                                <option>Желтый</option>
                                <option>Бирюзовый</option>
                                <option>Фиолетовый</option>
                            </select></td>
                        <td><select class="choose-icon-menu">
                                <option>Нет</option>
                                <option>Внимание</option>
                                <option>Угол</option>
                                <option>Огонь</option>
                                <option>Цветок</option>
                                <option>Длина</option>
                                <option>Молния</option>
                                <option>Дождь</option>
                                <option>Скорость</option>
                                <option>Солнце</option>
                                <option>Температура</option>
                                <option>Вода</option>
                            </select></td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="modal_buttons">
            <a href="#" class="close_modal_widget">close</a>
            <a href="#" class="apply_modal_widget">apply</a>
        </div>
    </div>
</div>
