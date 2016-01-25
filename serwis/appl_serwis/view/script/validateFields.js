/**
 * Created by Miszal on 2015-12-25.
 */

    function validateForm()
    {

        var number = document.getElementById('phone').value;
        var len = number.length;
        if( document.getElementById('fname').value != '' &&
            document.getElementById('lname').value != '' &&
            document.getElementById('email').value != '' &&
            document.getElementById('city').value != '' &&
            document.getElementById('address').value != '' &&
            document.getElementById('zipc').value != '' &&
            isNaN(document.getElementById('phone').value) === false &&
            len == 9)
        {
            document.getElementById("edit").submit();
        }
        else
        {
            alert('Wprowadzono złe dane');
        }
    }
