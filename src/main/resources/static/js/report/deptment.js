var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		deptment: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.deptment = {};
		},
		update: function (event) {
			var id = getSelectedRow('deptmenttable','id');
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.deptment.id == null ? "/report/deptment/create" : "/report/deptment/update";
			$.ajax({
				type: "POST",
			    url: rootPath + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.deptment),
			    success: function(r){
				vm.reload();
				alert(r.msg);
			    	
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows('deptmenttable','id');
			if(ids == null){
				return ;
			}			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: rootPath + "/report/deptment/remove",
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
			$.get(rootPath + "/report/deptment/get?id="+id, function(r){
                vm.deptment = r.deptment;
            });
		},
		reload: function (event) {
			
	            vm.showList = true;
	           layui.use('table', function() {
                var table = layui.table;
                table.render({
                    elem : '#deptmenttable' // 选定是那个DIV
                    ,
                    url : rootPath + '/report/deptment/list',
                    cols : [
                        [
                            {
                                type : 'checkbox'
                            }
                                                          , {
                                field : 'id',
                                title : '主键',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.id;
                                }
                            }                             , {
                                field : 'deptno',
                                title : '单位代码（唯一）',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.deptno;
                                }
                            }                             , {
                                field : 'parentId',
                                title : '父级ID',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.parentId;
                                }
                            }                             , {
                                field : 'deptName',
                                title : '单位名称',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.deptName;
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
                                field : 'ifDel',
                                title : '是否删除',
                                minWidth : 100,
                                templet : function(data) {
                                  
                                  return data.ifDel;
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