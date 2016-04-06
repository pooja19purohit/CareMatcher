var geocoder;

function StoreGeo(address, email)
{
    
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
           
            var ll = results[0].geometry.location.toString();
            var latitude;
            var longitude;

            llarr = ll.replace(/[\(\) ]/g, '').split(',');
            
            for(i = 0; i < llarr.length;i++)
            {
                if(i===0) {
                    latitude = llarr[0];
                }

                else {
                    longitude = llarr[1];
                }
                //$('#registrationForm').append($('<input type="hidden" name="'+(i == 0 ? 'latitude' : 'longitude')+'">').val(llarr[i]));     
            }
            
            $.ajax({ 
                url: "location", 
                type: "POST", 
                data: { "latitude": latitude, "longitude": longitude, "userEmail" : email, "address": address }, 
                success: function() { console.log("Lat Long Data sent " + latitude + "," + longitude); }, 
                error: function() { console.log("Error setting Lat Long Data"); },
                cache: false 
             });
             
            console.log("Coming here in the end");
            // $('#registrationForm').submit();
        } 
        else
        {
            alert(status);
        }
    });
    /*alert("Coming here");
    $('#registrationForm').unbind('submit');
    return false;*/
    return true;
 }

$(document).ready(function () { 

    //init maps
    geocoder = new google.maps.Geocoder();

    $('#registrationForm').bind('submit',function() {
        var address = $("#inputAddressLine1").val() + ", " + 
                  $("#city").val() + ', ' + 
                  $("#selectState option:selected").val();
          
        var email = $("#inputEmail").val();
        
        StoreGeo(address, email);
    });
    
    $('#updateForm').bind('submit', function() {
        $(".updateAdd").each(function(i, obj) {
            var toks = jQuery(obj).val().split(":");
            var email = toks[0];
            var address = toks[1];
            StoreGeo(address, email);
        });
    });
}); 