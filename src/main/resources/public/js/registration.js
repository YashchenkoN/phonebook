/**
 * Created by kolyan on 17.04.16.
 */
$(function() {

    $('#login-form-link').click(function(e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

    $('#register-form-link').click(function(e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

    $('#register-submit').on('click', function() {
        $.ajax({
            url: '/api/user/registration',
            contentType: 'application/json',
            dataType: 'json',
            type: 'POST',
            data: JSON.stringify({
                name: $('#name').val(),
                login: $('#login').val(),
                password: $('#password').val()
            }),
            success: function(data) {
                console.log(data);
            },
            error: function(e) {
                console.log(e);
            }
        })
    });
});
