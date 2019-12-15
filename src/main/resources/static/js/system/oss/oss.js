var vm = new Vue({
    el : '#rrapp',
    data : {
        showList : true,
        title : null,
        config : {},
        ossConfigList:[]
    },
    created : function() {
        this.getConfig();
    },
    methods : {
        query : function() {
            vm.reload();
        },
        getConfig : function() {
            $.getJSON(rootPath + "/oss/config/select-list", function(list) {
                vm.ossConfigList = list;
            });
        },
        del : function() {
            var ossIds = getSelectedRows('osstable','id');
            if (ossIds == null || ossIds.length == 0) {
                return;
            }
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.confirm('确定要删除选中的记录？', function(index) {
                    layer.close(index);
	                $.ajax({
	                    type : "POST",
	                    url : rootPath + "/oss/delete",
	                    data : {
	                        ids : ossIds
	                    },
	                    success : function(r) {
	                        alert(r.msg);
	                        vm.reload();
	                    }
	                });
	            });
            });
        },
        upload: function(){
        	vm.showList = false;
        	vm.title = "上传文件到存储桶";
        },
        fileUpload: function(){
        	// 要提交的数据：
        	var formData = new FormData(document.getElementById("uploadForm"));
        	$.ajax({  
        		url : "/oss/upload",
        		type : 'POST',
        		processData : false, // 数据不需要处理
        		contentType : false, // 类型按照表单类型提交
        		data : formData,    
        		success : function(data){
        			alert(data.msg);
        			vm.reload();
        		}
        	});
        },
        reload : function() {
            vm.showList = true;
            layui.use('table', function() {
                var table = layui.table;
                table.render({
                    elem : '#osstable' // 选定是那个DIV
                    ,
                    url : rootPath + '/oss/page-list',
                    cols : [
                        [
                            {
                                type : 'checkbox'
                            }, {
                                field : 'id',
                                width : 180,
                                title : 'ID'
                            }, {
                                field : 'url',
                                minWidth : 100,
                                title : '图片',
                                templet : function(data) {
                                    return "<img width='300px' hight='300px' src='" + data.url + "'>";
                                }
                            }, {
                                field : 'createdate',
                                title : '创建时间',
                                minWidth : 100
                            } ] ],
                    page : true, // 开启分页
                    // request : laypagerequest,
                    // response : laypageresponse,
                    where : $("#searchForm").serializeJSON()
                });
            });
        }
    }
});

vm.reload();
/*文件的上传*/
$("#uploadForm").submit(function(){
	vm.fileUpload();
});