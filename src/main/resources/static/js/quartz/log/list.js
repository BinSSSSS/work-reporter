var vm = new Vue({
    el : "#rrapp",
    data : {
        showList : true
    },
    methods : {
        reload : function() {
            vm.showList = true;
            layui.use('table', function() {
                var table = layui.table;
                table.render({
                    elem : '#joblogtable2', // 选定是那个DIV
                    url : rootPath + '/schedule/log/page-list',
                    cols : [
                        [
                            {
                                type : 'checkbox'
                            },  {
                                field : 'logId',
                                width : 180,
                                title : '任务ID'
                            }, {
                                field : 'beanName',
                                width : 100,
                                title : 'Spring Bean'
                            }, {
                                field : 'methodName',
                                title : '方法名',
                                width : 100
                            }, {
                                field : 'params',
                                title : '参数',
                                width : 100
                            }, {
                                field : 'status',
                                title : '状态',
                                width : 100
                                
                            }, {
                                field : 'remark',
                                title : '备注',
                                width : 100
                            }, {
                                field : 'error',
                                title : '失败信息',
                                width : 100
                            }, {
                                field : 'createTime',
                                title : '创建时间',
                                width : 100
                            }, {
                                field : 'times',
                                title : '耗时',
                                width : 100
                            } ] ],
                    page : true, // 开启分页
                    method: 'post',
                    request : LayPagePostRequest
                // ,
                // where : $("#searchForm").serializeJSON()
                });
            });// lay
        }
    }
})
vm.reload();