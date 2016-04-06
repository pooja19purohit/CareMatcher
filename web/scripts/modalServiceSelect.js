/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
    $("#service-search").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "editProfile",
                data: {"query":request.term, "service":"service-search"},
                success: function (data) {
                    var services = data.split(":");
                    response(services);
                }
            });
        },
        select: function(event, ui) {
            event.preventDefault();
            $("#service-search").val("");
            $("#added").append("<li class='li-serv'>" + ui.item.label + "</li>");
            
        }
    });
    
    $('#service-search').keypress(function (e) {
        var key = e.which;
        if(key === 13)  // the enter key code
        {
            $("#added").append("<li class='li-serv'>" + $("#service-search").val() + "</li>");
            $("#service-search").val("");
            return false;  
        }
    }); 
    
    //  On modal submit, get the service names of all items added, and send Ajax request to add to database
    $("#serv-select-submit").click(function() {
        $(".li-serv").each(function(i, obj) {
            $.ajax({
               url: "editProfile",
               data: {"service": "add-services", "name": jQuery(obj).text()},
               cache: false
            });
        });
        
        setTimeout(function() {
            location.reload();
        }, 500);
    });
});

