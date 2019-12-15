var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		row: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.row = {};
		},
		update: function (event) {
			var id = getSelectedRow('rowtable','id');
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.row.id == null ? "/report/row/create" : "/report/row/update";
			$.ajax({
				type: "POST",
			    url: rootPath + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.row),
			    success: function(r){
				vm.reload();
				alert(r.msg);
			    	
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows('rowtable','id');
			if(ids == null){
				return ;
			}			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: rootPath + "/report/row/remove",
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
			$.get(rootPath + "/report/row/get?id="+id, function(r){
                vm.row = r.row;
            });
		},
		reload: function (event) {
			
	            vm.showList = true;
	           layui.use('table', function() {
                var table = layui.table;
                table.render({
                    elem : '#rowtable' // 选定是那个DIV
                    ,
                    url : rootPath + '/report/row/list',
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
                                field : 'rowName',
                                title : '',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.rowName;
                                }
                            }                             , {
                                field : 'rowKey',
                                title : '',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.rowKey;
                                }
                            }                             , {
                                field : 'rowNum',
                                title : '',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.rowNum;
                                }
                            }                             , {
                                field : 'gmtCreate',
                                title : '',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.gmtCreate;
                                }
                            }                             , {
                                field : 'gmtModified',
                                title : '',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.gmtModified;
                                }
                            }                             , {
                                field : 'reportId',
                                title : '',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.reportId;
                                }
                            }                             ] ],
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