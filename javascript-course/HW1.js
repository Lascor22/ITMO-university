"use strict";

/**
 * 8
 * Складывает два целых числа
 * @param {Number} a Первое целое
 * @param {Number} b Второе целое
 * @throws {TypeError} Когда в аргументы переданы не числа
 * @returns {Number} Сумма аргументов
 */
function abProblem(a, b) {
  if (typeof a !== "number" || typeof b !== "number") {
    throw new TypeError();
  }
  return a + b;
}

/**
 * 7
 * Определяет век по году
 * @param {Number} year Год, целое положительное число
 * @throws {TypeError} Когда в качестве года передано не число
 * @throws {RangeError} Когда год – отрицательное значение
 * @returns {Number} Век, полученный из года
 */
function centuryByYearProblem(year) {
  if (typeof year !== "number") {
    throw new TypeError();
  }
  if (year < 0 || !Number.isInteger(year)) {
    throw new RangeError();
  }
  let vek = Math.floor(year / 100) + 1;
  if (year % 100 === 0) {
    vek -= 1;
  }
  return vek;
}

/**
 * 7
 * Переводит цвет из формата HEX в формат RGB
 * @param {String} hexColor Цвет в формате HEX, например, '#FFFFFF'
 * @throws {TypeError} Когда цвет передан не строкой
 * @throws {RangeError} Когда значения цвета выходят за пределы допустимых
 * @returns {String} Цвет в формате RGB, например, '(255, 255, 255)'
 */
function colorsProblem(hexColor) {
  if (typeof hexColor !== "string") {
    throw new TypeError("Expected a valid hex string");
  }
  if (!/^#[0-9a-f]{6}$/i.test(hexColor)) {
    throw new RangeError();
  }
  hexColor = hexColor.slice(1);
  const r = parseInt(hexColor.slice(0, 2), 16);
  const g = parseInt(hexColor.slice(2, 4), 16);
  const b = parseInt(hexColor.slice(4, 6), 16);
  if ([r, g, b].filter((c) => c > 255 || c < 0).length > 0) {
    throw new RangeError();
  }
  return `(${r}, ${g}, ${b})`;
}

/**
 * 12
 * Находит n-ое число Фибоначчи
 * @param {Number} n Положение числа в ряде Фибоначчи
 * @throws {TypeError} Когда в качестве положения в ряде передано не число
 * @throws {RangeError} Когда положение в ряде не является целым положительным числом
 * @returns {Number} Число Фибоначчи, находящееся на n-ой позиции
 */
function fibonacciProblem(n) {
  if (typeof n !== "number") {
    throw new TypeError();
  }
  if (n < 1 || !Number.isInteger(n)) {
    throw new RangeError();
  }
  if (n < 3) {
    return 1;
  }
  const fibs = [1, 1];
  for (let i = 2; i < n; i++) {
    fibs.push(fibs[i - 1] + fibs[i - 2]);
  }
  return fibs[n - 1];
}

/**
 * 6
 * Транспонирует матрицу
 * @param {(Any[])[]} matrix Матрица размерности MxN
 * @throws {TypeError} Когда в функцию передаётся не двумерный массив
 * @returns {(Any[])[]} Транспонированная матрица размера NxM
 */
function matrixProblem(matrix) {
  if (!Array.isArray(matrix)) {
    throw new TypeError();
  }
  const result = [];
  const rows = matrix[0].length || 0;
  for (let j = 0; j < rows; j++) {
    const temp = [];
    for (let i = 0; i < matrix.length; i++) {
      if (!Array.isArray(matrix[i])) {
        throw new TypeError();
      }
      temp.push(matrix[i][j]);
    }
    result.push(temp);
  }
  return result;
}

/**
 * 11
 * Переводит число в другую систему счисления
 * @param {Number} n Число для перевода в другую систему счисления
 * @param {Number} targetNs Система счисления, в которую нужно перевести (Число от 2 до 36)
 * @throws {TypeError} Когда переданы аргументы некорректного типа
 * @throws {RangeError} Когда система счисления выходит за пределы значений [2, 36]
 * @returns {String} Число n в системе счисления targetNs
 */
function numberSystemProblem(n, targetNs) {
  if (typeof n !== "number" || typeof targetNs !== "number") {
    throw new TypeError();
  }
  if (targetNs < 2 || targetNs > 36) {
    throw new RangeError();
  }
  return n.toString(targetNs);
}

/**
 * 9
 * Проверяет соответствие телефонного номера формату
 * @param {String} phoneNumber Номер телефона в формате '8–800–xxx–xx–xx'
 * @throws {TypeError} Когда в качестве аргумента передаётся не строка
 * @returns {Boolean} Если соответствует формату, то true, а иначе false
 */
function phoneProblem(phoneNumber) {
  if (typeof phoneNumber !== "string") {
    throw new TypeError();
  }
  return phoneNumber.match(/^8-800-\d\d\d-\d\d-\d\d$/) !== null;
}

/**
 * Определяет количество улыбающихся смайликов в строке
 * @param {String} text Строка в которой производится поиск
 * @throws {TypeError} Когда в качестве аргумента передаётся не строка
 * @returns {Number} Количество улыбающихся смайликов в строке
 */
function smilesProblem(text) {
  if (typeof text !== "string") {
    throw new TypeError();
  }
  const arr = text.match(/(:-\)|\(-:)/g);
  return arr !== null ? arr.length : 0;
}

/**
 * Определяет победителя в игре "Крестики-нолики"
 * Тестами гарантируются корректные аргументы.
 * @param {(('x' | 'o')[])[]} field Игровое поле 3x3 завершённой игры
 * @returns {'x' | 'o' | 'draw'} Результат игры
 */
function ticTacToeProblem(field) {
  if (field[0][0] === field[0][1] && field[0][1] === field[0][2]) {
    return field[0][0];
  }
  if (field[1][0] === field[1][1] && field[1][1] === field[1][2]) {
    return field[1][0];
  }
  if (field[2][0] === field[2][1] && field[2][1] === field[2][2]) {
    return field[2][0];
  }
  if (field[0][0] === field[1][0] && field[1][0] === field[2][0]) {
    return field[0][0];
  }
  if (field[0][1] === field[1][1] && field[1][1] === field[2][1]) {
    return field[0][1];
  }
  if (field[0][2] === field[1][2] && field[1][2] === field[2][2]) {
    return field[0][2];
  }
  if (field[0][0] === field[1][1] && field[1][1] === field[2][2]) {
    return field[0][0];
  }
  if (field[0][2] === field[1][1] && field[1][1] === field[2][0]) {
    return field[0][2];
  }
  return "draw";
}

module.exports = {
  abProblem,
  centuryByYearProblem,
  colorsProblem,
  fibonacciProblem,
  matrixProblem,
  numberSystemProblem,
  phoneProblem,
  smilesProblem,
  ticTacToeProblem,
};
