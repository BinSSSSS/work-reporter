layui.config({
    base : '/js/system/'
}).use([
        'jquery', 'form', 'layer'], function() {
    var form = layui.form, layer = layui.layer, swiper = layui.swiper, $ = layui.jquery;
    var lrpObjs = {
        $imgHolder : $('#switch-bg-list'),
        $target : $('#bg-overlay'),
        lrg_a : $('.login,.reg,.forgetPass'),
        logForm_input : $("#loginForm input"),
        log_reg_pass : $("#loginForm,#regForm,#updatePassForm")
    }
    $(document).ready(function() {
        var $bgBtn = lrpObjs.$imgHolder.find('.switch-chg-bg');
        $bgBtn.on('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            var $el = $(this);
            if ($el.hasClass('active') || lrpObjs.$imgHolder.hasClass('disabled')) {
                return;
            }
            if ($el.hasClass('bg-trans')) {
                lrpObjs.$target.css('background-image', 'none').removeClass('bg-img');
                lrpObjs.$imgHolder.removeClass('disabled');
                $bgBtn.removeClass('active');
                $el.addClass('active');
                return;
            }
            lrpObjs.$imgHolder.addClass('disabled');
            var url = $el.attr('src').replace('/thumbs', '');
            $('<img/>').attr('src', url).load(function() {
                lrpObjs.$target.css('background-image', 'url("' + url + '")').addClass('bg-img');
                lrpObjs.$imgHolder.removeClass('disabled');
                $bgBtn.removeClass('active');
                $el.addClass('active');
                $(this).remove();
            })
        });
    });
    $("#bgimg").click();
  
    /**
     * 翻转效果
     */
    var swiper = new Swiper('.swiper-container', {
        pagination : '.swiper-pagination',
        effect : 'flip',
        noSwiping : true,
        paginationClickable : true,
    });
    lrpObjs.lrg_a.on('click', function() {
        var _className = $(this).context.className;
        lrpObjs.log_reg_pass.find("input").each(function() {
            $(this).val("");
        })
        if (_className === 'reg') {
            $('.swiper-pagination span').eq(1).trigger('click');
          //  $('#regForm').find("img").attr("src", "/defaultKaptcha");
        } else
            if (_className === 'login') {
                $('.swiper-pagination span').eq(0).trigger('click');
              //  $('#loginForm').find("img[name='Kaptcha']").attr("src", "");
              //  $('#loginForm').find("img[name='Kaptcha']").attr("src", "/defaultKaptcha");
            } else
                if (_className === 'forgetPass') {
                  $('.swiper-pagination span').eq(2).trigger('click');
                    
               //     $('#updatePassForm').find("img").attr("src", "/defaultKaptcha");
                }
    });
    /**
     * 登陆 form 表单
     */
    lrpObjs.logForm_input.on('input', function() {
        var _form = $(this).parents('.layui-form'), _inputs = _form.find("input");
        if (_inputs.eq(0).val() && _inputs.eq(1).val()) {
            _form.children('button').removeClass("layui-btn-disabled");
            _form.children('button').prop('disabled', false);
        }
    });
    form.on("submit(loginForm)", function(data) {

    	$("#loginForm").ajaxSubmit({
            type : 'post', // 提交方式 get/post
            url : '/login', // 需要提交的 url
            data : $(this).serialize(),
            success : function(data) { // data 保存提交后返回的数据，一般为 json 数据
                // 此处可对 data 作相关处理
                alert(data.msg);
//                $("#loginyzm").attr("src","/defaultKaptcha?d='+new Date()*1");
                if(data.success){
                	// alert(data.msg);
                	
                	 window.location.href ="/index.html";
                }
               
            }
        });
        return false;
    });
    /**
     * 注册 form 表单
     */
    form.on("submit(regForm)", function(data) {
    	
        if ($("#regForm").find("input[name='inputPassword']").val() != $("#regForm").find("input[name='password']").val()) {
            alert("两次密码不一致！");
            return false;
        }
        $("#regForm").ajaxSubmit({
            type : 'post', // 提交方式 get/post
            url : '/register', // 需要提交的 url
            data : $(this).serialize(),
            success : function(data) { // data 保存提交后返回的数据，一般为 json 数据
                // 此处可对 data 作相关处理
                alert(data.msg);
                $("#regForm").resetForm(); // 提交后重置表单
//                $("#regForm").find("img").attr("src","/kaptcha?d='+new Date()*1");
                
            }
        });
        return false;
    });
    /**
     * 忘记密码 form 表单
     */
    form.on("submit(updatePassForm)", function(data) {
     //   layer.msg(JSON.stringify(data.field));
        
        $.ajax({
            
            type : 'post', 
            async : false,
            url : "/password-forget" ,
            data : data.field,
            dataType : "json",
            success : function(data) {
//                alert(data.msg);
            	$("#updatePassForm").resetForm();
                $("#updatePassForm").find("img").attr("src","/kaptcha?d='+new Date()*1");
            	alert(data.msg);
            	
            }
        });
        
        return false;
    });
    
    
    $("#send-email").click(function(){
    	var email =  $("#email").val();
    	
    	$(this).attr("disabled","true");
    	if(email ==  null || email.length == 0){
    		alert("邮箱不能为空");
    		return ;
    	}
    	$.ajax({
    		type: "POST",
    		url: "/send-ecode",
    		data:{
    			email: email
    		},
    		success: function(data){
    			alert(data.msg);
    			 $("#send-email").removeAttr("disabled");
    		},
    		error: function(){
    			alert("输入的邮箱格式错误或邮箱不存在~");
    			 $("#send-email").removeAttr("disabled");
    			
    		}
    	});
    	
    })
});
