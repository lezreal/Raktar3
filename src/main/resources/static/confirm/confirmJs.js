$(document).ready(function(){
    $('#submit_button').click(function(event) {
        event.preventDefault();
        var currentForm = $(this).closest('form');
        
        /** Create div element for delete confirmation dialog*/
        var dynamicDialog = $('<div id="conformBox">'+
        '<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;">'+
        '</span>Rögzíthetem a változásokat?</div>');
        
        dynamicDialog.dialog({
                title : "Biztos vagy benne?",
                closeOnEscape: true,
                modal : true,
        
               buttons : 
                        [{
                                text : "Igen",
                                click : function() {
                                        $(this).dialog("close");
                                        currentForm.submit();
                                }
                        },
                        {
                                text : "Nem",
                                click : function() {
                                        $(this).dialog("close");
                                }
                        }]
        });
        return false;
    });
}); 