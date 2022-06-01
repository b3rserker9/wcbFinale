function messageDialog (title, message) {
	$("<div>"+message+"</div>").dialog({
			title:title, open: function() {
    	        $(this).closest(".ui-dialog")
    	        .find(".ui-dialog-titlebar-close")
    	        .removeClass("ui-dialog-titlebar-close")
    	        .addClass("ui-icon-minusthick");
			}
	});	
}