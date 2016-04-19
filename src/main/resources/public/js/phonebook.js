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
            data.objects.forEach(function(item) {
                $('#records').append(
                    '<tr>' +
                        '<td>' + item.id + '</td>' +
                        '<td>' + item.firstName + '</td>' +
                        '<td>' + item.lastName + '</td>' +
                        '<td>' + item.patronymic + '</td>' +
                        '<td>' + item.mobilePhone + '</td>' +
                        '<td>' + (item.homePhone != null ? item.homePhone : '') + '</td>' +
                        '<td>' + (item.address != null ? item.address : '') + '</td>' +
                        '<td>' + (item.email != null ? item.email : '') + '</td>' +
                    '</tr>'
                )
            });
        },
        error: function (e) {
            console.log(e);
        }
    });
}