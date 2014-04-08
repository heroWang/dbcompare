//问号图标+提示
$.fn.forecastQuestion = function (arg) {
    var that = this,div;
    $(document.body).append(div = $('<div style="position:absolute;background-color:#FEFCCC;display:none;padding:5px;">'+arg+'</div>'));
    this.hover(function() {
        var offset = that.offset();
        div.css({
            top: offset.top + 17,
            left: offset.left
        });
        div.toggle();
    }).addClass('forecast-question');
};
//清空日期控件
$(function(){
	$("#clear-date").click(function(){
		$(this).prevAll("input").val("");
	});
});
	$.fn.selectPreventChange = function (isCancel, warn) {
		return $.each(this, function () {
            if (isCancel) {
    			$(this).bind("focus change", function (e) {
    				if (e.type == "focus") {
    					$(this).data("val", $(this).val());
    				}
    				else {
    					$(this).val($(this).data("val"));
    					alert(warn || "此项不可以修改");
    				};
    			});
            }
            else {
                $(this).unbind("focus change");
            }
		});
	};
window.shrinkToFit = false;
// 执行
function execute(url) {
	window.location.href = url;
}

//
function confirmurl(url,message){				
	if(window.confirm(message)){
		window.location.href=url;
	}
}

// get short file name 
// e.g.: '/contract-template/template.txt' -> 'template.txt'
function getShortFileName(fullName){
	if(fullName==null || fullName == undefined){
		return '';
	}
	if(fullName.indexOf("/") < 0){
		return fullName;
	}
	return fullName.substring(fullName.lastIndexOf('/') + 1);
}



/**
 * refresh task count of navigation bar
 */
var crmutil = {
	refreshTaskCount : function (){
		top.frames['header'].window.renewTaskCount();
	}
};
