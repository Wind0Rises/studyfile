//提交修改
$("#form_edit").submit(function(){
	var data = $("#form_edit").serialize();
	$.ajax({
		url: saveAndUpdate,
		data: data,
		dataType: 'json',
		type: 'post',
		success: function(data){
			if(data.message == 'SUCCESS') {
				layer.confirm('恭喜您！操作成功,确定跳转到列表页面，取消留在当前页面',{
					btn:['确定','取消']
				},function(index){
					console.log(index)
					location.href = listHref;
				},function(){
					location.reload();
				});
			} else if(data.errorMessage != null && data.errorMessage != "") {
				layer.alert(data.errorMessage, {
					icon : 2,
					skin : 'layer-ext-moon',
				});
			} else {
				layer.alert("操作失败！", {
					icon : 2,
					skin : 'layer-ext-moon',
				});
			}
		},
		error: function(){
			layer.alert("操作失败！", {
				icon : 2,
				skin : 'layer-ext-moon',
			});
		}
	});
	return false;
});