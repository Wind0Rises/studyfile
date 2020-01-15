/* 数据初始化  */
var intiDate = function(param_url, param_data) {
	$.ajax({
		url : param_url,
		data : param_data,
		type : 'post',
		success : function(data) {
			console.log(data)
			if(data != null && data.data != null && data.data.length > 0) {
				dataDisplayAndNone(1);
			} else {
				dataDisplayAndNone(0);
			}
			
			$("#tbody").empty();
			$("#tbody").html(replace(data.data));
			page(data.count, param_data, param_url);
		},
		error : function() {
			layer.alert("初始化失败！", {
				icon : 2,
				skin : 'layer-ext-moon',
			});
		}
	});
};

//分页
var page = function(count, param_data, param_url){
	$("#page").pagination(count, {
		//两侧显示的首尾分页的条目数
	    num_edge_entries: 2,
	   	//连续分页主体部分显示的分页条目数
	    num_display_entries: 4,
	    //每页显示的条数
	    items_per_page:10,
	    prev_text: '上一页',
	    next_text: '下一页',
	    callback: PageCallback,
	});
	

	function PageCallback (index){
		index += 1;
		param_data["correntPage"] = index;
		$.ajax({
			url: param_url,
			data: param_data,
			type: 'post',
			success: function(data){
				$("#tbody").empty();
				$("#tbody").html(replace(data.data));
			},
			error: function(){
				alert("分页失败！");
			}
		});
	};
};

/* 跳转编辑页面 */
var edit = function(url) {
	location.href = url;
};

/* 跳转到查看页面 */
var show = function(url) {
	location.href = url;
};

/*
 * 删除操作 layer有的skin：layui-layer-lan layui-layer-molv layer-ext-moon
 */
var deleteM = function(_url) {
	layer.confirm("您确定要删除该条数据吗？", {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : _url,
			type : 'post',
			success : function(data) {
				layer.alert("恭喜您，删除成功!", {
					icon : 6,
					skin : 'layui-layer-lan',
				}, function() {
					location.reload();
				});
			},
			error : function() {
				layer.alert("删除失败！", {
					icon : 2,
					skin : 'layer-ext-moon',
				});
			}
		});
	}, function() {
		// 取消操作
	});
};


//数据显示和隐藏。1表述有数据，0变数没有数据
function dataDisplayAndNone(param) {
	if(param == 1) {
		$("#noneData").css("display","none");
		$("#page").css("display","block");
	}
	if(param == 0) {
		$("#page").css("display","none");
		$("#noneData").css("display","block");
	}
}

//date时间格式的转化
function dateUtilMethod(date, type) {
	if(date == null || date == '') {
		return "";
	}
	var dataDate = new Date(date);
	//返回日期格式
	if(type == 'date') {
		return dataDate.toLocaleDateString();
	}
	//返回时间格式
	if(type == 'dateTime') {
		return dataDate.toLocaleString();
	}
}

function nullToEmpty(param) {
	if(param == null) {
		return "";
	}
	return param;
}
