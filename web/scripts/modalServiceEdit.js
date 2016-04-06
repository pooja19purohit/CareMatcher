/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready( function() {
    //   Clear default text on textarea focus
    $("#service-text").focus( function() {
       //   Get the current textarea value
       var text = $("#service-text").val();
       //   Check against default text
       if ("Edit Biography Here.".localeCompare(text) === 0) {
           $("#service-text").val("");
       }
    });
    
    //  Replace default text if modal textarea is empty
    $("#service-text").blur( function() {
        //  Get the textarea value
        var text = $("#service-text").val();
        //  Check if blank
        if ("".localeCompare($.trim(text)) === 0) {
            $("#service-text").val("Edit Biography Here.");
        }
    });
    
    //  Update biography DOM when submit button is clicked
    $("#modal-submit").click( function() {
        //  Send the modal textarea value to the editProfile servlet to update DB
        ajaxDescripUpdate($("#service-text").val());
    });
});

function ajaxDescripUpdate(newtext) {
    var name = $("#serv-name").val();
    $.ajax({ 
        url: "editProfile", 
        type: "POST", 
        data: { "name":name, "descrip": newtext, "service": "description-update" }, 
        success: function() {
            $("#list-area").hide().fadeIn();
        }, 
        cache: false 
    });
}

