$(document).ready(function() {  
    $('.inactive').click(function(){ 
    	$(".first").children('li').children('ul').children('li').css("background-color","#6196bb");
    	//$(".content").css("background-color","#6196bb");
    	
        var className=$(this).parents('li').parents().attr('class'); 
        if($(this).siblings('ul').css('display')=='none'){  
            if(className=="first"){  
            	//siblings() 获得匹配集合中每个元素的同胞，通过选择器进行筛选是可选的。不包括自己
                $(this).parents('li').siblings('li').children('ul').parent('li').children('a').removeClass('inactives');  
                //slideUp() 方法以滑动方式隐藏被选元素。把已经展开都隐藏起来
                $(this).parents('li').siblings('li').children('ul').slideUp(500);
            }  
            $(this).addClass('inactives');  
            $(this).siblings('ul').slideDown(500);  
        }else{  
            $(this).removeClass('inactives');  
            $(this).siblings('ul').slideUp(1000);
        } 
    });
    
   $('.content').click(function(){
   		//tong
   		$(this).parent('li').siblings('li').css("background-color","#6196bb");
   		$(this).parent('li').parents('ul').parents('li').siblings('li').children('ul').children('li').css("background-color","#6196bb")
    	$(this).parent('li').css('background','#3C3C3C');
    });
}); 

