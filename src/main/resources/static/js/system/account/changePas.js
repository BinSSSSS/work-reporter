var vm = new Vue({
            el : '#rrapp',
            data : {
            	items:null,
                password : "",
                newPassword2 : "",
                newPassword : ""
            },
            methods : {
//                boundinfo : function() {
//                    $.ajax({
//                        type : "GET",
//                        url : rootPath + "/thirdoartlogin_do/boundinfo.do",
//                        dataType : "json",
//                        success : function(data) {
//                            vm.items = data.list;
//                            
//                        }
//                    });
//                },
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
                                        vrifyCode : $("#vrifyCode").val()
                                    },
                                    dataType : "json",
                                    success : function(data) {
                                        alert(data.msg);
                                        $("#vrifyCode_modifypwd").attr("src", '/kaptcha?d=' + new Date() * 1);
                                    }
                                });
                            }
                        });
                    });
                   
                }
//                    ,
//                delsocial : function(pro,provideruserid) {
//                    confirm('确定要删除关联？', function() {
//                        $.ajax({
//                            type : "POST",
//                            url : rootPath + "/thirdoartlogin_do/delsocialbind.do",
//                            dataType : "json",
//                            data : {
//                                providerid : pro,
//                                provideruserid,provideruserid
//                            },
//                            success : function(data) {
//                                layui.use('layer', function(){ 
//                                  var  layer = layui.layer
//                                    layer.closeAll(); 
//                                });
//                                alert(data.msg);
//                                vm.boundinfo();
//                                return false;
//                            }
//                        });
//                        
//                    });
//                   
//                },
//                connected : function(pro) {
//                    location.href = rootPath + "/connect/" + pro + "Connected";
//                }
            }
        });
//        vm.boundinfo();