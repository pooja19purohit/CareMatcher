/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//  Modify Registration Page for specific user types
$("#type-select").change(function() {
    var type = $("#type-select").val();

    var allFields = getAllFields();
    var custFields = getCustFields();
    var provFields = getProvFields();
    var pracFields = getPracFields();

    //	Modify registration page for customer fields
    if ("customer".localeCompare(type) === 0) {
        var fields = [$("#exp-group"), $("#avail-group")];
        dropFields(fields);
        fadeinFields(custFields);
        if ('Practice Name'.localeCompare($("#first-name-label").html()) === 0) {
            $("#first-name-label").fadeOut('slow', function() {
                $("#first-name-label").html('First Name');
                $("#inputFName").attr("placeholder", "First Name");
            });
            $("#first-name-label").fadeIn();
        }
    }
    //	Modify registration page for provider fields
    else if ("provider".localeCompare(type) === 0) {
        var fields = [$("#dob-group")];
        dropFields(fields);
        fadeinFields(provFields);
        if ('Practice Name'.localeCompare($("#first-name-label").html()) === 0) {
            $("#first-name-label").fadeOut('slow', function() {
                $("#first-name-label").html('First Name');
                $("#inputFName").attr("placeholder", "First Name:");
            });
            $("#first-name-label").fadeIn();
        }
    }
    //	Modify registration page for practice fields
    else if ("practice".localeCompare(type) === 0) {
        var fields = [$("#mi-group"), $("#lname-group"),
                      $("#dob-group"), $("#exp-group"),
                      $("#avail-group")];
        dropFields(fields);
        fadeinFields(pracFields);
        $("#first-name-label").fadeOut('slow', function() {
            $("#first-name-label").html('Practice Name');
            $("#inputFName").attr("placeholder", "Practice Name:");
        });
        $("#first-name-label").fadeIn();
    }
    //	If default option selected, remove all fields
    else {
        dropFields(allFields);
    }
});

//	Get an array of all of the section div Ids
function getAllFields() {
	
    var allFields = [
        $("#fname-group"),
        $("#mi-group"),
        $("#lname-group"),
        $("#dob-group"),
        $("#email-group"),
        $("#pass-group"),
        $("#conf-group"),
        $("#exp-group"),
        $("#avail-group"),
        $("#addr1-group"),
        $("#addr2-group"),
        $("#city-group"),
        $("#st-group"),
        $("#zip-group"),
        $("#country-group"),
        $("#phone-group"),
        $("#submit-group")
    ];

    return allFields;
}

//	Get an array of all of the section div Ids for Customer elements
function getCustFields() {
	
    var custFields = [
        $("#fname-group"),
        $("#mi-group"),
        $("#lname-group"),
        $("#dob-group"),
        $("#email-group"),
        $("#pass-group"),
        $("#conf-group"),
        $("#addr1-group"),
        $("#addr2-group"),
        $("#city-group"),
        $("#st-group"),
        $("#zip-group"),
        $("#country-group"),
        $("#phone-group"),
        $("#submit-group")
    ];

    return custFields;

}

//	Get an array of all of the section div Ids for Provider elements
function getProvFields() {

    var provFields = [
        $("#fname-group"),
        $("#mi-group"),
        $("#lname-group"),
        $("#email-group"),
        $("#pass-group"),
        $("#conf-group"),
        $("#exp-group"),
        $("#avail-group"),
        $("#addr1-group"),
        $("#addr2-group"),
        $("#city-group"),
        $("#st-group"),
        $("#zip-group"),
        $("#country-group"),
        $("#phone-group"),
        $("#submit-group")
    ];

    return provFields;
}

//	Get an array of all of the section div Ids for Practice elements
function getPracFields() {

    var pracFields = [
        $("#fname-group"),
        $("#email-group"),
        $("#pass-group"),
        $("#conf-group"),
        $("#addr1-group"),
        $("#addr2-group"),
        $("#city-group"),
        $("#st-group"),
        $("#zip-group"),
        $("#country-group"),
        $("#phone-group"),
        $("#submit-group")
    ];

    return pracFields;
}

//	drop-out the specified fields if they are visible
function dropFields(fields) {
    var i;
    for (i = 0; i < fields.length; i++) {
        if (fields[i].is(":visible")) {
            fields[i].hide("drop");
        }
    }
}

//	fade-in the specified fields
function fadeinFields(fields) {
    var i;
    for (i = 0; i < fields.length; i++) {
        if (fields[i].is(":hidden")) {
            fields[i].show("fade");
        }
    }
}

