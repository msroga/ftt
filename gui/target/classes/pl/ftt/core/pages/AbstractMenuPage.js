$.message_logout = "${message.logout}";
$.message_logout_info = "${message.logout.info}";
$.message_button_yes = "${message.button.yes}";
$.message_button_no = "${message.button.no}";

$(document).ready(function() {

    pageSetUp();
    var $loginForm = $('.login-form').validate({
        // Rules for form validation
        rules : {
            myapp_user : {
                required : true
            },
            myapp_password : {
                required : true
            }
        },

        // Messages for form validation
        messages : {
            myapp_user : {
                required : '${empty.login}'
            },
            myapp_password : {
                required : '${empty.password}'
            }
        },

        // Do not change code below
        errorPlacement : function(error, element) {
            error.insertAfter(element.parent());
        }
    });
})