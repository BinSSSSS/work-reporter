var vm = new Vue({
    el : '#rrapp',
    data : {
        q : {
            beanName : null
        },
        showList : true,
        title : null,
        tags : {}
    },
    methods : {
        query : function() {
            vm.reload();
        },
        add : function() {
            vm.showList = false;
            vm.title = "新增";
            vm.tags = {};
        },
        update : function() {
            var jobId = getSelectedRow('tagtable','id');
            if (jobId == null) {
                return;
            }
            var tagsname = getSelectedRow('tagtable','name');
            vm.title = "修改";
            vm.showList = false;
            vm.tags.id = jobId;
            vm.tags.name = tagsname;
        },
        saveOrUpdate : function(event) {
            var url = vm.tags.id == null ? rootPath + '/wechat/tags/create' : rootPath + '/wechat/tags/update';
            $.ajax({
                type : "POST",
                url : rootPath + url,
                contentType : "application/json",
                data : JSON.stringify(vm.tags),
                success : function(r) {
                    vm.reload();
                    alert(r.msg);
                }
            });
        },
        del : function() {
            var jobIds = getSelectedRows('tagtable','id');
            if (jobIds == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function() {
                $.ajax({
                    type : "POST",
                    url : rootPath + "/wechat/tags/remove",
                    // contentType : "application/json",
                    data : {
                        ids : jobIds
                    },
                    success : function(r) {
                        vm.reload();
                        alert(r.msg);
                    }
                });
            });
        },
        reload : function(event) {
            vm.showList = true;
            layui.use('table', function() {
                var table = layui.table;
                table.render({
                    elem : '#tagtable' // 选定是那个DIV
                    ,
                    url : rootPath + '/wechat/tags/page-list',
                    cols : [
                        [
                            {
                                type : 'checkbox'
                            }, {
                                field : 'id',
                                width : 180,
                                title : '标签ID'
                            }, {
                                field : 'name',
                                width : 180,
                                title : '标签名称'
                            }, {
                                field : 'count',
                                title : '标签人数',
                                width : 180
                            } ] ],
                    page : true, // 开启分页
                    request : laypagerequest,
                    response : laypageresponse,
                    where : $("#searchForm").serializeJSON()
                });
            });
        }
    }
});

vm.reload();