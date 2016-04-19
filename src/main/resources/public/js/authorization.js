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
                var response = JSON.parse(data);
                console.log(response);
                if (response.erorrs.length == 0) {
                    window.location = '/';
                } else {
                    console.log('!!!!!!!!');
                }
            },
            error: function(e) {
                console.log(e);
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
                }
            },
            error: function(e) {
                console.log(e);
            }
        })
    });

    $('#register-form').validate({
        framework: 'bootstrap',
        fields: {
            login: {
                validators: {
                    notEmpty: {
                        message: 'The username is required'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The username must be more than 6 and less than 30 characters long'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z]+$/,
                        message: 'The username can only consist of alphabetical, number, dot and underscore'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    }
                }
            }
        }
    });
});
