window.notify = function (message) {
    $.notify(message, {position: "bottom right"})
};

ajax = function (data, success) {
    return $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data: data,
        success: function (response) {
            if (response["redirect"]) {
                location.href = response["redirect"];
            } else {
                success(response);
            }
        }
    })
};

var findBy = function (list, key, value) {
    var result = null;
    list.forEach(function (item) {
        if (item[key] === value) {
            result = item;
        }
    });
    return result;
};
