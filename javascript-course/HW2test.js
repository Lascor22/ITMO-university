const pbql = require('./HW2.js');
const assert = require('assert');

describe('pbql.run', () => {
    beforeEach(() => {
        pbql.phoneBook.clear();
    });
    it('FirstTest', () => {
        assert.strictEqual(true, true);
    });
    it('Пример 1', () => {
        let query =
            'Покажи имя для контактов, где есть ий;';
        let ans =
            []
        assert.strictEqual(JSON.stringify(pbql.run(query)), JSON.stringify(ans));
    });
    it('Пример 2', () => {
        let query =
            'Создай контакт Григорий;' +
            'Создай контакт Василий;' +
            'Создай контакт Иннокентий;' +
            'Покажи имя для контактов, где есть ий;';
        let ans =
            [
                'Григорий',
                'Василий',
                'Иннокентий'
            ]
        assert.strictEqual(JSON.stringify(pbql.run(query)), JSON.stringify(ans));
    });
    it('Пример 3', () => {
        let query =
            'Создай контакт Григорий;' +
            'Создай контакт Василий;' +
            'Создай контакт Иннокентий;' +
            'Покажи имя и имя и имя для контактов, где есть ий;';
        let ans =
            [
                'Григорий;Григорий;Григорий',
                'Василий;Василий;Василий',
                'Иннокентий;Иннокентий;Иннокентий'
            ]
        assert.strictEqual(JSON.stringify(pbql.run(query)), JSON.stringify(ans));
    });
    it('Пример 4', () => {
        let query =
            'Создай контакт Григорий;' +
            'Покажи имя для контактов, где есть ий;' +
            'Покажи имя для контактов, где есть ий;';
        let ans =
            [
                'Григорий',
                'Григорий'
            ]
        assert.strictEqual(JSON.stringify(pbql.run(query)), JSON.stringify(ans));
    });
    it('Пример 5', () => {
        let query =
            'Создай контакт Григорий;' +
            'Удали контакт Григорий;' +
            'Покажи имя для контактов, где есть ий;';
        let ans =
            []
        assert.strictEqual(JSON.stringify(pbql.run(query)), JSON.stringify(ans));
    });
    it('Пример 6', () => {
        let query =
            'Создай контакт Григорий;' +
            'Добавь телефон 5556667787 для контакта Григорий;' +
            'Добавь телефон 5556667788 и почту grisha@example.com для контакта Григорий;' +
            'Покажи имя и телефоны и почты для контактов, где есть ий;';
        let ans =
            [
                'Григорий;+7 (555) 666-77-87,+7 (555) 666-77-88;grisha@example.com'
            ]
        assert.strictEqual(JSON.stringify(pbql.run(query)), JSON.stringify(ans));
    });
    it('Пример 7', () => {
        let query =
            'Создай контакт Григорий;' +
            'Добавь телефон 5556667788 для контакта Григорий;' +
            'Удали телефон 5556667788 для контакта Григорий;' +
            'Покажи имя и телефоны для контактов, где есть ий;';
        let ans =
            [
                'Григорий;'
            ]
        assert.strictEqual(JSON.stringify(pbql.run(query)), JSON.stringify(ans));
    });
    it('SyntaxError Пример 1', () => {
        let query =
            'покажи имя для контактов, где есть Гр;';
        assert.throws(() => pbql.run(query), /SyntaxError: Unexpected token at 1:1/);
    });
    it('SyntaxError Пример 2', () => {
        let query =
            'Покжи имя для контактов, где есть Гр;';
        assert.throws(() => pbql.run(query), /SyntaxError: Unexpected token at 1:1/);
    });
    it('SyntaxError Пример 3', () => {
        let query =
            'Покажи  имя для контактов, где есть Гр;';
        assert.throws(() => pbql.run(query), /SyntaxError: Unexpected token at 1:8/);
    });

    it('SyntaxError Пример 4', () => {
        let query =
            'Удали телефон 55566677 для контакта Григорий;';
        assert.throws(() => pbql.run(query), /SyntaxError: Unexpected token at 1:15/);
    });

    it('SyntaxError Пример 5', () => {
        let query =
            'Покажи имя для контактов, где есть Гр';
        assert.throws(() => pbql.run(query), /SyntaxError: Unexpected token at 1:38/);
    });

    it('Покажи ничего', () => {
        let query =
            'Создай контакт Создай контакт;' +
            'Покажи для контактов, где есть Создай;';
        assert.throws(() => pbql.run(query), /SyntaxError: Unexpected token at 2:8/);
    });

    it('Добавь и телефон и почту', () => {
        let query =
            'Создай контакт Биба;' +
            'Добавь и телефон 1234567890 и почту ok@ok для контакта Биба;';
        assert.throws(() => pbql.run(query), /SyntaxError: Unexpected token at 2:8/);
    });

    it('Добавь телефон и почту и', () => {
        let query =
            'Создай контакт Биба;' +
            'Добавь телефон 1234567890 и почту ok@ok и для контакта Биба;';
        assert.throws(() => pbql.run(query), /SyntaxError: Unexpected token at 2:43/);
    });

    it('должен возвращать пустой массив, если контактов нет', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Покажи имя для контактов, где есть ий;'
            ),
            []
        );
    });

    it('должен находить контакты по запросу "ий"', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Создай контакт Василий;' +
                'Создай контакт Иннокентий;' +
                'Покажи имя для контактов, где есть ий;'
            ),
            [
                'Григорий',
                'Василий',
                'Иннокентий'
            ]
        );
    });

    it('должен выводить поля произвольное количество раз', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Создай контакт Василий;' +
                'Создай контакт Иннокентий;' +
                'Покажи имя и имя и имя для контактов, где есть ий;'
            ),
            [
                'Григорий;Григорий;Григорий',
                'Василий;Василий;Василий',
                'Иннокентий;Иннокентий;Иннокентий'
            ]
        );
    });

    it('должен конкатенировать результаты', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Покажи имя для контактов, где есть ий;' +
                'Покажи имя для контактов, где есть ий;'
            ),
            [
                'Григорий',
                'Григорий'
            ]
        );
    });

    it('должен удалять контакт по имени', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Удали контакт Григорий;' +
                'Покажи имя для контактов, где есть ий;'
            ),
            []
        );
    });

    it('должен добавлять телефон и почту для контакта', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Добавь телефон 5556667787 для контакта Григорий;' +
                'Добавь телефон 5556667788 и почту grisha@example.com для контакта Григорий;' +
                'Покажи имя и телефоны и почты для контактов, где есть ий;'
            ),
            [
                'Григорий;+7 (555) 666-77-87,+7 (555) 666-77-88;grisha@example.com'
            ]
        );
    });

    it('должен добавлять составные имена и уметь их искать', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий Васильевич;' +
                'Покажи имя для контактов, где есть ий Васил;'
            ),
            [
                'Григорий Васильевич'
            ]
        );
    });


    it('должен удалять телефон контакта', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Добавь телефон 5556667788 для контакта Григорий;' +
                'Удали телефон 5556667788 для контакта Григорий;' +
                'Покажи имя и телефоны для контактов, где есть ий;'
            ),
            [
                'Григорий;'
            ]
        );
    });

    it('должен удалять все контакты по запросу "ий"', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Создай контакт Василий;' +
                'Создай контакт Иннокентий;' +
                'Удали контакты, где есть ий;' +
                'Покажи имя для контактов, где есть ий;'
            ),
            []
        );
    });

    it('должен проверять регистр команд', () => {
        assert.throws(() =>
            pbql.run(
                'покажи имя для контактов, где есть Гр;'
            ),
            /at 1:1$/
        );
    });

    it('должен проверять опечатки в командах', () => {
        assert.throws(() =>
            pbql.run(
                'Покжи имя для контактов, где есть Гр;'
            ),
            /at 1:1$/
        );
    });

    it('пустая команда', () => {
        assert.deepStrictEqual(
            pbql.run(
                ''
            ),
            []
        );
    });


    it('должен показать пустой запрос', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Покажи имя для контактов, где есть ;'
            ),
            []
        );
    });

    it('должен показать с запросом который будет пуст', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Покажи имя для контактов, где есть фблфыв;'
            ),
            []
        );
    });

    it('должен Удалить ничего', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Удали контакты, где есть фблфыв;' +
                'Покажи имя для контактов, где есть ий;'
            ),
            [
                'Григорий'
            ]
        );
    });

    it('должен удалить пустой запрос', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Удали контакты, где есть ;' +
                'Покажи имя для контактов, где есть ий;'

            ),
            [
                'Григорий'
            ]
        );
    });

    it('должен проверять наличие лишних пробелов', () => {
        assert.throws(() =>
            pbql.run(
                'Покажи  имя для контактов, где есть Гр;'
            ),
            /at 1:8$/
        );
    });

    it('должен проверять неправильные команды', () => {
        assert.throws(() =>
            pbql.run(
                ' '
            ),
            /at 1:1$/
        );
    });

    it('должен проверять правильность формата номеров', () => {
        assert.throws(() =>
            pbql.run(
                'Удали телефон 55566677 для контакта Григорий;'
            ),
            /at 1:15$/
        );
    });

    it('должен находить ошибку во второй команде', () => {
        assert.throws(() =>
            pbql.run(
                'Покажи имя для контактов, где есть ий;' +
                'Say my name для контактов, где есть W;'
            ),
            /at 2:1$/
        );
    });

    it('должен проверять наличие ;', () => {
        assert.throws(() =>
            pbql.run(
                'Покажи имя для контактов, где есть Гр'
            ),
            /at 1:38$/
        );
    });


    it('должен быть регистрозависимым 1', () => {
        assert.throws(() =>
            pbql.run(
                'покажи имя для контактов, где есть Гр'
            ),
            /at 1:1$/
        );
    });

    it('должен проверять наличие ; не только в конце первой строки', () => {
        assert.throws(() =>
            pbql.run(
                'Создай контакт Григорий;' +
                'Создай контакт Василий'
            ),
            /at 2:23/
        );
    });

    it('Удали контакт [empty]', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий;' +
                'Удали контакт ;' +
                'Покажи имя для контактов, где есть ий;'
            ),
            [
                'Григорий'
            ]
        );
    });

    it('Поиск по multiple whitespace', () => {
        assert.deepStrictEqual(
            pbql.run(
                'Создай контакт Григорий   B;' +
                'Покажи имя для контактов, где есть    ;'
            ),
            [
                'Григорий   B'
            ]
        );
    });
});