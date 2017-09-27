$('.level1 div').click(function(){
	if($(this).hasClass('active')){
		return;
	}
	$('.ul ul').slideUp();
	$(this).siblings('ul').slideDown();
	$('.ul div').removeClass('active');
	$(this).addClass('active');
})
$('.level2').click(function(){
	$('.level2').removeClass('select');
	$(this).addClass('select');
})

$('.person').click(function(e){
	$('.person-info').toggle();
	e.stopPropagation();
});
$(document).click(function(e){
	$('.person-info').hide();
});
$('.person-info').click(function(e){
	e.stopPropagation();
});
function addEvent(o, evt, func) {
	if (o.addEventListener) o.addEventListener(evt, func, false);
	else if (o.attachEvent) o.attachEvent('on' + evt, func);
}
addEvent(document.querySelector('iframe').contentWindow.document,'click', function () {
	$('.person-info').hide();
});
