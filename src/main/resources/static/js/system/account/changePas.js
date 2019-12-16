var vm = new Vue({
            el : '#rrapp',
            data : {
            	items:null,
                password : "",
                newPassword2 : "",
                newPassword : ""
            },
            methods : {

                toupdatepwd : function() {
                    $("#passwordLayer").css("display", 'block');
                },
                updatePassword : function() {
                    layui.use('layer', function(){ 
                        var  layer = layui.layer
                        layer.open({
                            type : 1,
                            skin : 'layui-layer-molv',
                            title : "修改密码",
                            area : [
                                    '600px', '600px'],
                            shadeClose : false,
                            content : jQuery("#passwordLayer"),
                            btn : [
                                    '修改', '取消'],
                            btn1 : function(index) {
                                if (vm.newPassword != vm.newPassword2) {
                                    alert("两次密码不一致");
                                    return false;
                                }
                                var data = "password=" + vm.password + "&newPassword=" + vm.newPassword;
                                $.ajax({
                                    type : "POST",
                                    url : "/account/change-password",
                                    data : {
                                        password : vm.password,
                                        newPassword : vm.newPassword,
                                        vcode : $("#vcode").val()
                                    },
                                    dataType : "json",
                                    success : function(data) {
                                        alert(data.msg);
                                        $("#vrifyCode_modifypwd").attr("src", '/kaptcha?id=' + new Date() * 1);
                                    }
                                });
                            }
                        });
                    });
                   
                }
            }
        });