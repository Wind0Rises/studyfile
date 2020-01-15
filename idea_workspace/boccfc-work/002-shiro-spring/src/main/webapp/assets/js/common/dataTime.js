$(function(){	
	$('.form_date').datetimepicker({
	    language: 'zh-CN',
	    format: "yyyy-mm-dd",
	    weekStart: 1,
	    todayBtn: 1,
	    autoclose: 1,
	    todayHighlight: 1,
	    startView: 2,
	    minView: 2,
	    forceParse: 0
	});
	
	$('.form_datetime').datetimepicker({
	    language: 'zh-CN',
	    format: "yyyy-mm-dd HH:mm:ss",
	    weekStart: 1,
	    todayBtn: 1,
	    autoclose: 1,
	    todayHighlight: 1,
	    startView: 2,
	    minView: 0,      				//0:表示可以设置小时
	    forceParse: 0
	});
	
	$('.form_date_hour').datetimepicker({
	    language: 'zh-CN',
	    format: "yyyy-mm-dd hh:ii",
	    weekStart: 1,
	    todayBtn: 1,
	    autoclose: 1,
	    todayHighlight: 1,
	    startView: 2,
	    minView: 0,      				//0:表示可以设置小时
	    forceParse: 0
	});
	
	$('.remove-date-input').click(function(){
		$('.form_date').val('');
		$('.form_datetime').val('');
	});
	
	$(".remove-date-first-input").click(function() {
		var _spanId = this.id;
		var _inputId = "#" + _spanId.substring(7);
		console.log(_inputId);
		$(_inputId).val('');
	})
});