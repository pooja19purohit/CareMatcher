/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready( function() {
    //   Clear default text on textarea focus
    $("#bio-text").focus( function() {
       //   Get the current textarea value
       var text = $("#bio-text").val();
       //   Check against default text
       if ("Edit Biography Here.".localeCompare(text) === 0) {
           $("#bio-text").val("");
       }
    });
    
    //  Replace default text if modal textarea is empty
    $("#bio-text").blur( function() {
        //  Get the textarea value
        var text = $("#bio-text").val();
        //  Check if blank
        if ("".localeCompare($.trim(text)) === 0) {
            $("#bio-text").val("Edit Biography Here.");
        }
    });
    
    //  Update biography DOM when submit button is clicked
    $("#modal-submit").click( function() {
        //  Send the modal textarea value to the editProfile servlet to update DB
        ajaxBioUpdate($("#bio-text").val());
        $("#bio-area").text("Processing...");
    });
});

function ajaxBioUpdate(newtext) {
    $.ajax({ 
        url: "editProfile", 
        type: "POST", 
        data: { "bio": newtext, "service": "bio-update" }, 
        success: function() { $("#bio-area").text(newtext); }, 
        error: function() { alert("Error setting new bio information."); },
        cache: false 
    });
}
