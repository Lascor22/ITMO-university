"use strict";

/**
 * Телефонная книга
 */
const phoneBook = new Map()

/**
 * Вызывайте эту функцию, если есть синтаксическая ошибка в запросе
 * @param {number} lineNumber – номер строки с ошибкой
 * @param {number} charNumber – номер символа, с которого запрос стал ошибочным
 */
function syntaxError(lineNumber, charNumber) {
    throw new Error(
        `SyntaxError: Unexpected token at ${lineNumber + 1}:${charNumber + 1}`
    )
}

/**
 * commands
 */
const CREATE_CONTACT = 'Создай контакт '
const DELETE_CONTACT = 'Удали контакт '
const ADD_PHONE = 'Добавь телефон'
const ADD_EMAIL = 'Добавь почту'
const DELETE_PHONE = 'Удали телефон'
const DELETE_EMAIL = 'Удали почту'
const SELECT = 'Покажи'
const DELETE_WITH_SELECT = 'Удали контакты,'


/**
 * Выполнение запроса на языке pbQL
 * @param {string} query
 * @returns {string[]} - строки с результатами запроса
 */

function run(query) {
    let isEnd = false;
    const queries = query.split(';')
    const result = []
    for (let i = 0; i < queries.length; i++) {
        let q = queries[i]
        if (q === '' && i === (queries.length - 1)) {
            isEnd = true
            break
        }
        validateQuery(i, q)
        if (q.startsWith(CREATE_CONTACT)) {
            createContact(q)
        } else if (q.startsWith(DELETE_CONTACT)) {
            deleteContact(q)
        } else if (q.startsWith(ADD_PHONE) || q.startsWith(ADD_EMAIL)) {
            addPhoneAndEmail(q)
        } else if (q.startsWith(DELETE_PHONE) || q.startsWith(DELETE_EMAIL)) {
            deletePhoneAndEmail(q)
        } else if (q.startsWith(SELECT)) {
            result.push(...select(q))
        } else if (q.startsWith(DELETE_WITH_SELECT)) {
            deleteWithSelect(q)
        }
    }
    if (!isEnd) {
        syntaxError(queries.length - 1, queries[queries.length - 1].length)
    }
    return result
}



function validateQuery(i, query) {
    const T = {
        init: 'init',
        create: 'Создай',
        delete: 'Удали',
        add: 'Добавь',
        show: 'Покажи',
        contact: 'контакт',
        and: 'и',
        phone: 'телефон',
        emaily: 'почту',
        contactComma: 'контакты,',
        emails: 'почты',
        phones: 'телефоны',
        name: 'имя',
        phoneValue: 'phoneValue',
        emailValue: 'emailValue',
        for: 'для',
        where: 'где',
        contactovComma: 'контактов,',
        thereIs: 'есть',
        contacta: 'контакта',
        nameValue: 'nameValue',
        queryValue: 'queryValue'
    }
    const words = query.split(' ')
    let last = T.init
    let position = 0
    words.forEach(w => {
        if (last === T.nameValue || last === T.queryValue) {
            return
        }
        if (w === T.create && last === T.init) {
            last = T.create
            position += w.length + 1
            return
        }
        if (w === T.delete && last === T.init) {
            last = T.delete
            position += w.length + 1
            return
        }
        if (w === T.add && last === T.init) {
            last = T.add
            position += w.length + 1
            return
        }
        if (w === T.show && last === T.init) {
            last = T.show
            position += w.length + 1
            return
        }
        if (w === T.contact && (last === T.create || last === T.delete)) {
            last = T.contact
            position += w.length + 1
            return
        }
        if (w === T.phone && (last === T.add || last === T.delete || last === T.and)) {
            last = T.phone
            position += w.length + 1
            return
        }
        if (w === T.emaily && (last === T.add || last === T.delete || last === T.and)) {
            last = T.emaily
            position += w.length + 1
            return
        }
        if (w === T.contactComma && last === T.delete) {
            last = T.contactComma
            position += w.length + 1
            return
        }
        if (w === T.emails && (last === T.show || last === T.and)) {
            last = T.emails
            position += w.length + 1
            return
        }
        if (w === T.phones && (last === T.show || last === T.and)) {
            last = T.phones
            position += w.length + 1
            return
        }
        if (w === T.name && (last === T.show || last === T.and)) {
            last = T.name
            position += w.length + 1
            return
        }
        if (w === T.and &&
            (last === T.phones ||
                last === T.name ||
                last === T.emails ||
                last === T.emailValue ||
                last === T.phoneValue)) {
            last = T.and
            position += w.length + 1
            return
        }
        if (/^\d{10}$/.test(w) && last === T.phone) { // TYT MOZHET BIT' BAGA
            last = T.phoneValue
            position += w.length + 1
            return
        }
        if (/\b[^\s]+\b/.test(w) && last === T.emaily) { // TYT MOZHET BIT' BAGA
            last = T.emailValue
            position += w.length + 1
            return
        }
        if (w === T.for &&
            (last === T.emailValue ||
                last === T.phoneValue ||
                last === T.name ||
                last === T.emails ||
                last === T.phones)) {
            last = T.for
            position += w.length + 1
            return
        }
        if (w === T.where && (last === T.contactComma || last === T.contactovComma)) {
            last = T.where
            position += w.length + 1
            return
        }
        if (w === T.thereIs && last === T.where) {
            last = T.thereIs
            position += w.length + 1
            return
        }
        if (w === T.contacta && last === T.for) {
            last = T.contacta
            position += w.length + 1
            return
        }
        if (w === T.contactovComma && last === T.for) {
            last = T.contactovComma
            position += w.length + 1
            return
        }
        if (/[^;]*/.test(w) && (last === T.contact || last === T.contacta)) {
            last = T.nameValue
            position += w.length + 1
            return
        }
        if (/[^;]*/.test(w) && last === T.thereIs) {
            last = T.queryValue
            position += w.length + 1
            return
        }
        syntaxError(i, position)
    })
}

