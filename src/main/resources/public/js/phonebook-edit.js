/**
 * Created by kolyan on 19.04.16.
 */
//jsGrid.validators.phone = {
//    validator: function(value, item) {
//        return /\\+\\d{3}\\(\\d{2}\\)\\d{7}/.test(value);
//    }
//};

$.ajax({
    url: '/api/record/',
    type: 'GET',
    success: function (data) {
        console.log(data);

        $('#records').jsGrid({
            width: "100%",
            inserting: true,
            editing: true,
            //paging: true
            data: data,
            fields: [
                {name: "id", type: "number", editing: false},
                {name: "firstName", type: "text", validate: "required"},
                {name: "lastName", type: "text", validate: "required"},
                {name: "patronymic", type: "text", validate: "required"},
                {name: "mobilePhone", type: "text", validate: "required"},
                {name: "homePhone", type: "text"},
                {name: "address", type: "text"},
                {name: "email", type: "text"},
                {
                    type: "control",
                    editButton: true,
                    modeSwitchButton: true,
                    deleteButton: true
                }
            ],

            controller: {
                insertItem: function (item) {
                    item.id = null;
                    item.userId = window.userId;
                    console.log('INSERT', item);
                    return $.ajax({
                        url: '/api/record/',
                        contentType: 'application/json',
                        dataType: 'json',
                        type: 'POST',
                        data: JSON.stringify(item),
                        success: function (data) {
                            console.log('INSERT RESPONSE', data);
                        }
                    });
                },

                updateItem: function (item) {
                    item.userId = window.userId;
                    console.log('UPDATE', item);
                    return $.ajax({
                        url: '/api/record/' + item.id,
                        contentType: 'application/json',
                        dataType: 'json',
                        type: 'PUT',
                        data: JSON.stringify(item),
                        success: function (data) {
                            console.log('UPDATE RESPONSE', data);
                        }
                    });
                },

                deleteItem: function (item) {
                    item.userId = window.userId;
                    console.log(item);
                    $.ajax({
                        url: '/api/record/' + item.id,
                        contentType: 'application/json',
                        dataType: 'json',
                        type: 'DELETE',
                        success: function (data) {
                            console.log('DELETE RESPONSE', data);
                        }
                    });
                    return item;
                }
            }

        })
    },
    error: function (e) {
        console.log(e);
    }
});