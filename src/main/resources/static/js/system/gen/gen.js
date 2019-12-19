var vm = new Vue({
    el : '#rrapp',
    data : {
        showList : true,
        title : null,
        tablePrefix : null,
        javapackage : null,
        author : null,
        pathName : null,
        email : null,
        styleId:null,
        database:[],allstyle:[]
    },
    created:function(){

        $.ajax({
            url:"/gen-db/select-list",
            data:{},
            dataType:"json",
            type:"post",
            success:function(data){
                vm.database=data;
            }


        });

        $.ajax({
            url : rootPath + "/gen-style/select-list",
            type : "post",
            dataType : "json",
            success : function(data) {
                vm.allstyle = data;
            }

        })


    },
    methods : {
        query : function() {
            vm.reload();
        },

        addConfig : function() {
            vm.showList = false;
            vm.title = "代码生成配置";
            var url = '/gen-code/settings';
            $.ajax({
                type : "POST",
                url : url,
                // contentType : "application/json",
                dataType : 'json',
                data : {

                },
                success : function(map) {
                    try{
                        vm.javapackage=map.package;
                        vm.tablePrefix=map.tablePrefix;
                        vm.author=map.author;
                        vm.pathName=map.pathName;
                        vm.email=map.email;
                    }catch(e){

                    }

                }
            });
        },
        saveOrUpdate : function() {
            var url = rootPath + "/gen-code/update-settings";
            $.ajax({
                dataType : 'json',
                type : "POST",
                url : url,
                // contentType : "application/json",
                data : {

                    tablePrefix : vm.tablePrefix,
                    package : vm.javapackage,
                    author : vm.author.value,
                    pathName : vm.pathName,
                    email : vm.email,
                    styleId:vm.styleId

                },
                success : function(r) {
                    alert(r.msg);
                    vm.reload();
                }
            });
        },

        reload : function() {
            vm.showList = true;
            layui.use('table', function() {
                var table = layui.table;
                table.render({
                    elem : '#gentable' // 选定是那个DIV
                    ,
                    url : rootPath + '/gen-code/table-list',
                    cols : [
                        [
                            {
                                type : 'checkbox'
                            }, {
                            field : 'tableName',
                            width : 180,
                            title : '表名'
                        }, {
                            field : 'comment',
                            title : '备注',
                            minWidth : 100,
                            // templet : function(data) {
                            //     return data.comment;
                            // }
                        } ] ],
                    page : true, // 开启分页
                    method: 'post',
                    request : LayPagePostRequest,
                    where : $("#searchForm").serializeJSON()
                });
            });
        },
        gencode:function(){

            var tableName = getSelectedRows('gentable', 'tableName');
            if (tableName == null || tableName.length == 0) {
                return;
            }

            layui.use('layer', function() {
                    layer=layui.layer;
                    layer.confirm('是否生成？', function(index) {
                    	layer.close(index);
                    	
                         location.href = '/gen-code/code?tableName=' + tableName+"&schemaName="+$("#schemaName").val();
                    	
                    });
                }
            );
        }
    }
});


vm.reload();
$("#genForm").submit(function(){
	vm.saveOrUpdate();
})