/**
 * Created by kolyan on 19.04.16.
 */

$.ajax({
    url: '/api/record/',
    type: 'GET',
    success: function (data) {
        console.log(data);

        $('#records').jsGrid({
            width: "100%",
            //paging: true
            filtering: true,
            data: data,
            fields: [
                {name: "id", type: "number", filtering: false},
                {name: "firstName", type: "text"},
                {name: "lastName", type: "text"},
                {name: "patronymic", type: "text", filtering: false},
                {name: "mobilePhone", type: "text"},
                {name: "homePhone", type: "text", filtering: false},
                {name: "address", type: "text", filtering: false},
                {name: "email", type: "text", filtering: false}
            ],

            controller: {
                loadData: function(filter) {
                    console.log(filter);
                    return $.ajax({
                        url: '/api/record/',
                        type: 'GET',
                        data: {
                            firstname: filter.firstName,
                            lastname: filter.lastName,
                            phone: filter.mobilePhone
                        },
                        success: function(data) {
                            console.log('FILTER RESPONSE', data);
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
