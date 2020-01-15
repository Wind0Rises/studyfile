/*  菜单显示    */
var menu = function(first, second) {
	var first_number = "li:eq(" + first + ")";
	var second_number = "li:eq(" + second + ")";
	$(".first").children(first_number).children("ul").css("display", "block");
	$(".first").children(first_number).children("ul").children(second_number)
			.css("background", "#3C3C3C");
};

/*  时间控件  */
/*$(".form_date").datetimepicker({
	language: 'zh-CN',				//语言，注意要引入对应语言的js
	format: "yyyy-MM-dd",			//格式
	weekStart: 1,					//从周几开始
});*/



/*$(".form_datetime").datetimepicker({
	
});*/

/*$(function(){
	if(total < 5){
		for( j = total+1; j <= 5; j++){
			var v = "#"+ j;
			$(v).css("display","none");
		}
	}
	$("#1").addClass("active");
});


var next = function(){
	if((now_nu - -1  >= 3)&&(now_nu - -1 <= total - 2)){
		now_nu = now_nu - -1;
		$("#1 a").text(now_nu - 2);
		$("#2 a").text(now_nu - 1);
		$("#3 a").text(now_nu);
		$("#4 a").text(now_nu - -1);
		$("#5 a").text(now_nu - -2);
		for (i = 1 ; i <= 5; i++) {
			var v = "#"+ i;
			if (i == 3) {
				$(v).addClass("active");
			} else{
				$(v).removeClass("active");
			}
		}
	}else{ 
		if(now_nu >= total){
			now_nu == now_nu;
		}else{
			now_nu = now_nu - -1;
		}
		
		var a;
		if(now_nu < 3) a = now_nu;
		if(now_nu > 3) a = now_nu - total +5;
		for (i = 1 ; i <= 5; i++) {
			var v = "#"+ i;
			if (i == a) {
				$(v).addClass("active");
			} else{
				$(v).removeClass("active");
			}
		}
	}
	replace(now_nu,urlPath);
};

var previous = function(){
	if((now_nu  -1  >= 3)&&(now_nu  -1 <= total - 2)){
		now_nu = now_nu -1;
		$("#1 a").text(now_nu - 2);
		$("#2 a").text(now_nu - 1);
		$("#3 a").text(now_nu);
		$("#4 a").text(now_nu - -1);
		$("#5 a").text(now_nu - -2);
		for (i = 1 ; i <= 5; i++) {
			var v = "#"+ i;
			if (i == 3) {
				$(v).addClass("active");
			} else{
				$(v).removeClass("active");
			}
		}
	}else{ 
		if(now_nu <= 1){
			now_nu == 1;
		}else{
			now_nu = now_nu -1;
		}
		
		var a;
		if(now_nu < 3) a = now_nu;
		if(now_nu > 3) a = now_nu - total +5;
		for (i = 1 ; i <= 5; i++) {
			var v = "#"+ i;
			if (i == a) {
				$(v).addClass("active");
			} else{
				$(v).removeClass("active");
			}
		}
	}
	replace(now_nu,urlPath);
};

var cli = function(number){
	var v = "#" + number;
	now_nu = $(v).text();
	if(now_nu >= 3 && now_nu <= total - 2){
		$("#1 a").text(now_nu - 2);
		$("#2 a").text(now_nu - 1);
		$("#3 a").text(now_nu);
		$("#4 a").text(now_nu - -1);
		$("#5 a").text(now_nu - -2);
		for (i = 1 ; i <= 5; i++) {
			var v = "#"+ i;
			if (i == 3) {
				$(v).addClass("active");
			} else{
				$(v).removeClass("active");
			}
		}
		
	}else{
		for (i = 1 ; i <= 5; i++) {
			var v = "#"+ i;
			if (i == number) {
				$(v).addClass("active");
			} else{
				$(v).removeClass("active");
			}
		}
	};
	replace(now_nu,urlPath);
};*/
