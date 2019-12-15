var vm = new Vue(
		{
			el : '#rrapp',
			data : {
				showList : true,
				title : null,
				rtemplate : {}
			},
			methods : {
				query : function() {
					vm.reload();
				},
				add : function() {
					vm.showList = false;
					vm.title = "新增";
					vm.rtemplate = {};
				},
				update : function(event) {
					var id = getSelectedRow('rtemplatetable', 'id');
					if (id == null) {
						return;
					}
					vm.showList = false;
					vm.title = "修改";

					vm.getInfo(id)
				},
				saveOrUpdate : function(event) {
					var url = vm.rtemplate.id == null ? "/report/template/create"
							: "/report/template/update";
					$.ajax({
						type : "POST",
						url : rootPath + url,
						contentType : "application/json",
						data : JSON.stringify(vm.rtemplate),
						success : function(r) {
							vm.reload();
							alert(r.msg);

						}
					});
				},
				del : function(event) {
					var ids = getSelectedRows('rtemplatetable', 'id');
					if (ids == null) {
						return;
					}
					confirm('确定要删除选中的记录？', function() {
						$.ajax({
							type : "POST",
							url : rootPath + "/report/template/remove",
							data : {
								ids : ids
							},
							success : function(r) {

								alert(r.msg);

							}
						});
					});
				},
				getInfo : function(id) {
					$.get(rootPath + "/report/template/get?id=" + id,
							function(r) {
								vm.rtemplate = r.rtemplate;
							});
				},
				reload : function(event) {

					vm.showList = true;
					layui.use('table', function() {
						var table = layui.table;
						table.render({
							elem : '#rtemplatetable' // 选定是那个DIV
							,
							url : rootPath + '/report/template/list',
							cols : [ [ {
								type : 'checkbox'
							}, {
								field : 'id',
								title : '',
								minWidth : 100,
								templet : function(data) {

									return data.id;
								}
							}, {
								field : 'filename',
								title : '文件名',
								minWidth : 100,
								templet : function(data) {

									return data.filename;
								}
							}, {
								field : 'gmtCreate',
								title : '',
								minWidth : 100,
								templet : function(data) {

									return data.gmtCreate;
								}
							}, {
								field : 'gmtModified',
								title : '',
								minWidth : 100,
								templet : function(data) {

									return data.gmtModified;
								}
							}, {
								field : 'fileContent',
								title : '模板内容',
								minWidth : 100,
								templet : function(data) {

									return data.fileContent;
								}
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