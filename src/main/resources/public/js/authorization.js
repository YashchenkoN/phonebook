/**
 * @author Nikolay Yashchenko
 */
$(function() {

    $('#login-form-link').click(function(e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        $('#error-span').text('');
        e.preventDefault();
    });

    $('#register-form-link').click(function(e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        $('#error-span').text('');
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
                login: $('#login-reg').val(),
                password: $('#password-reg').val()
            }),
            success: function(data) {
                var response = data;
                console.log(response);
                if (response.erorrs == null || response.errors.length == 0) {
                    window.location = '/';
                } else {
                    console.log('!!!!!!!!');
                    $('#error-span').text('Error! Check inputs')
                }
            },
            error: function(e) {
                console.log(e);
                $('#error-span').text('Error! Check inputs')
            }
        })
    });

    $('#login-form').on('submit', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: $(this).attr('action'),
            data: $(this).serialize(),
            success: function(data) {
                var response = JSON.parse(data);
                console.log(response);
                if (response.success) {
                    window.location = '/';
                } else {
                    console.log('!!!!!!!!');
                    $('#error-span').text('Error! Check inputs')
                }
            },
            error: function(e) {
                console.log(e);
                $('#error-span').text('Error! Check inputs')
            }
        })
    });
});
