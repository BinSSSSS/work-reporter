var vm = new Vue({
    el : '#rrapp',
    data : {
        showList : true,
        title : null,
        reminder : {},
        mailSender:{}
    },
    methods : {
        query : function() {
            vm.reload();
        },
        update : function() {
            var reminderId = getSelectedRow('reminderTable', 'id');
            if (reminderId == null ||  reminderId.length == 0)  {
                return;
            }
            $.get(rootPath + "/reminder/email/get?id=" + reminderId, function(reminder) {
            	vm.title = "修改";
                vm.showList = false;
            	vm.reminder = reminder;
            	vm.mailSender = reminder.mailSender;
            });
        },
        updateReminder : function(event) {
            var url = rootPath + '/reminder/email/update';
            $.ajax({
                type : "POST",
                url : rootPath + url,
                contentType : "application/json",
                data : JSON.stringify(vm.reminder),
                success : function(r) {
                    vm.reload();
                    alert(r.msg);
                }
            });
        },
        del : function() {
            var reminderIds = getSelectedRows('reminderTable', 'id');
            if (reminderIds == null || reminderIds.length == 0) {
                return;
            }
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.confirm('确定要删除选中的提醒？', function(index) {
                    layer.close(index);
	                $.ajax({
	                    type : "POST",
	                    url : rootPath + "/reminder/email/delete",
	                    // contentType : "application/json",
	                    data : {
	                        ids : reminderIds
	                    },
	                    success : function(r) {
	                        vm.reload();
	                        alert(r.msg);
	                    }
	                });
                });
            });
        },
        pause : function() {
        	var reminderIds = getSelectedRows('reminderTable', 'id');
            if (reminderIds == null || reminderIds.length == 0) {
                return;
            }
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.confirm('确定要暂停选中的提醒？', function(index) {
                    layer.close(index);
	                $.ajax({
	                    type : "POST",
	                    url : rootPath + "/reminder/email/pause",
	                    // contentType : "application/json",
	                    data : {
	                        ids : reminderIds
	                    },
	                    success : function(r) {
	                        vm.reload();
	                        alert(r.msg);
	                    }
	                });
	            });
            });
        },
        resume : function() {
        	var reminderIds = getSelectedRows('reminderTable', 'id');
            if (reminderIds == null || reminderIds.length == 0) {
                return;
            }
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.confirm('确定要恢复选中的提醒？', function(index) {
                    layer.close(index);
	                $.ajax({
	                    type : "POST",
	                    url : rootPath + "/reminder/email/start",
	                    // contentType : "application/json",
	                    data : {
	                        ids : reminderIds
	                    },
	                    success : function(r) {
	                        vm.reload();
	                        alert(r.msg);
	                    }
	                });
	            });
            });
        },
        runOnce : function() {
        	var reminderIds = getSelectedRows('reminderTable', 'id');
            if (reminderIds == null || reminderIds.length == 0) {
                return;
            }
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.confirm('确定要立即执行选中的提醒？', function(index) {
                    layer.close(index);
	                $.ajax({
	                    type : "POST",
	                    url : rootPath + "/reminder/email/run",
	                    // contentType : "application/json",
	                    data : {
	                        ids : reminderIds
	                    },
	                    success : function(r) {
	                        vm.reload();
	                        alert(r.msg);
	                    }
	                });
	            });
            });
        },
        reload : function(event) {
            vm.showList = true;
            layui.use('table', function() {
                var table = layui.table;
                table.render({
                    elem : '#reminderTable' // 选定是那个DIV
                    ,
                    url : rootPath + '/reminder/email/page-list',
                    cols : [
                        [
                            {
                                type : 'checkbox'
                            }, {
                                field : 'id',
                                width : 180,
                                title : '提醒id'
                            }, {
                                field : 'sendTo',
                                title : '发送到邮箱',
                                width : 180,
                                templet: function(data){
                                	return data.mailSender.sendTo;
                                }
                            },
                            {
                                field : 'repeat',
                                title : '提醒类型',
                                width : 100,
                                templet : function(data) {
                                	if(data.schedule.repeat == 0){
                                		return "单次提醒";
                                	}else{
                                		return "重复提醒";
                                	}
                                }
                            }
                            , {
                                field : 'title',
                                title : '发送标题',
                                width : 180,
                                templet: function(data){
                                	return data.mailSender.title;
                                }
                                
                            }, {
                                field : 'content',
                                title : '发送内容',
                                width : 180,
                                templet: function(data){
                                	return data.mailSender.content;
                                }
                            }, {
                                field : 'deprecated',
                                title : '有效性',
                                width : 100,
                                templet: function(data){
                                	if(data.deprecated == 1){
                                		return "已暂停";
                                	}else{
                                		return "正在执行";
                                	}
                                }
                            }, {
                                field : 'sendTime',
                                title : '下次发送时间',
                                width : 180,
                                templet: function(data){
                                	var time =  data.mailSender.sendTime;
                                	if(data.mailSender.sendTime != null)
                                		return new Date(time).Format("yyyy-MM-dd hh:mm");
                                	return null;
                                }
                            } ] ],
                    page : true, // 开启分页
                    method: 'post',
                    request : LayPagePostRequest
//                 where : $("#searchForm").serializeJSON()
                });
            });
        }
    }
});
vm.reload();
$("#reminder-form").submit(function(){
	vm.updateReminder();
})