var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		departmenTable: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.departmenTable = {};
		},
		update: function (event) {
			var id = getSelectedRow('departmenTabletable','id');
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.departmenTable.id == null ? "/report/departmentTable/create" : "/report/departmentTable/update";
			$.ajax({
				type: "POST",
			    url: rootPath + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.departmenTable),
			    success: function(r){
				vm.reload();
				alert(r.msg);
			    	
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows('departmenTabletable','id');
			if(ids == null){
				return ;
			}			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: rootPath + "/report/departmentTable/remove",
				    data : {
                        ids : ids
                    },				   
				    success: function(r){
						
							alert(r.msg);
						
					}
				});
			});
		},
		getInfo: function(id){
			$.get(rootPath + "/report/departmentTable/get?id="+id, function(r){
                vm.departmenTable = r.departmenTable;
            });
		},
		reload: function (event) {
			
	            vm.showList = true;
	           layui.use('table', function() {
                var table = layui.table;
                table.render({
                    elem : '#departmenTabletable' // 选定是那个DIV
                    ,
                    url : rootPath + '/report/departmentTable/list',
                    cols : [
                        [
                            {
                                type : 'checkbox'
                            }
                                                          , {
                                field : 'id',
                                title : '',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.id;
                                }
                            }                             , {
                                field : 'reportId',
                                title : '',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.reportId;
                                }
                            }                             , {
                                field : 'departmentId',
                                title : '',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.departmentId;
                                }
                            }                             ] ],
                    page : true, // 开启分页
                    method: 'post',
                    request : LayPagePostRequest,
                    where : $("#searchForm").serializeJSON()
                });
            });
        }
		
	}
});


vm.reload();