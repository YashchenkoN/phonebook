/**
 * Created by kolyan on 19.04.16.
 */
showRecords('', '', '');
function showRecords(firstName, lastName, phone) {
    $.ajax({
        url: '/api/record/',
        type: 'GET',
        data: {
            firstname: firstName,
            lastname: lastName,
            phone: phone
        },
        success: function (data) {
            console.log(data);

            $('#records').jsGrid({
                width: "100%",
                inserting: true,
                editing: true,
                //paging: true
                data: data.objects,
                fields: [
                    {name : "id", type : "number", editing : false},
                    {name : "firstName", type : "text", validate : "required"},
                    {name : "lastName", type : "text", validate : "required"},
                    {name : "patronymic", type : "text", validate : "required"},
                    {name : "mobilePhone", type : "text", validate : "required"},
                    {name : "homePhone", type : "text"},
                    {name : "address", type : "text"},
                    {name : "email", type : "text"},
                    {
                        type : "control",
                        editButton : true,
                        modeSwitchButton : true,
                        deleteButton : true
                    }
                ],

                controller: {
                    insertItem: function(item) {
                        item.id = null;
                        item.userId = window.userId;
                        console.log('INSERT', item);
                        $.ajax({
                            url: '/api/record/',
                            contentType: 'application/json',
                            dataType: 'json',
                            type: 'POST',
                            data: JSON.stringify(item),
                            success: function(data) {
                                console.log('INSERT RESPONSE', data);
                                return data.object;
                            }
                        });
                    },

                    updateItem: function(item) {
                        item.userId = window.userId;
                        console.log('UPDATE', item);
                        $.ajax({
                            url: '/api/record/' + item.id,
                            contentType: 'application/json',
                            dataType: 'json',
                            type: 'PUT',
                            data: JSON.stringify(item),
                            success: function(data) {
                                console.log('UPDATE RESPONSE', data);
                                return data.object;
                            }
                        });
                    },

                    deleteItem: function(item) {
                        item.userId = window.userId;
                        console.log(item);
                        $.ajax({
                            url: '/api/record/' + item.id,
                            contentType: 'application/json',
                            dataType: 'json',
                            type: 'DELETE',
                            success: function(data) {
                                console.log('DELETE RESPONSE', data);
                                return data;
                            }
                        });
                    }
                }

            })
        },
        error: function (e) {
            console.log(e);
        }
    });
}