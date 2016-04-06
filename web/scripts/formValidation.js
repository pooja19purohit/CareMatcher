// Validate Form Fields
//	- Loop over all of the fields with the "form-action" HTML class
//	- Check the "type" attribute to determine the type of field
//	- For email fields, validate email syntax, return false if bad syntax found
//	- For password fields, vaidate password syntax, return false if bad syntax found
var checkFields = function() {
    clearErrors();
    var success = true;
    
    //  Check that the select user type field is set correctly
    if (!checkSelect())  {
        var errtext = document.getElementById("select-error");
        errtext.style.display = "inline";
        success = false;
    }
    
    //  Check the email field for syntax
    var email = document.getElementById("email-input");
    if (!validateEmails(email)) {
        var errtext = document.getElementById("email-error");
        errtext.style.display = "inline";
        success = false;
    }
    
    //  Check the password field & confirm field for syntax, then equality
    var pass = document.getElementById("password");
    if (!validatePassSyn(pass)) {
        var errtext = document.getElementById("pass-error");
        errtext.style.display = "inline";
        return false;
    }
    
    var conf = document.getElementById("conf-pass");
    if (!validatePassSyn(conf)) {
        var errtext = document.getElementById("conf-error");
        errtext.innerHTML = "Invalid password syntax.";
        errtext.style.display = "inline";
        success = false;
    }
    else if (pass.value.localeCompare(conf.value) !== 0) {
        var errtext = document.getElementById("conf-error");
        errtext.innerHTML = "Passwords do not match.";
        errtext.style.display = "inline";
        success = false;
    }
    
    return success;
};

var checkSelect = function() {
    //  Check that the userType selection is not set to the default option
    var s = document.getElementById("register-select");
    var selected = s.options[s.selectedIndex].value;
    if ("default".localeCompare(selected) === 0) return false;
    else return true;
};

// Validate email syntax:
//	- check that addresses contain "@" symbol
//	- check that length of substring before and after @ > 0
//	- check that substring after "@" contains a "."
var validateEmails = function(emailField) {
    //  If the value doesn't have an '@' with valid text on either side
    var parts = emailField.value.split("@");
    if (parts[1].indexOf(".") === -1) {
        alert("part after '@' in email address must have a '.' (ex: test@test.com)");
        return false;
    }

    //  If no conflicts found, return true
    return true;
};


// Valdiate password syntax
//	- check that the password is at least 6 characters, but less than 25
//	- check that at least 1 special character is used
//	- check that at least 1 upper-case letter is used
//	- check that at least 1 number is used
var validatePassSyn = function(passField) {
    //	Regex for allowable password formats
    var specRE = /[^\w\=\{\}\[\]\*]|_/g;
    var upperRE = /[A-Z]/g;
    var lowerRE = /[a-z]/g;
    var numRE = /[0-9]/g;

    //	Test password length
    if (passField.value.length < 6 || 
            passField.value.length > 25) 
    {
        alert("Password must be between 6 and 25 characters."); 
        return false;
    }

    //	Test password regex conditions
    else if (!specRE.test(passField.value)) {
        alert("Password must contain a valid special character:\n"
      + "\! \` \$ \@ \# \% \^ \_ \- \| \~ \. \: \\ \/ \+ \, \? \< \> \" \' \&");
        return false;
    }
    else if (!upperRE.test(passField.value)) {
        alert("Password must contain at least 1 upper case letter.");
        return false;
    }
    else if (!lowerRE.test(passField.value)) {
        alert("Password must contain at least 1 lower case letter.");
        return false;
    }      
    else if (!numRE.test(passField.value)) {
        alert("Password must contain at least 1 number 0-9.");
        return false;
    }

    //	If passed all conditions, return true
    return true;
};

var clearErrors = function() {
    var select = document.getElementById("select-error");
    var email = document.getElementById("email-error");
    var pass = document.getElementById("pass-error");
    var conf = document.getElementById("conf-error");
    
    select.style.display = "none";
    email.style.display = "none";
    pass.style.display = "none";
    conf.style.display = "none";
};