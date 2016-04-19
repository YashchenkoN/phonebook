/**
 * Created by kolyan on 19.04.16.
 */
showRecords('', '', '');
function showRecords(firstName, lastName, phone) {
    $.ajax({
        url: '/api/record/',
        contentType: 'application/json',
        dataType: 'json',
        type: 'GET',
        data: {
            firstname: firstName,
            lastname: lastName,
            phone: phone
        },
        success: function (data) {
            var response = JSON.stringify(data);
            console.log(response);
        },
        error: function (e) {
            console.log(e);
        }
    });
}