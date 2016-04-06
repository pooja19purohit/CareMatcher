/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    var fields = getFields();
    setDefaults();
    
    if("for-me".localeCompare($('input[name=for-me]:checked', '#appt-form').val()) === 0) {
        dropFields(fields);
    }
});

function getFields() {
    var fields = [
        $("#fname-group"),
        $("#mi-group"),
        $("#lname-group"),
        $("#email-group")
    ];
    
    return fields;
}

function getInputs() {
    var inputs = [
        $("#inputFName"),
        $("#inputLName"),
        $("#inputMInit"),
        $("#inputEmail")
    ];
    
    return inputs;
}

$("#for-me-id").on("change", function() {
    var fields = getFields();
    resetDefaults();
    dropFields(fields);
});

$("#not-me-id").on("change", function() {
    var fields = getFields();
    dropFields(fields);
});

//  drop-out the specified fields if they are visible
function dropFields(fields) {
    var i;
    for (i = 0; i < fields.length; i++) {
        fields[i].toggle("drop");
    }
}

function setDefaults() {
    var inputs = getInputs();
    var i;
    for (i = 0; i < inputs.length; i++) {
        inputs[i].data('default', inputs[i].val());
    }
}

function resetDefaults() {
    var inputs = getInputs();
    var i;
    for (i = 0; i < inputs.length; i++) {
        inputs[i].val(inputs[i].data('default'));
    }
}
