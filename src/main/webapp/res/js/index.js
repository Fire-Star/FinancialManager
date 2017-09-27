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

$('.person').hover(function(e){
	$('.person-info').show();
},function(){
	$('.person-info').hide();
});
