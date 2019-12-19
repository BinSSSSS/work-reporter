var vm = new Vue(
		{
			el : '#rrapp',
			data : {
				showList : true,
				title : null,
				genDb : {}
			},
			methods : {
				query : function() {
					vm.reload();
				},
				add : function() {
					vm.showList = false;
					vm.title = "新增";
					vm.genDb = {
						dbUrl : "jdbc:mysql://localhost:3306/wreporter_sys?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=true",
						dbClassDriver : "com.mysql.cj.jdbc.Driver"
					};
				},
				update : function(event) {
					var id = getSelectedRow('genDbTable', 'id');
					if (id == null) {
						return;
					}
					vm.showList = false;
					vm.title = "修改";

					vm.getInfo(id)
				},
				saveOrUpdate : function(event) {
					var url = vm.genDb.id == null ? "/gen-db/create"
							: "/gen-db/update";
					$.ajax({
						type : "POST",
						url : rootPath + url,
						contentType : "application/json",
						data : JSON.stringify(vm.genDb),
						success : function(r) {
							vm.reload();
							alert(r.msg);

						}
					});
				},
				del : function(event) {
					var ids = getSelectedRows('genDbTable', 'id');
					if (ids == null) {
						return;
					}
					layui.use('layer', function() {
		                var layer = layui.layer;
		                layer.confirm('确定要删除选中的记录？', function(index) {
		                    layer.close(index);
							$.ajax({
								type : "POST",
								url : rootPath + "/gen-db/delete",
								data : {
									ids : ids
								},
								success : function(r) {
									alert(r.msg);
									vm.reload();
								}
							});
						});
					});
				},
				getInfo : function(id) {
					$.get(rootPath + "/gen-db/get?id=" + id, function(
							genDb) {
						vm.genDb = genDb;
					});
				},
				reload : function(event) {

					vm.showList = true;
					layui.use('table', function() {
						var table = layui.table;
						table.render({
							elem : '#genDbTable' // 选定是那个DIV
							,
							url : rootPath + '/gen-db/page-list',
							cols : [ [ {
								type : 'checkbox'
							}, {
								field : 'id',
								title : '主键',
								minWidth : 100,
								templet : function(data) {

									return data.id;
								}
							}, {
								field : 'dbName',
								title : '名称',
								minWidth : 100,
								templet : function(data) {

									return data.dbName;
								}
							}, {
								field : 'duUrl',
								title : '数据库地址',
								minWidth : 100,
								templet : function(data) {

									return data.dbUrl;
								}
							}, {
								field : 'dbUsername',
								title : '数据库用户名称',
								minWidth : 100,
								templet : function(data) {

									return data.dbUsername;
								}
							}, {
								field : 'dbPwd',
								title : '数据库密码',
								minWidth : 100,
								templet : function(data) {

									return data.dbPwd;
								}
							},{
								field : 'dbClassDriver',
								title : '数据库驱动',
								minWidth : 100,
								templet : function(data) {

									return data.dbClassDriver;
								}
							},{
								field: 'description',
								title: '数据库描述',
								minWidth: 100,
								templet: function(data){
									return data.description;
								}
							}
							] ],
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

$("#dbForm").submit(function(){
	vm.saveOrUpdate();
})