/** 
 * Создай контакт <имя>
 * Удали контакт <имя>
 * Добавь (телефон <телефон>|почту <почта>)( и телефон <телефон>| и почту <почта>)* для контакта <имя>
 * Удали (телефон <телефон>|почту <почта>)( и телефон <телефон>| и почту <почта>)* для контакта <имя>
 * Покажи (почты|телефоны|имя)( и телефоны| и имя| и почты)* для контактов, где есть <запрос>
 * Удали контакты, где есть <запрос>
*/


function createContact(query) {
    const name = query.slice(CREATE_CONTACT.length)
    if (!phoneBook.has(name)) {
        phoneBook.set(name, { phones: [], emails: [] })
    }
}

function deleteContact(query) {
    const name = query.slice(DELETE_CONTACT.length)
    phoneBook.delete(name)
}

function addPhoneAndEmail(query) {
    const name = query.match(/для контакта [^;]+/)[0].slice('для контакта '.length)
    if (!phoneBook.has(name)) {
        return;
    }
    const phones = query.includes('телефон') ? query.match(/телефон \d{10}/g).map(p => p.slice('телефон '.length)) : []
    const emails = query.includes('почту') ? query.match(/почту [^\ ]+/g).map(p => p.slice('почту '.length)) : []
    phones.forEach(phone => {
        if (phoneBook.get(name).phones.indexOf(phone) === -1) {
            phoneBook.get(name).phones.push(phone)
        }
    })
    emails.forEach(email => {
        if (phoneBook.get(name).emails.indexOf(email) === -1) {
            phoneBook.get(name).emails.push(email)
        }
    })
}

function deletePhoneAndEmail(query) {
    const name = query.match(/для контакта [^;]+/)[0].slice('для контакта '.length)
    if (!phoneBook.has(name)) {
        return;
    }
    const phones = query.includes('телефон') ? query.match(/телефон \d{10}/g).map(p => p.slice('телефон '.length)) : []
    const emails = query.includes('почту') ? query.match(/почту [^\ ]+/g).map(p => p.slice('почту '.length)) : []
    phones.forEach(phone => {
        phoneBook.get(name).phones = phoneBook.get(name).phones.filter(e => e !== phone)
    })
    emails.forEach(email => {
        phoneBook.get(name).emails = phoneBook.get(name).emails.filter(e => e !== email)
    })
}

function select(query) {
    if (!query.includes('почты') && !query.includes('телефоны') && !query.includes('имя')) {
        return []
    }
    const substr = query.match(/где есть [^;]*/)[0].slice('где есть '.length)
    if (substr.length === 0) {
        return []
    }
    const tokens = tokininze(query)
    const contacts = []
    phoneBook.forEach((pAndE, name) => {
        const inName = name.includes(substr)
        const inPhones = pAndE.phones.reduce((res, curr) => res || curr.includes(substr), false)
        const inEmails = pAndE.emails.reduce((res, curr) => res || curr.includes(substr), false)
        if (inName || inPhones || inEmails) {
            contacts.push(name)
        }
    })
    const result = []
    contacts.forEach(name => {
        const answer = []
        tokens.forEach(token => {
            switch (token) {
                case 'n':
                    answer.push(name)
                    break
                case 't':
                    answer.push(phoneBook
                        .get(name)
                        .phones
                        .map(p => `+7 (${p.slice(0, 3)}) ${p.slice(3, 6)}-${p.slice(6, 8)}-${p.slice(8, 10)}`)
                        .join(','))
                    break
                case 'e':
                    answer.push(phoneBook.get(name).emails.join(','))
                    break
                default:
                    break
            }
        })
        result.push(answer.join(';'))
    })
    return result
}

function deleteWithSelect(query) {
    const substr = query.match(/где есть [^;]*/)[0].slice('где есть '.length)
    if (substr.length === 0) {
        return
    }
    const contacts = []
    phoneBook.forEach((pAndE, name) => {
        const inName = name.includes(substr)
        const inPhones = pAndE.phones.reduce((res, curr) => res || curr.includes(substr), false)
        const inEmails = pAndE.emails.reduce((res, curr) => res || curr.includes(substr), false)
        if (inName || inPhones || inEmails) {
            contacts.push(name)
        }
    })
    contacts.forEach(e => phoneBook.delete(e))
}

function tokininze(query) {
    const words = query.split(' ').filter(w => w === 'почты' || w === 'телефоны' || w === 'имя')
    return words.map(w => {
        switch (w) {
            case 'почты':
                return 'e'
            case 'телефоны':
                return 't'
            case 'имя':
                return 'n'
            default:
                return null
        }
    })
}

module.exports = { phoneBook, run };
